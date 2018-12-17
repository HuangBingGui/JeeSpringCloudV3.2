/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.jeespring.modules.sys.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.jeespring.modules.sys.entity.SysUserOnline;
import com.jeespring.modules.sys.service.SysUserOnlineService;
/**
 * 在线用户记录Controller
 * @author JeeSpring
 * @version 2018-08-16
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysUserOnline")
public class SysUserOnlineController extends AbstractBaseController {

	@Autowired
	private SysUserOnlineService sysUserOnlineService;
	
	@ModelAttribute
	public SysUserOnline get(@RequestParam(required=false) String id) {
		SysUserOnline entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysUserOnlineService.getCache(id);
			//entity = sysUserOnlineService.get(id);
		}
		if (entity == null){
			entity = new SysUserOnline();
		}
		return entity;
	}

	/**
	 * 在线用户记录统计页面
	 */
	@RequiresPermissions("sys:sysUserOnline:total")
	@RequestMapping(value = {"total"})
	public String totalView(SysUserOnline sysUserOnline, HttpServletRequest request, HttpServletResponse response, Model model) {
		total(sysUserOnline,request,response,model);
		return "modules/sys/sysUserOnlineTotal";
	}
	private void total(SysUserOnline sysUserOnline, HttpServletRequest request, HttpServletResponse response, Model model) {
			if(StringUtils.isEmpty(sysUserOnline.getTotalType())){
			sysUserOnline.setTotalType("%Y-%m-%d");
		}
		//X轴的数据
		List<String> xAxisData= new ArrayList<String>();
		//Y轴的数据
		Map<String,List<Double>> yAxisData = new HashMap<String,List<Double>>();
		List<Double> countList = new ArrayList<Double>();
		List<Double> sumList = new ArrayList<Double>();
		if(	sysUserOnline.getOrderBy()==""){
			sysUserOnline.setOrderBy("totalDate");
		}
		List<SysUserOnline> list = sysUserOnlineService.totalCache(sysUserOnline);
		//List<SysUserOnline> list = sysUserOnlineService.total(sysUserOnline);
		model.addAttribute("list", list);
		for(SysUserOnline sysUserOnlineItem:list){
			//x轴数据
			xAxisData.add( sysUserOnlineItem.getTotalDate());
			countList.add(Double.valueOf(sysUserOnlineItem.getTotalCount()));
		}
		yAxisData.put("数量", countList);
	    request.setAttribute("xAxisData", xAxisData);
		request.setAttribute("yAxisData", yAxisData);
		model.addAttribute("sumTotalCount", list.stream().mapToInt(SysUserOnline::getTotalCount).sum());

		//饼图数据
		Map<String,Object> orientData= new HashMap<String,Object>();
		for(SysUserOnline sysUserOnlineItem:list){
			orientData.put(sysUserOnlineItem.getTotalDate(), sysUserOnlineItem.getTotalCount());
		}
		model.addAttribute("orientData", orientData);
	}
	@RequiresPermissions("sys:sysUserOnline:total")
	@RequestMapping(value = {"totalMap"})
	public String totalMap(SysUserOnline sysUserOnline, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(StringUtils.isEmpty(sysUserOnline.getTotalType())){
			sysUserOnline.setTotalType("%Y-%m-%d");
		}
		List<SysUserOnline> list = sysUserOnlineService.totalCache(sysUserOnline);
		//List<SysUserOnline> list = sysUserOnlineService.total(sysUserOnline);
		model.addAttribute("sumTotalCount", list.stream().mapToInt(SysUserOnline::getTotalCount).sum());
		model.addAttribute("list", list);
		return "modules/sys/sysUserOnlineTotalMap";
	}

	/**
	 * 在线用户记录列表页面
	 */
	@RequiresPermissions("sys:sysUserOnline:list")
	@RequestMapping(value = {"list", ""})
	public String list(SysUserOnline sysUserOnline, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysUserOnline> page = sysUserOnlineService.findPageCache(new Page<SysUserOnline>(request, response), sysUserOnline);
		//Page<SysUserOnline> page = sysUserOnlineService.findPage(new Page<SysUserOnline>(request, response), sysUserOnline);
		model.addAttribute("page", page);
		sysUserOnline.setOrderBy("totalDate");
		total(sysUserOnline,request,response,model);
		return "modules/sys/sysUserOnlineList";
	}

	/**
	 * 在线用户记录列表页面
	 */
	@RequiresPermissions("sys:sysUserOnline:list")
	@RequestMapping(value = {"listVue"})
	public String listVue(SysUserOnline sysUserOnline, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysUserOnline> page = sysUserOnlineService.findPageCache(new Page<SysUserOnline>(request, response), sysUserOnline);
		//Page<SysUserOnline> page = sysUserOnlineService.findPage(new Page<SysUserOnline>(request, response), sysUserOnline);
		model.addAttribute("page", page);
		return "modules/sys/sysUserOnlineListVue";
	}

	/**
	 * 在线用户记录列表页面
	 */
	//RequiresPermissions("sys:sysUserOnline:select")
	@RequestMapping(value = {"select"})
	public String select(SysUserOnline sysUserOnline, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysUserOnline> page = sysUserOnlineService.findPageCache(new Page<SysUserOnline>(request, response), sysUserOnline);
		//Page<SysUserOnline> page = sysUserOnlineService.findPage(new Page<SysUserOnline>(request, response), sysUserOnline);
		model.addAttribute("page", page);
		return "modules/sys/sysUserOnlineSelect";
	}

	/**
	 * 查看，增加，编辑在线用户记录表单页面
	 */
	@RequiresPermissions(value={"sys:sysUserOnline:view","sys:sysUserOnline:add","sys:sysUserOnline:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(SysUserOnline sysUserOnline, Model model, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("action", request.getParameter("action"));
		model.addAttribute("sysUserOnline", sysUserOnline);
		if(request.getParameter("ViewFormType")!=null && "FormTwo".equals(request.getParameter("ViewFormType"))) {
            return "modules/sys/sysUserOnlineFormTwo";
        }
		return "modules/sys/sysUserOnlineForm";
	}

	/**
	 * 保存在线用户记录
	 */
	@RequiresPermissions(value={"sys:sysUserOnline:add","sys:sysUserOnline:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(SysUserOnline sysUserOnline, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {
		if (!beanValidator(model, sysUserOnline)){
			return form(sysUserOnline, model,request,response);
		}
		sysUserOnlineService.save(sysUserOnline);
		addMessage(redirectAttributes, "保存在线用户记录成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysUserOnline/?repage";
	}

	/**
	 * 删除在线用户记录
	 */
	@RequiresPermissions("sys:sysUserOnline:del")
	@RequestMapping(value = "delete")
	public String delete(SysUserOnline sysUserOnline, RedirectAttributes redirectAttributes) {
		sysUserOnlineService.delete(sysUserOnline);
		addMessage(redirectAttributes, "删除在线用户记录成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysUserOnline/?repage";
	}

	/**
	 * 删除在线用户记录（逻辑删除，更新del_flag字段为1,在表包含字段del_flag时，可以调用此方法，将数据隐藏）
	 */
	@RequiresPermissions(value={"sys:sysUserOnline:del","sys:sysUserOnline:delByLogic"},logical=Logical.OR)
	@RequestMapping(value = "deleteByLogic")
	public String deleteByLogic(SysUserOnline sysUserOnline, RedirectAttributes redirectAttributes) {
		sysUserOnlineService.deleteByLogic(sysUserOnline);
		addMessage(redirectAttributes, "逻辑删除在线用户记录成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysUserOnline/?repage";
	}

	/**
	 * 批量删除在线用户记录
	 */
	@RequiresPermissions("sys:sysUserOnline:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
        String[] idArray = ids.split(",");
		for(String id : idArray){
			sysUserOnlineService.delete(sysUserOnlineService.get(id));
		}
		addMessage(redirectAttributes, "删除在线用户记录成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysUserOnline/?repage";
	}

	/**
	 * 批量删除在线用户记录（逻辑删除，更新del_flag字段为1,在表包含字段del_flag时，可以调用此方法，将数据隐藏）
	 */
	@RequiresPermissions(value={"sys:sysUserOnline:del","sys:sysUserOnline:delByLogic"},logical=Logical.OR)
	@RequestMapping(value = "deleteAllByLogic")
	public String deleteAllByLogic(String ids, RedirectAttributes redirectAttributes) {
        String[] idArray = ids.split(",");
		for(String id : idArray){
			sysUserOnlineService.deleteByLogic(sysUserOnlineService.get(id));
		}
		addMessage(redirectAttributes, "删除在线用户记录成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysUserOnline/?repage";
	}

	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("sys:sysUserOnline:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(SysUserOnline sysUserOnline, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "在线用户记录"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SysUserOnline> page = sysUserOnlineService.findPage(new Page<SysUserOnline>(request, response, -1), sysUserOnline);
    		new ExportExcel("在线用户记录", SysUserOnline.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出在线用户记录记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/sysUserOnline/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:sysUserOnline:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<SysUserOnline> list = ei.getDataList(SysUserOnline.class);
			for (SysUserOnline sysUserOnline : list){
				sysUserOnlineService.save(sysUserOnline);
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条在线用户记录记录");
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入在线用户记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/sysUserOnline/?repage";
    }
	
	/**
	 * 下载导入在线用户记录数据模板
	 */
	@RequiresPermissions("sys:sysUserOnline:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "在线用户记录数据导入模板.xlsx";
    		List<SysUserOnline> list = Lists.newArrayList(); 
    		new ExportExcel("在线用户记录数据", SysUserOnline.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/sysUserOnline/?repage";
    }
	

}