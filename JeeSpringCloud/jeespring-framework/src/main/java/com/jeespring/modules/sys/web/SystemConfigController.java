/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeespring.org/">JeeSpring</a> All rights reserved.
 */
package com.jeespring.modules.sys.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.jeespring.common.config.Global;
import com.jeespring.common.json.AjaxJson;
import com.jeespring.common.persistence.Page;
import com.jeespring.common.utils.DateUtils;
import com.jeespring.common.utils.MyBeanUtils;
import com.jeespring.common.utils.StringUtils;
import com.jeespring.common.utils.excel.ExportExcel;
import com.jeespring.common.utils.excel.ImportExcel;
import com.jeespring.common.web.AbstractBaseController;
import com.jeespring.modules.sys.entity.SystemConfig;
import com.jeespring.modules.sys.service.SystemConfigService;

/**
 * 系统配置Controller
 * @author liugf
 * @version 2016-02-07
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/systemConfig")
public class SystemConfigController extends AbstractBaseController {

	@Autowired
	private SystemConfigService systemConfigService;

	@ModelAttribute
	public SystemConfig get(@RequestParam(required=false) String id) {
		SystemConfig entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = systemConfigService.get(id);
		}
		if (entity == null){
			entity = new SystemConfig();
		}
		return entity;
	}
	
	/**
	 * 系统配置列表页面
	 */
	@RequiresPermissions("sys:systemConfig:index")
	@RequestMapping(value = {"index", ""})
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		SystemConfig systemConfig = systemConfigService.get("1");
		model.addAttribute("systemConfig", systemConfig);
		return "modules/sys/systemConfig";
	}


	/**
	 * 保存系统配置
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxJson save(SystemConfig systemConfig, Model model, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String message = "保存系统配置成功";
		SystemConfig t = systemConfigService.get("1");
		try {
			systemConfig.setId("1");
			MyBeanUtils.copyBeanNotNull2Bean(systemConfig, t);
			systemConfigService.save(t);
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
			message = "保存系统配置失败";
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 删除系统配置
	 */
	@RequiresPermissions("sys:systemConfig:del")
	@RequestMapping(value = "delete")
	public String delete(SystemConfig systemConfig, RedirectAttributes redirectAttributes) {
		systemConfigService.delete(systemConfig);
		addMessage(redirectAttributes, "删除系统配置成功");
		return "redirect:"+Global.getAdminPath()+"/sys/systemConfig/?repage";
	}
	
	/**
	 * 批量删除系统配置
	 */
	@RequiresPermissions("sys:systemConfig:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
        String[] idArray = ids.split(",");
		for(String id : idArray){
			systemConfigService.delete(systemConfigService.get(id));
		}
		addMessage(redirectAttributes, "删除系统配置成功");
		return "redirect:"+Global.getAdminPath()+"/sys/systemConfig/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("sys:systemConfig:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(SystemConfig systemConfig, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "系统配置"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SystemConfig> page = systemConfigService.findPage(new Page<SystemConfig>(request, response, -1), systemConfig);
    		new ExportExcel("系统配置", SystemConfig.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出系统配置记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/systemConfig/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:systemConfig:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<SystemConfig> list = ei.getDataList(SystemConfig.class);
			for (SystemConfig systemConfig : list){
				systemConfigService.save(systemConfig);
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条系统配置记录");
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入系统配置失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/systemConfig/?repage";
    }
	
	/**
	 * 下载导入系统配置数据模板
	 */
	@RequiresPermissions("sys:systemConfig:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "系统配置数据导入模板.xlsx";
    		List<SystemConfig> list = Lists.newArrayList(); 
    		new ExportExcel("系统配置数据", SystemConfig.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/systemConfig/?repage";
    }
	

}