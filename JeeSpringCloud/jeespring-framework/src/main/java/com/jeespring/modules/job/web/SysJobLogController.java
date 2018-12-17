/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.jeespring.modules.job.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.jeespring.modules.job.entity.SysJobLog;
import com.jeespring.modules.job.service.SysJobLogService;
/**
 * 定时任务调度日志表Controller
 * @author JeeSpring
 * @version 2018-08-16
 */
@Controller
@RequestMapping(value = "${adminPath}/job/sysJobLog")
public class SysJobLogController extends AbstractBaseController {

	@Autowired
	private SysJobLogService sysJobLogService;
	@Autowired
	private SysConfigService sysConfigService;

	@ModelAttribute
	public SysJobLog get(@RequestParam(required=false) String id) {
		SysJobLog entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysJobLogService.getCache(id);
			//entity = sysJobLogService.get(id);
		}
		if (entity == null){
			entity = new SysJobLog();
		}
		return entity;
	}

	/**
	 * 定时任务调度日志统计页面
	 */
	@RequiresPermissions("job:sysJobLog:total")
	@RequestMapping(value = {"total"})
	public String totalView(SysJobLog sysJobLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		total(sysJobLog,request,response,model);
		return "modules/job/sysJobLogTotal";
	}
	private void total(SysJobLog sysJobLog, HttpServletRequest request, HttpServletResponse response, Model model) {
			if(StringUtils.isEmpty(sysJobLog.getTotalType())){
			sysJobLog.setTotalType("%Y-%m-%d");
		}
		//X轴的数据
		List<String> xAxisData= new ArrayList<String>();
		//Y轴的数据
		Map<String,List<Double>> yAxisData = new HashMap<String,List<Double>>();
		List<Double> countList = new ArrayList<Double>();
		List<Double> sumList = new ArrayList<Double>();
		if(sysJobLog.getOrderBy()==""){
			sysJobLog.setOrderBy("totalDate");
		}
		List<SysJobLog> list = sysJobLogService.totalCache(sysJobLog);
		//List<SysJobLog> list = sysJobLogService.total(sysJobLog);
		model.addAttribute("list", list);
		for(SysJobLog sysJobLogItem:list){
			//x轴数据
			xAxisData.add( sysJobLogItem.getTotalDate());
			countList.add(Double.valueOf(sysJobLogItem.getTotalCount()));
		}
		yAxisData.put("数量", countList);
	    request.setAttribute("xAxisData", xAxisData);
		request.setAttribute("yAxisData", yAxisData);
		model.addAttribute("sumTotalCount", list.stream().mapToInt(SysJobLog::getTotalCount).sum());

		//饼图数据
		Map<String,Object> orientData= new HashMap<String,Object>();
		for(SysJobLog sysJobLogItem:list){
			orientData.put(sysJobLogItem.getTotalDate(), sysJobLogItem.getTotalCount());
		}
		model.addAttribute("orientData", orientData);
	}
	@RequiresPermissions("job:sysJobLog:total")
	@RequestMapping(value = {"totalMap"})
	public String totalMap(SysJobLog sysJobLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(StringUtils.isEmpty(sysJobLog.getTotalType())){
			sysJobLog.setTotalType("%Y-%m-%d");
		}
		List<SysJobLog> list = sysJobLogService.totalCache(sysJobLog);
		//List<SysJobLog> list = sysJobLogService.total(sysJobLog);
		model.addAttribute("sumTotalCount", list.stream().mapToInt(SysJobLog::getTotalCount).sum());
		model.addAttribute("list", list);
		return "modules/job/sysJobLogTotalMap";
	}

	/**
	 * 定时任务调度日志列表页面
	 */
	@RequiresPermissions("job:sysJobLog:list")
	@RequestMapping(value = {"list", ""})
	public String list(SysJobLog sysJobLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysJobLog> page = sysJobLogService.findPageCache(new Page<SysJobLog>(request, response), sysJobLog);
		//Page<SysJobLog> page = sysJobLogService.findPage(new Page<SysJobLog>(request, response), sysJobLog);
		model.addAttribute("page", page);
		sysJobLog.setOrderBy("totalDate");
		total(sysJobLog,request,response,model);
		return "modules/job/sysJobLogList";
	}

	/**
	 * 定时任务调度日志列表页面
	 */
	@RequiresPermissions("job:sysJobLog:list")
	@RequestMapping(value = {"listVue"})
	public String listVue(SysJobLog sysJobLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysJobLog> page = sysJobLogService.findPageCache(new Page<SysJobLog>(request, response), sysJobLog);
		//Page<SysJobLog> page = sysJobLogService.findPage(new Page<SysJobLog>(request, response), sysJobLog);
		model.addAttribute("page", page);
		return "modules/job/sysJobLogListVue";
	}

	/**
	 * 定时任务调度日志列表页面
	 */
	//RequiresPermissions("job:sysJobLog:select")
	@RequestMapping(value = {"select"})
	public String select(SysJobLog sysJobLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysJobLog> page = sysJobLogService.findPageCache(new Page<SysJobLog>(request, response), sysJobLog);
		//Page<SysJobLog> page = sysJobLogService.findPage(new Page<SysJobLog>(request, response), sysJobLog);
		model.addAttribute("page", page);
		return "modules/job/sysJobLogSelect";
	}

	/**
	 * 查看，增加，编辑定时任务调度日志表单页面
	 */
	@RequiresPermissions(value={"job:sysJobLog:view","job:sysJobLog:add","job:sysJobLog:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(SysJobLog sysJobLog, Model model, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("action", request.getParameter("action"));
		model.addAttribute("sysJobLog", sysJobLog);
		if(request.getParameter("ViewFormType")!=null && "FormTwo".equals(request.getParameter("ViewFormType"))) {
            return "modules/job/sysJobLogFormTwo";
        }
		return "modules/job/sysJobLogForm";
	}

	/**
	 * 保存定时任务调度日志
	 */
	@RequiresPermissions(value={"job:sysJobLog:add","job:sysJobLog:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(SysJobLog sysJobLog, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {
		if(sysConfigService.isDemoMode()){
			addMessage(redirectAttributes, sysConfigService.isDemoModeDescription());
			return "redirect:" + adminPath + "/job/sysJobLog/?repage";
		}
		if (!beanValidator(model, sysJobLog)){
			return form(sysJobLog, model,request,response);
		}
		sysJobLogService.save(sysJobLog);
		addMessage(redirectAttributes, "保存定时任务调度日志成功");
		return "redirect:"+Global.getAdminPath()+"/job/sysJobLog/?repage";
	}

	/**
	 * 删除定时任务调度日志
	 */
	@RequiresPermissions("job:sysJobLog:del")
	@RequestMapping(value = "delete")
	public String delete(SysJobLog sysJobLog, RedirectAttributes redirectAttributes) {
		if(sysConfigService.isDemoMode()){
			addMessage(redirectAttributes, sysConfigService.isDemoModeDescription());
			return "redirect:" + adminPath + "/job/sysJobLog/?repage";
		}
		sysJobLogService.delete(sysJobLog);
		addMessage(redirectAttributes, "删除定时任务调度日志成功");
		return "redirect:"+Global.getAdminPath()+"/job/sysJobLog/?repage";
	}

	/**
	 * 删除定时任务调度日志（逻辑删除，更新del_flag字段为1,在表包含字段del_flag时，可以调用此方法，将数据隐藏）
	 */
	@RequiresPermissions(value={"job:sysJobLog:del","job:sysJobLog:delByLogic"},logical=Logical.OR)
	@RequestMapping(value = "deleteByLogic")
	public String deleteByLogic(SysJobLog sysJobLog, RedirectAttributes redirectAttributes) {
		if(sysConfigService.isDemoMode()){
			addMessage(redirectAttributes, sysConfigService.isDemoModeDescription());
			return "redirect:" + adminPath + "/job/sysJobLog/?repage";
		}
		sysJobLogService.deleteByLogic(sysJobLog);
		addMessage(redirectAttributes, "逻辑删除定时任务调度日志成功");
		return "redirect:"+Global.getAdminPath()+"/job/sysJobLog/?repage";
	}

	/**
	 * 批量删除定时任务调度日志
	 */
	@RequiresPermissions("job:sysJobLog:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		if(sysConfigService.isDemoMode()){
			addMessage(redirectAttributes, sysConfigService.isDemoModeDescription());
			return "redirect:" + adminPath + "/job/sysJobLog/?repage";
		}
        String[] idArray = ids.split(",");
		for(String id : idArray){
			sysJobLogService.delete(sysJobLogService.get(id));
		}
		addMessage(redirectAttributes, "删除定时任务调度日志成功");
		return "redirect:"+Global.getAdminPath()+"/job/sysJobLog/?repage";
	}

	/**
	 * 批量删除定时任务调度日志（逻辑删除，更新del_flag字段为1,在表包含字段del_flag时，可以调用此方法，将数据隐藏）
	 */
	@RequiresPermissions(value={"job:sysJobLog:del","job:sysJobLog:delByLogic"},logical=Logical.OR)
	@RequestMapping(value = "deleteAllByLogic")
	public String deleteAllByLogic(String ids, RedirectAttributes redirectAttributes) {
		if(sysConfigService.isDemoMode()){
			addMessage(redirectAttributes, sysConfigService.isDemoModeDescription());
			return "redirect:" + adminPath + "/job/sysJobLog/?repage";
		}
        String[] idArray = ids.split(",");
		for(String id : idArray){
			sysJobLogService.deleteByLogic(sysJobLogService.get(id));
		}
		addMessage(redirectAttributes, "删除定时任务调度日志成功");
		return "redirect:"+Global.getAdminPath()+"/job/sysJobLog/?repage";
	}

	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("job:sysJobLog:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(SysJobLog sysJobLog, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "定时任务调度日志"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SysJobLog> page = sysJobLogService.findPage(new Page<SysJobLog>(request, response, -1), sysJobLog);
    		new ExportExcel("定时任务调度日志", SysJobLog.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出定时任务调度日志记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/job/sysJobLog/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("job:sysJobLog:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<SysJobLog> list = ei.getDataList(SysJobLog.class);
			for (SysJobLog sysJobLog : list){
				sysJobLogService.save(sysJobLog);
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条定时任务调度日志记录");
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入定时任务调度日志失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/job/sysJobLog/?repage";
    }
	
	/**
	 * 下载导入定时任务调度日志数据模板
	 */
	@RequiresPermissions("job:sysJobLog:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "定时任务调度日志数据导入模板.xlsx";
    		List<SysJobLog> list = Lists.newArrayList(); 
    		new ExportExcel("定时任务调度日志数据", SysJobLog.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/job/sysJobLog/?repage";
    }
	

}