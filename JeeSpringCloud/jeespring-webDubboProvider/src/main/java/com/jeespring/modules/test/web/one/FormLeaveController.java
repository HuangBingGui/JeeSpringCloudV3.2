/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/jeeSpringCloud">JeeSpringCloud</a> All rights reserved..
 */
package com.jeespring.modules.test.web.one;

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
import com.jeespring.modules.test.entity.one.FormLeave;
import com.jeespring.modules.test.service.one.FormLeaveService;
import com.jeespring.modules.test.service.one.IFormLeaveService;
//import com.alibaba.dubbo.config.annotation.Reference;

/**
 * 请假Controller
 * @author JeeSpring
 * @version 2018-10-12
 */
@Controller
@RequestMapping(value = "${adminPath}/test/one/formLeave")
public class FormLeaveController extends AbstractBaseController {

	//调用dubbo服务器是，要去Reference注解,注解Autowired
	//@Reference(version = "1.0.0")
	@Autowired
	private IFormLeaveService formLeaveService;

	@ModelAttribute
	public FormLeave get(@RequestParam(required=false) String id) {
		FormLeave entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = formLeaveService.getCache(id);
			//entity = formLeaveService.get(id);
		}
		if (entity == null){
			entity = new FormLeave();
		}
		return entity;
	}

	/**
	 * 请假统计页面
	 */
	@RequiresPermissions("test:one:formLeave:total")
	@RequestMapping(value = {"total"})
	public String totalView(FormLeave formLeave, HttpServletRequest request, HttpServletResponse response, Model model) {
		total(formLeave,request,response,model);
		return "modules/test/one/formLeaveTotal";
	}
	private void total(FormLeave formLeave, HttpServletRequest request, HttpServletResponse response, Model model) {
			if(StringUtils.isEmpty(formLeave.getTotalType())){
			formLeave.setTotalType("%Y-%m-%d");
		}
		//X轴的数据
		List<String> xAxisData= new ArrayList<String>();
		//Y轴的数据
		Map<String,List<Double>> yAxisData = new HashMap<String,List<Double>>();
		List<Double> countList = new ArrayList<Double>();
		List<Double> sumList = new ArrayList<Double>();
		if(formLeave.getOrderBy()==""){
			formLeave.setOrderBy("totalDate");
		}
		List<FormLeave> list = formLeaveService.totalCache(formLeave);
		//List<FormLeave> list = formLeaveService.total(formLeave);
		model.addAttribute("list", list);
		for(FormLeave formLeaveItem:list){
			//x轴数据
			xAxisData.add( formLeaveItem.getTotalDate());
			countList.add(Double.valueOf(formLeaveItem.getTotalCount()));
		}
		yAxisData.put("数量", countList);
	    request.setAttribute("xAxisData", xAxisData);
		request.setAttribute("yAxisData", yAxisData);
		model.addAttribute("sumTotalCount", list.stream().mapToInt(FormLeave::getTotalCount).sum());

		//饼图数据
		Map<String,Object> orientData= new HashMap<String,Object>();
		for(FormLeave formLeaveItem:list){
			orientData.put(formLeaveItem.getTotalDate(), formLeaveItem.getTotalCount());
		}
		model.addAttribute("orientData", orientData);
	}
	@RequiresPermissions("test:one:formLeave:total")
	@RequestMapping(value = {"totalMap"})
	public String totalMap(FormLeave formLeave, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(StringUtils.isEmpty(formLeave.getTotalType())){
			formLeave.setTotalType("%Y-%m-%d");
		}
		List<FormLeave> list = formLeaveService.totalCache(formLeave);
		//List<FormLeave> list = formLeaveService.total(formLeave);
		model.addAttribute("sumTotalCount", list.stream().mapToInt(FormLeave::getTotalCount).sum());
		model.addAttribute("list", list);
		return "modules/test/one/formLeaveTotalMap";
	}

	/**
	 * 请假列表页面
	 */
	@RequiresPermissions("test:one:formLeave:list")
	@RequestMapping(value = {"list", ""})
	public String list(FormLeave formLeave, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FormLeave> page = formLeaveService.findPageCache(new Page<FormLeave>(request, response), formLeave);
		//Page<FormLeave> page = formLeaveService.findPage(new Page<FormLeave>(request, response), formLeave);
		model.addAttribute("page", page);
		formLeave.setOrderBy("totalDate");
		total(formLeave,request,response,model);
		return "modules/test/one/formLeaveList";
	}

	/**
	 * 请假列表页面
	 */
	@RequiresPermissions("test:one:formLeave:list")
	@RequestMapping(value = {"listVue"})
	public String listVue(FormLeave formLeave, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FormLeave> page = formLeaveService.findPageCache(new Page<FormLeave>(request, response), formLeave);
		//Page<FormLeave> page = formLeaveService.findPage(new Page<FormLeave>(request, response), formLeave);
		model.addAttribute("page", page);
		return "modules/test/one/formLeaveListVue";
	}

	/**
	 * 请假列表页面
	 */
	//RequiresPermissions("test:one:formLeave:select")
	@RequestMapping(value = {"select"})
	public String select(FormLeave formLeave, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FormLeave> page = formLeaveService.findPageCache(new Page<FormLeave>(request, response), formLeave);
		//Page<FormLeave> page = formLeaveService.findPage(new Page<FormLeave>(request, response), formLeave);
		model.addAttribute("page", page);
		return "modules/test/one/formLeaveSelect";
	}

	/**
	 * 查看，增加，编辑请假表单页面
	 */
	@RequiresPermissions(value={"test:one:formLeave:view","test:one:formLeave:add","test:one:formLeave:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(FormLeave formLeave, Model model, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("action", request.getParameter("action"));
		model.addAttribute("formLeave", formLeave);
		if(request.getParameter("ViewFormType")!=null && "FormTwo".equals(request.getParameter("ViewFormType"))) {
            return "modules/test/one/formLeaveFormTwo";
        }
		return "modules/test/one/formLeaveForm";
	}

	/**
	 * 保存请假
	 */
	@RequiresPermissions(value={"test:one:formLeave:add","test:one:formLeave:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(FormLeave formLeave, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {
		if (!beanValidator(model, formLeave)){
			return form(formLeave, model,request,response);
		}
		formLeaveService.save(formLeave);
		addMessage(redirectAttributes, "保存请假成功");
		return "redirect:"+Global.getAdminPath()+"/test/one/formLeave/?repage";
	}

	/**
	 * 删除请假
	 */
	@RequiresPermissions("test:one:formLeave:del")
	@RequestMapping(value = "delete")
	public String delete(FormLeave formLeave, RedirectAttributes redirectAttributes) {
		formLeaveService.delete(formLeave);
		addMessage(redirectAttributes, "删除请假成功");
		return "redirect:"+Global.getAdminPath()+"/test/one/formLeave/?repage";
	}

	/**
	 * 删除请假（逻辑删除，更新del_flag字段为1,在表包含字段del_flag时，可以调用此方法，将数据隐藏）
	 */
	@RequiresPermissions(value={"test:one:formLeave:del","test:one:formLeave:delByLogic"},logical=Logical.OR)
	@RequestMapping(value = "deleteByLogic")
	public String deleteByLogic(FormLeave formLeave, RedirectAttributes redirectAttributes) {
		formLeaveService.deleteByLogic(formLeave);
		addMessage(redirectAttributes, "逻辑删除请假成功");
		return "redirect:"+Global.getAdminPath()+"/test/one/formLeave/?repage";
	}

	/**
	 * 批量删除请假
	 */
	@RequiresPermissions("test:one:formLeave:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String[] idArray = ids.split(",");
		for(String id : idArray){
			formLeaveService.delete(formLeaveService.get(id));
		}
		addMessage(redirectAttributes, "删除请假成功");
		return "redirect:"+Global.getAdminPath()+"/test/one/formLeave/?repage";
	}

	/**
	 * 批量删除请假（逻辑删除，更新del_flag字段为1,在表包含字段del_flag时，可以调用此方法，将数据隐藏）
	 */
	@RequiresPermissions(value={"test:one:formLeave:del","test:one:formLeave:delByLogic"},logical=Logical.OR)
	@RequestMapping(value = "deleteAllByLogic")
	public String deleteAllByLogic(String ids, RedirectAttributes redirectAttributes) {
		String[] idArray = ids.split(",");
		for(String id : idArray){
			formLeaveService.deleteByLogic(formLeaveService.get(id));
		}
		addMessage(redirectAttributes, "删除请假成功");
		return "redirect:"+Global.getAdminPath()+"/test/one/formLeave/?repage";
	}

	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("test:one:formLeave:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(FormLeave formLeave, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "请假"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<FormLeave> page = formLeaveService.findPage(new Page<FormLeave>(request, response, -1), formLeave);
    		new ExportExcel("请假", FormLeave.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出请假记录失败！失败信息："+e.getMessage());
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
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条请假记录");
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入请假失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/test/one/formLeave/?repage";
    }
	
	/**
	 * 下载导入请假数据模板
	 */
	@RequiresPermissions("test:one:formLeave:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "请假数据导入模板.xlsx";
    		List<FormLeave> list = Lists.newArrayList(); 
    		new ExportExcel("请假数据", FormLeave.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/test/one/formLeave/?repage";
    }
	

}