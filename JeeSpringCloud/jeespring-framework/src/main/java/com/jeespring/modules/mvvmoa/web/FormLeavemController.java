/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.jeespring.modules.mvvmoa.web;

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
import org.springframework.web.bind.annotation.ResponseBody;
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
import com.jeespring.modules.mvvmoa.entity.FormLeavem;
import com.jeespring.modules.mvvmoa.service.FormLeavemService;

/**
 * 员工请假Controller
 * @author JeeSpring
 * @version 2017-07-17
 */
@Controller
@RequestMapping(value = "${adminPath}/mvvmoa/formLeavem")
public class FormLeavemController extends AbstractBaseController {

	@Autowired
	private FormLeavemService formLeavemService;
	
	@ModelAttribute
	public FormLeavem get(@RequestParam(required=false) String id) {
		FormLeavem entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = formLeavemService.get(id);
		}
		if (entity == null){
			entity = new FormLeavem();
		}
		return entity;
	}

	@RequestMapping(value = {"getjson"})
	@ResponseBody
	public FormLeavem getjson(@RequestParam(required=false) String id) {
		FormLeavem entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = formLeavemService.get(id);
		}
		if (entity == null){
			entity = new FormLeavem();
		}
		return entity;
	}
	
	/**
	 * 请假单列表页面
	 */
	//RequiresPermissions("mvvmoa:formLeavem:list")
	@RequestMapping(value = {"list", ""})
	public String list(FormLeavem formLeavem, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FormLeavem> page = formLeavemService.findPage(new Page<FormLeavem>(request, response), formLeavem); 
		model.addAttribute("page", page);
		return "modules/mvvmoa/formLeavemList";
	}

	@RequestMapping(value = {"listjson"})
	@ResponseBody
	public Page<FormLeavem> listjson(FormLeavem formLeavem, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FormLeavem> page = formLeavemService.findPage(new Page<FormLeavem>(request, response), formLeavem); 
		return page;
	}
	
	/**
	 * 查看，增加，编辑请假单表单页面
	 */
	//RequiresPermissions(value={"mvvmoa:formLeavem:view","mvvmoa:formLeavem:add","mvvmoa:formLeavem:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(FormLeavem formLeavem, Model model) {
		model.addAttribute("formLeavem", formLeavem);
		return "modules/mvvmoa/formLeavemForm";
	}

	/**
	 * 保存请假单
	 */
	//RequiresPermissions(value={"mvvmoa:formLeavem:add","mvvmoa:formLeavem:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(FormLeavem formLeavem, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, formLeavem)){
			return form(formLeavem, model);
		}
		formLeavemService.save(formLeavem);
		addMessage(redirectAttributes, "保存请假单成功");
		return "redirect:"+Global.getAdminPath()+"/mvvmoa/formLeavem/?repage";
	}
	
	@RequestMapping(value = "savejson")
	@ResponseBody
	public String savejson(FormLeavem formLeavem, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, formLeavem)){
			return form(formLeavem, model);
		}
		formLeavemService.save(formLeavem);
		//addMessage(redirectAttributes, "保存请假单成功");
		return "保存请假单成功";
	}
	
	/**
	 * 删除请假单
	 */
	//RequiresPermissions("mvvmoa:formLeavem:del")
	@RequestMapping(value = "delete")
	public String delete(FormLeavem formLeavem, RedirectAttributes redirectAttributes) {
		formLeavemService.delete(formLeavem);
		addMessage(redirectAttributes, "删除请假单成功");
		return "redirect:"+Global.getAdminPath()+"/mvvmoa/formLeavem/?repage";
	}
	
	@RequestMapping(value = "deletejson")
	@ResponseBody	
	public String deletejson(FormLeavem formLeavem, RedirectAttributes redirectAttributes) {
		formLeavemService.delete(formLeavem);
		//addMessage(redirectAttributes, "删除请假单成功");
		return "删除请假单成功";
	}
	
	/**
	 * 批量删除请假单
	 */
	//RequiresPermissions("mvvmoa:formLeavem:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
        String[] idArray = ids.split(",");
		for(String id : idArray){
			formLeavemService.delete(formLeavemService.get(id));
		}
		addMessage(redirectAttributes, "删除请假单成功");
		return "redirect:"+Global.getAdminPath()+"/mvvmoa/formLeavem/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	//RequiresPermissions("mvvmoa:formLeavem:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(FormLeavem formLeavem, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "请假单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<FormLeavem> page = formLeavemService.findPage(new Page<FormLeavem>(request, response, -1), formLeavem);
    		new ExportExcel("请假单", FormLeavem.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出请假单记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/mvvmoa/formLeavem/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	//RequiresPermissions("mvvmoa:formLeavem:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<FormLeavem> list = ei.getDataList(FormLeavem.class);
			for (FormLeavem formLeavem : list){
				formLeavemService.save(formLeavem);
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条请假单记录");
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入请假单失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/mvvmoa/formLeavem/?repage";
    }
	
	/**
	 * 下载导入请假单数据模板
	 */
	//RequiresPermissions("mvvmoa:formLeavem:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "请假单数据导入模板.xlsx";
    		List<FormLeavem> list = Lists.newArrayList(); 
    		new ExportExcel("请假单数据", FormLeavem.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/mvvmoa/formLeavem/?repage";
    }
	

}