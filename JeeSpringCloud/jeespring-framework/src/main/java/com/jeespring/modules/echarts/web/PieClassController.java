/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeespring.org/">JeeSpring</a> All rights reserved.
 */
package com.jeespring.modules.echarts.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

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
import com.jeespring.common.utils.MyBeanUtils;
import com.jeespring.common.config.Global;
import com.jeespring.common.persistence.Page;
import com.jeespring.common.web.AbstractBaseController;
import com.jeespring.common.utils.StringUtils;
import com.jeespring.common.utils.excel.ExportExcel;
import com.jeespring.common.utils.excel.ImportExcel;
import com.jeespring.modules.echarts.entity.PieClass;
import com.jeespring.modules.echarts.service.PieClassService;

/**
 * 班级Controller
 * @author lgf
 * @version 2016-05-26
 */
@Controller
@RequestMapping(value = "${adminPath}/echarts/pieClass")
public class PieClassController extends AbstractBaseController {

	@Autowired
	private PieClassService pieClassService;
	
	@ModelAttribute
	public PieClass get(@RequestParam(required=false) String id) {
		PieClass entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = pieClassService.get(id);
		}
		if (entity == null){
			entity = new PieClass();
		}
		return entity;
	}
	
	/**
	 * 班级列表页面
	 */
	@RequiresPermissions("echarts:pieClass:list")
	@RequestMapping(value = {"list", ""})
	public String list(PieClass pieClass, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PieClass> page = pieClassService.findPage(new Page<PieClass>(request, response), pieClass); 
		model.addAttribute("page", page);
		
		//生成报表数据
		Map<String,Object> orientData = new HashMap<String,Object>();
		List<PieClass> list = pieClassService.findList(pieClass);
		for(PieClass pie:list){
			orientData.put(pie.getClassName(), pie.getNum());
		}
		model.addAttribute("orientData", orientData);
		return "modules/echarts/pieClassList";
	}

	/**
	 * 查看，增加，编辑班级表单页面
	 */
	@RequiresPermissions(value={"echarts:pieClass:view","echarts:pieClass:add","echarts:pieClass:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(PieClass pieClass, Model model) {
		model.addAttribute("pieClass", pieClass);
		return "modules/echarts/pieClassForm";
	}

	/**
	 * 保存班级
	 */
	@RequiresPermissions(value={"echarts:pieClass:add","echarts:pieClass:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(PieClass pieClass, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, pieClass)){
			return form(pieClass, model);
		}
		if(!pieClass.getIsNewRecord()){//编辑表单保存
			PieClass t = pieClassService.get(pieClass.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(pieClass, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			pieClassService.save(t);//保存
		}else{//新增表单保存
			pieClassService.save(pieClass);//保存
		}
		addMessage(redirectAttributes, "保存班级成功");
		return "redirect:"+Global.getAdminPath()+"/echarts/pieClass/?repage";
	}
	
	/**
	 * 删除班级
	 */
	@RequiresPermissions("echarts:pieClass:del")
	@RequestMapping(value = "delete")
	public String delete(PieClass pieClass, RedirectAttributes redirectAttributes) {
		pieClassService.delete(pieClass);
		addMessage(redirectAttributes, "删除班级成功");
		return "redirect:"+Global.getAdminPath()+"/echarts/pieClass/?repage";
	}
	
	/**
	 * 批量删除班级
	 */
	@RequiresPermissions("echarts:pieClass:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
        String[] idArray = ids.split(",");
		for(String id : idArray){
			pieClassService.delete(pieClassService.get(id));
		}
		addMessage(redirectAttributes, "删除班级成功");
		return "redirect:"+Global.getAdminPath()+"/echarts/pieClass/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("echarts:pieClass:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(PieClass pieClass, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "班级"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<PieClass> page = pieClassService.findPage(new Page<PieClass>(request, response, -1), pieClass);
    		new ExportExcel("班级", PieClass.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出班级记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/echarts/pieClass/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("echarts:pieClass:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<PieClass> list = ei.getDataList(PieClass.class);
			for (PieClass pieClass : list){
				try{
					pieClassService.save(pieClass);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条班级记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条班级记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入班级失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/echarts/pieClass/?repage";
    }
	
	/**
	 * 下载导入班级数据模板
	 */
	@RequiresPermissions("echarts:pieClass:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "班级数据导入模板.xlsx";
    		List<PieClass> list = Lists.newArrayList(); 
    		new ExportExcel("班级数据", PieClass.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/echarts/pieClass/?repage";
    }
	
	
	

}