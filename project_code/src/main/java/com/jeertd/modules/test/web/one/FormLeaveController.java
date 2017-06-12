/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeertd.org/">jeertd</a> All rights reserved.
 */
package com.jeertd.modules.test.web.one;

import java.util.List;

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
import com.jeertd.common.config.Global;
import com.jeertd.common.persistence.Page;
import com.jeertd.common.utils.DateUtils;
import com.jeertd.common.utils.StringUtils;
import com.jeertd.common.utils.excel.ExportExcel;
import com.jeertd.common.utils.excel.ImportExcel;
import com.jeertd.common.web.BaseController;
import com.jeertd.modules.test.entity.one.FormLeave;
import com.jeertd.modules.test.service.one.FormLeaveService;

/**
 * 员工请假Controller
 * @author liugf
 * @version 2016-01-15
 */
@Controller
@RequestMapping(value = "${adminPath}/test/one/formLeave")
public class FormLeaveController extends BaseController {

	@Autowired
	private FormLeaveService formLeaveService;
	
	@ModelAttribute
	public FormLeave get(@RequestParam(required=false) String id) {
		FormLeave entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = formLeaveService.get(id);
		}
		if (entity == null){
			entity = new FormLeave();
		}
		return entity;
	}
	
	/**
	 * 请假单列表页面
	 */
	@RequiresPermissions("test:one:formLeave:list")
	@RequestMapping(value = {"list", ""})
	public String list(FormLeave formLeave, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FormLeave> page = formLeaveService.findPage(new Page<FormLeave>(request, response), formLeave); 
		model.addAttribute("page", page);
		return "modules/test/one/formLeaveList";
	}

	/**
	 * 查看，增加，编辑请假单表单页面
	 */
	@RequiresPermissions(value={"test:one:formLeave:view","test:one:formLeave:add","test:one:formLeave:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(FormLeave formLeave, Model model) {
		model.addAttribute("formLeave", formLeave);
		return "modules/test/one/formLeaveForm";
	}

	/**
	 * 保存请假单
	 */
	@RequiresPermissions(value={"test:one:formLeave:add","test:one:formLeave:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(FormLeave formLeave, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, formLeave)){
			return form(formLeave, model);
		}
		formLeaveService.save(formLeave);
		addMessage(redirectAttributes, "保存请假单成功");
		return "redirect:"+Global.getAdminPath()+"/test/one/formLeave/?repage";
	}
	
	/**
	 * 删除请假单
	 */
	@RequiresPermissions("test:one:formLeave:del")
	@RequestMapping(value = "delete")
	public String delete(FormLeave formLeave, RedirectAttributes redirectAttributes) {
		formLeaveService.delete(formLeave);
		addMessage(redirectAttributes, "删除请假单成功");
		return "redirect:"+Global.getAdminPath()+"/test/one/formLeave/?repage";
	}
	
	/**
	 * 批量删除请假单
	 */
	@RequiresPermissions("test:one:formLeave:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			formLeaveService.delete(formLeaveService.get(id));
		}
		addMessage(redirectAttributes, "删除请假单成功");
		return "redirect:"+Global.getAdminPath()+"/test/one/formLeave/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("test:one:formLeave:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(FormLeave formLeave, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "请假单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<FormLeave> page = formLeaveService.findPage(new Page<FormLeave>(request, response, -1), formLeave);
    		new ExportExcel("请假单", FormLeave.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出请假单记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/test/one/formLeave/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("test:one:formLeave:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<FormLeave> list = ei.getDataList(FormLeave.class);
			for (FormLeave formLeave : list){
				formLeaveService.save(formLeave);
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条请假单记录");
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入请假单失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/test/one/formLeave/?repage";
    }
	
	/**
	 * 下载导入请假单数据模板
	 */
	@RequiresPermissions("test:one:formLeave:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "请假单数据导入模板.xlsx";
    		List<FormLeave> list = Lists.newArrayList(); 
    		new ExportExcel("请假单数据", FormLeave.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/test/one/formLeave/?repage";
    }
	

}