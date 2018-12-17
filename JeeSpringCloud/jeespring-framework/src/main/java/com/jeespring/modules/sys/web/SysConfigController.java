/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.jeespring.modules.sys.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.jeespring.modules.sys.entity.SysConfig;
import com.jeespring.modules.sys.service.SysConfigService;
/**
 * 系统配置Controller
 * @author 黄炳桂 516821420@qq.com
 * @version 2017-11-17
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysConfig")
public class SysConfigController extends AbstractBaseController {

	@Autowired
	private SysConfigService sysConfigService;
	
	@ModelAttribute
	public SysConfig get(@RequestParam(required=false) String id) {
		SysConfig entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysConfigService.get(id);
		}
		if (entity == null){
			entity = new SysConfig();
		}
		return entity;
	}
	
	/**
	 * 系统配置列表页面
	 */
	//RequiresPermissions("sys:sysConfig:list")
	@RequestMapping(value = {"list", ""})
	public String list(SysConfig sysConfig, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysConfig> page = sysConfigService.findPage(new Page<SysConfig>(request, response), sysConfig); 
		model.addAttribute("page", page);
		return "modules/sys/sysConfigList";
	}

	/**
	 * 系统配置列表页面
	 */
	//RequiresPermissions("sys:sysConfig:select")
	@RequestMapping(value = {"select"})
	public String select(SysConfig sysConfig, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysConfig> page = sysConfigService.findPage(new Page<SysConfig>(request, response), sysConfig);
		model.addAttribute("page", page);
		return "modules/sys/sysConfigSelect";
	}

	/**
	 * 查看，增加，编辑系统配置表单页面
	 */
	//RequiresPermissions(value={"sys:sysConfig:view","sys:sysConfig:add","sys:sysConfig:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(SysConfig sysConfig, Model model) {
		model.addAttribute("sysConfig", sysConfig);
		return "modules/sys/sysConfigForm";
	}

	/**
	 * 保存系统配置
	 */
	//RequiresPermissions(value={"sys:sysConfig:add","sys:sysConfig:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(SysConfig sysConfig, Model model, RedirectAttributes redirectAttributes) {
		if(sysConfigService.isDemoMode()){
			addMessage(redirectAttributes, sysConfigService.isDemoModeDescription());
			return "redirect:" + adminPath + "/sys/sysConfig/?repage";
		}

		if (!beanValidator(model, sysConfig)){
			return form(sysConfig, model);
		}
		sysConfigService.save(sysConfig);
		addMessage(redirectAttributes, "保存系统配置成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysConfig/?repage";
	}

	/**
	 * 删除系统配置
	 */
	//RequiresPermissions("sys:sysConfig:del")
	@RequestMapping(value = "delete")
	public String delete(SysConfig sysConfig, RedirectAttributes redirectAttributes) {
		if(sysConfigService.isDemoMode()){
			addMessage(redirectAttributes, sysConfigService.isDemoModeDescription());
			return "redirect:" + adminPath + "/sys/sysConfig/?repage";
		}

		sysConfigService.delete(sysConfig);
		addMessage(redirectAttributes, "删除系统配置成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysConfig/?repage";
	}

	/**
	 * 删除系统配置（逻辑删除，更新del_flag字段为1,在表包含字段del_flag时，可以调用此方法，将数据隐藏）
	 */
	//RequiresPermissions("sys:sysConfig:delByLogic")
	@RequestMapping(value = "deleteByLogic")
	public String deleteByLogic(SysConfig sysConfig, RedirectAttributes redirectAttributes) {
		if(sysConfigService.isDemoMode()){
			addMessage(redirectAttributes, sysConfigService.isDemoModeDescription());
			return "redirect:" + adminPath + "/sys/sysConfig/?repage";
		}

		sysConfigService.deleteByLogic(sysConfig);
		addMessage(redirectAttributes, "逻辑删除系统配置成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysConfig/?repage";
	}

	/**
	 * 批量删除系统配置
	 */
	//RequiresPermissions("sys:sysConfig:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		if(sysConfigService.isDemoMode()){
			addMessage(redirectAttributes, sysConfigService.isDemoModeDescription());
			return "redirect:" + adminPath + "/sys/sysConfig/?repage";
		}

        String[] idArray = ids.split(",");
		for(String id : idArray){
			sysConfigService.delete(sysConfigService.get(id));
		}
		addMessage(redirectAttributes, "删除系统配置成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysConfig/?repage";
	}

	/**
	 * 批量删除系统配置（逻辑删除，更新del_flag字段为1,在表包含字段del_flag时，可以调用此方法，将数据隐藏）
	 */
	//RequiresPermissions("sys:sysConfig:delByLogic")
	@RequestMapping(value = "deleteAllByLogic")
	public String deleteAllByLogic(String ids, RedirectAttributes redirectAttributes) {
		if(sysConfigService.isDemoMode()){
			addMessage(redirectAttributes, sysConfigService.isDemoModeDescription());
			return "redirect:" + adminPath + "/sys/sysConfig/?repage";
		}

        String[] idArray = ids.split(",");
		for(String id : idArray){
			sysConfigService.deleteByLogic(sysConfigService.get(id));
		}
		addMessage(redirectAttributes, "删除系统配置成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysConfig/?repage";
	}

	/**
	 * 导出excel文件
	 */
	//RequiresPermissions("sys:sysConfig:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(SysConfig sysConfig, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "系统配置"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SysConfig> page = sysConfigService.findPage(new Page<SysConfig>(request, response, -1), sysConfig);
    		new ExportExcel("系统配置", SysConfig.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出系统配置记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/sysConfig/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	//RequiresPermissions("sys:sysConfig:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<SysConfig> list = ei.getDataList(SysConfig.class);
			for (SysConfig sysConfig : list){
				sysConfigService.save(sysConfig);
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条系统配置记录");
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入系统配置失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/sysConfig/?repage";
    }
	
	/**
	 * 下载导入系统配置数据模板
	 */
	//RequiresPermissions("sys:sysConfig:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "系统配置数据导入模板.xlsx";
    		List<SysConfig> list = Lists.newArrayList(); 
    		new ExportExcel("系统配置数据", SysConfig.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/sysConfig/?repage";
    }
	

}