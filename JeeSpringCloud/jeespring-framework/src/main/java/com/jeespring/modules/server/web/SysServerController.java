/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.jeespring.modules.server.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeespring.modules.server.service.ISysServerService;
import com.jeespring.modules.sys.service.SysConfigService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.jeespring.common.utils.DateUtils;
import com.jeespring.common.config.Global;
import com.jeespring.common.persistence.Page;
import com.jeespring.common.web.AbstractBaseController;
import com.jeespring.common.utils.StringUtils;
import com.jeespring.common.utils.excel.ExportExcel;
import com.jeespring.common.utils.excel.ImportExcel;
import com.jeespring.modules.server.entity.SysServer;
import com.jeespring.modules.server.service.SysServerService;
//import com.alibaba.dubbo.config.annotation.Reference;
/**
 * 服务器监控Controller
 * @author JeeSpring
 * @version 2018-08-20
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysServer")
public class SysServerController extends AbstractBaseController {

	//调用dubbo服务器是，要去掉下面注解
	//@Reference(version = "1.0.0")
	@Autowired
	private ISysServerService sysServerService;
	@Autowired
	private SysConfigService sysConfigService;

	@ModelAttribute
	public SysServer get(@RequestParam(required=false) String id) {
		SysServer entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysServerService.getCache(id);
			//entity = sysServerService.get(id);
		}
		if (entity == null){
			entity = new SysServer();
		}
		return entity;
	}

	/**
	 * 服务器监控统计页面
	 */
	@RequiresPermissions("sys:sysServer:total")
	@RequestMapping(value = {"total"})
	public String totalView(SysServer sysServer, HttpServletRequest request, HttpServletResponse response, Model model) {
		total(sysServer,request,response,model);
		return "modules/sys/sysServerTotal";
	}
	private void total(SysServer sysServer, HttpServletRequest request, HttpServletResponse response, Model model) {
			if(StringUtils.isEmpty(sysServer.getTotalType())){
			sysServer.setTotalType("%Y-%m-%d");
		}
		//X轴的数据
		List<String> xAxisData= new ArrayList<String>();
		//Y轴的数据
		Map<String,List<Double>> yAxisData = new HashMap<String,List<Double>>();
		List<Double> countList = new ArrayList<Double>();
		List<Double> sumList = new ArrayList<Double>();
		if(sysServer.getOrderBy()==""){
			sysServer.setOrderBy("totalDate");
		}
		List<SysServer> list = sysServerService.totalCache(sysServer);
		//List<SysServer> list = sysServerService.total(sysServer);
		model.addAttribute("list", list);
		for(SysServer sysServerItem:list){
			//x轴数据
			xAxisData.add( sysServerItem.getTotalDate());
			countList.add(Double.valueOf(sysServerItem.getTotalCount()));
		}
		yAxisData.put("数量", countList);
	    request.setAttribute("xAxisData", xAxisData);
		request.setAttribute("yAxisData", yAxisData);
		model.addAttribute("sumTotalCount", list.stream().mapToInt(SysServer::getTotalCount).sum());

		//饼图数据
		Map<String,Object> orientData= new HashMap<String,Object>();
		for(SysServer sysServerItem:list){
			orientData.put(sysServerItem.getTotalDate(), sysServerItem.getTotalCount());
		}
		model.addAttribute("orientData", orientData);
	}
	@RequiresPermissions("sys:sysServer:total")
	@RequestMapping(value = {"totalMap"})
	public String totalMap(SysServer sysServer, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(StringUtils.isEmpty(sysServer.getTotalType())){
			sysServer.setTotalType("%Y-%m-%d");
		}
		List<SysServer> list = sysServerService.totalCache(sysServer);
		//List<SysServer> list = sysServerService.total(sysServer);
		model.addAttribute("sumTotalCount", list.stream().mapToInt(SysServer::getTotalCount).sum());
		model.addAttribute("list", list);
		return "modules/sys/sysServerTotalMap";
	}

	/**
	 * 服务器监控列表页面
	 */
	@RequiresPermissions("sys:sysServer:list")
	@RequestMapping(value = {"list", ""})
	public String list(SysServer sysServer, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysServer> page = sysServerService.findPageCache(new Page<SysServer>(request, response), sysServer);
		//Page<SysServer> page = sysServerService.findPage(new Page<SysServer>(request, response), sysServer);
		model.addAttribute("page", page);
		sysServer.setOrderBy("totalDate");
		total(sysServer,request,response,model);
		return "modules/sys/sysServerList";
	}

	/**
	 * 服务器监控列表页面
	 */
	@RequiresPermissions("sys:sysServer:list")
	@RequestMapping(value = {"listVue"})
	public String listVue(SysServer sysServer, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysServer> page = sysServerService.findPageCache(new Page<SysServer>(request, response), sysServer);
		//Page<SysServer> page = sysServerService.findPage(new Page<SysServer>(request, response), sysServer);
		model.addAttribute("page", page);
		return "modules/sys/sysServerListVue";
	}

	/**
	 * 服务器监控列表页面
	 */
	//RequiresPermissions("sys:sysServer:select")
	@RequestMapping(value = {"select"})
	public String select(SysServer sysServer, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysServer> page = sysServerService.findPageCache(new Page<SysServer>(request, response), sysServer);
		//Page<SysServer> page = sysServerService.findPage(new Page<SysServer>(request, response), sysServer);
		model.addAttribute("page", page);
		return "modules/sys/sysServerSelect";
	}

	/**
	 * 查看，增加，编辑服务器监控表单页面
	 */
	@RequiresPermissions(value={"sys:sysServer:view","sys:sysServer:add","sys:sysServer:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(SysServer sysServer, Model model, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("action", request.getParameter("action"));
		model.addAttribute("sysServer", sysServer);
		if(request.getParameter("ViewFormType")!=null && "FormTwo".equals(request.getParameter("ViewFormType"))) {
            return "modules/sys/sysServerFormTwo";
        }
		return "modules/sys/sysServerForm";
	}

	/**
	 * 保存服务器监控
	 */
	@RequiresPermissions(value={"sys:sysServer:add","sys:sysServer:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(SysServer sysServer, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {
		if(sysConfigService.isDemoMode()){
			addMessage(redirectAttributes, sysConfigService.isDemoModeDescription());
			return "redirect:" + adminPath + "/sys/sysServer/?repage";
		}
		if (!beanValidator(model, sysServer)){
			return form(sysServer, model,request,response);
		}
		sysServerService.save(sysServer);
		addMessage(redirectAttributes, "保存服务器监控成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysServer/?repage";
	}

	/**
	 * 删除服务器监控
	 */
	@RequiresPermissions("sys:sysServer:del")
	@RequestMapping(value = "delete")
	public String delete(SysServer sysServer, RedirectAttributes redirectAttributes) {
		if(sysConfigService.isDemoMode()){
			addMessage(redirectAttributes, sysConfigService.isDemoModeDescription());
			return "redirect:" + adminPath + "/sys/sysServer/?repage";
		}
		sysServerService.delete(sysServer);
		addMessage(redirectAttributes, "删除服务器监控成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysServer/?repage";
	}

	/**
	 * 删除服务器监控（逻辑删除，更新del_flag字段为1,在表包含字段del_flag时，可以调用此方法，将数据隐藏）
	 */
	@RequiresPermissions(value={"sys:sysServer:del","sys:sysServer:delByLogic"},logical=Logical.OR)
	@RequestMapping(value = "deleteByLogic")
	public String deleteByLogic(SysServer sysServer, RedirectAttributes redirectAttributes) {
		if(sysConfigService.isDemoMode()){
			addMessage(redirectAttributes, sysConfigService.isDemoModeDescription());
			return "redirect:" + adminPath + "/sys/sysServer/?repage";
		}
		sysServerService.deleteByLogic(sysServer);
		addMessage(redirectAttributes, "逻辑删除服务器监控成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysServer/?repage";
	}

	/**
	 * 批量删除服务器监控
	 */
	@RequiresPermissions("sys:sysServer:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		if(sysConfigService.isDemoMode()){
			addMessage(redirectAttributes, sysConfigService.isDemoModeDescription());
			return "redirect:" + adminPath + "/sys/sysServer/?repage";
		}
		String[] idArray = ids.split(",");
		for(String id : idArray){
			sysServerService.delete(sysServerService.get(id));
		}
		addMessage(redirectAttributes, "删除服务器监控成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysServer/?repage";
	}

	/**
	 * 批量删除服务器监控（逻辑删除，更新del_flag字段为1,在表包含字段del_flag时，可以调用此方法，将数据隐藏）
	 */
	@RequiresPermissions(value={"sys:sysServer:del","sys:sysServer:delByLogic"},logical=Logical.OR)
	@RequestMapping(value = "deleteAllByLogic")
	public String deleteAllByLogic(String ids, RedirectAttributes redirectAttributes) {
		if(sysConfigService.isDemoMode()){
			addMessage(redirectAttributes, sysConfigService.isDemoModeDescription());
			return "redirect:" + adminPath + "/sys/sysServer/?repage";
		}
		String[] idArray = ids.split(",");
		for(String id : idArray){
			sysServerService.deleteByLogic(sysServerService.get(id));
		}
		addMessage(redirectAttributes, "删除服务器监控成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysServer/?repage";
	}

	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("sys:sysServer:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(SysServer sysServer, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "服务器监控"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SysServer> page = sysServerService.findPage(new Page<SysServer>(request, response, -1), sysServer);
    		new ExportExcel("服务器监控", SysServer.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出服务器监控记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/sysServer/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:sysServer:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<SysServer> list = ei.getDataList(SysServer.class);
			for (SysServer sysServer : list){
				sysServerService.save(sysServer);
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条服务器监控记录");
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入服务器监控失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/sysServer/?repage";
    }
	
	/**
	 * 下载导入服务器监控数据模板
	 */
	@RequiresPermissions("sys:sysServer:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "服务器监控数据导入模板.xlsx";
    		List<SysServer> list = Lists.newArrayList(); 
    		new ExportExcel("服务器监控数据", SysServer.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/sysServer/?repage";
    }
	

}