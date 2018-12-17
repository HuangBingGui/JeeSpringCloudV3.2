/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/jeeSpringCloud">JeeSpringCloud</a> All rights reserved..
 */
package com.jeespring.modules.test.web.onetomany;

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
import com.jeespring.modules.test.entity.onetomany.TestDataMain;
import com.jeespring.modules.test.service.onetomany.TestDataMainService;
import com.jeespring.modules.test.service.onetomany.ITestDataMainService;
//import com.alibaba.dubbo.config.annotation.Reference;

/**
 * 订票Controller
 * @author JeeSpring
 * @version 2018-10-12
 */
@Controller
@RequestMapping(value = "${adminPath}/test/onetomany/testDataMain")
public class TestDataMainController extends AbstractBaseController {

	//调用dubbo服务器是，要去Reference注解,注解Autowired
	//@Reference(version = "1.0.0")
	@Autowired
	private ITestDataMainService testDataMainService;

	@ModelAttribute
	public TestDataMain get(@RequestParam(required=false) String id) {
		TestDataMain entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = testDataMainService.getCache(id);
			//entity = testDataMainService.get(id);
		}
		if (entity == null){
			entity = new TestDataMain();
		}
		return entity;
	}

	/**
	 * 订票统计页面
	 */
	@RequiresPermissions("test:onetomany:testDataMain:total")
	@RequestMapping(value = {"total"})
	public String totalView(TestDataMain testDataMain, HttpServletRequest request, HttpServletResponse response, Model model) {
		total(testDataMain,request,response,model);
		return "modules/test/onetomany/testDataMainTotal";
	}
	private void total(TestDataMain testDataMain, HttpServletRequest request, HttpServletResponse response, Model model) {
			if(StringUtils.isEmpty(testDataMain.getTotalType())){
			testDataMain.setTotalType("%Y-%m-%d");
		}
		//X轴的数据
		List<String> xAxisData= new ArrayList<String>();
		//Y轴的数据
		Map<String,List<Double>> yAxisData = new HashMap<String,List<Double>>();
		List<Double> countList = new ArrayList<Double>();
		List<Double> sumList = new ArrayList<Double>();
		if(testDataMain.getOrderBy()==""){
			testDataMain.setOrderBy("totalDate");
		}
		List<TestDataMain> list = testDataMainService.totalCache(testDataMain);
		//List<TestDataMain> list = testDataMainService.total(testDataMain);
		model.addAttribute("list", list);
		for(TestDataMain testDataMainItem:list){
			//x轴数据
			xAxisData.add( testDataMainItem.getTotalDate());
			countList.add(Double.valueOf(testDataMainItem.getTotalCount()));
		}
		yAxisData.put("数量", countList);
	    request.setAttribute("xAxisData", xAxisData);
		request.setAttribute("yAxisData", yAxisData);
		model.addAttribute("sumTotalCount", list.stream().mapToInt(TestDataMain::getTotalCount).sum());

		//饼图数据
		Map<String,Object> orientData= new HashMap<String,Object>();
		for(TestDataMain testDataMainItem:list){
			orientData.put(testDataMainItem.getTotalDate(), testDataMainItem.getTotalCount());
		}
		model.addAttribute("orientData", orientData);
	}
	@RequiresPermissions("test:onetomany:testDataMain:total")
	@RequestMapping(value = {"totalMap"})
	public String totalMap(TestDataMain testDataMain, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(StringUtils.isEmpty(testDataMain.getTotalType())){
			testDataMain.setTotalType("%Y-%m-%d");
		}
		List<TestDataMain> list = testDataMainService.totalCache(testDataMain);
		//List<TestDataMain> list = testDataMainService.total(testDataMain);
		model.addAttribute("sumTotalCount", list.stream().mapToInt(TestDataMain::getTotalCount).sum());
		model.addAttribute("list", list);
		return "modules/test/onetomany/testDataMainTotalMap";
	}

	/**
	 * 订票列表页面
	 */
	@RequiresPermissions("test:onetomany:testDataMain:list")
	@RequestMapping(value = {"list", ""})
	public String list(TestDataMain testDataMain, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TestDataMain> page = testDataMainService.findPageCache(new Page<TestDataMain>(request, response), testDataMain);
		//Page<TestDataMain> page = testDataMainService.findPage(new Page<TestDataMain>(request, response), testDataMain);
		model.addAttribute("page", page);
		testDataMain.setOrderBy("totalDate");
		total(testDataMain,request,response,model);
		return "modules/test/onetomany/testDataMainList";
	}

	/**
	 * 订票列表页面
	 */
	@RequiresPermissions("test:onetomany:testDataMain:list")
	@RequestMapping(value = {"listVue"})
	public String listVue(TestDataMain testDataMain, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TestDataMain> page = testDataMainService.findPageCache(new Page<TestDataMain>(request, response), testDataMain);
		//Page<TestDataMain> page = testDataMainService.findPage(new Page<TestDataMain>(request, response), testDataMain);
		model.addAttribute("page", page);
		return "modules/test/onetomany/testDataMainListVue";
	}

	/**
	 * 订票列表页面
	 */
	//RequiresPermissions("test:onetomany:testDataMain:select")
	@RequestMapping(value = {"select"})
	public String select(TestDataMain testDataMain, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TestDataMain> page = testDataMainService.findPageCache(new Page<TestDataMain>(request, response), testDataMain);
		//Page<TestDataMain> page = testDataMainService.findPage(new Page<TestDataMain>(request, response), testDataMain);
		model.addAttribute("page", page);
		return "modules/test/onetomany/testDataMainSelect";
	}

	/**
	 * 查看，增加，编辑订票表单页面
	 */
	@RequiresPermissions(value={"test:onetomany:testDataMain:view","test:onetomany:testDataMain:add","test:onetomany:testDataMain:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(TestDataMain testDataMain, Model model, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("action", request.getParameter("action"));
		model.addAttribute("testDataMain", testDataMain);
		if(request.getParameter("ViewFormType")!=null && "FormTwo".equals(request.getParameter("ViewFormType"))) {
            return "modules/test/onetomany/testDataMainFormTwo";
        }
		return "modules/test/onetomany/testDataMainForm";
	}

	/**
	 * 保存订票
	 */
	@RequiresPermissions(value={"test:onetomany:testDataMain:add","test:onetomany:testDataMain:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(TestDataMain testDataMain, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {
		if (!beanValidator(model, testDataMain)){
			return form(testDataMain, model,request,response);
		}
		testDataMainService.save(testDataMain);
		addMessage(redirectAttributes, "保存订票成功");
		return "redirect:"+Global.getAdminPath()+"/test/onetomany/testDataMain/?repage";
	}

	/**
	 * 删除订票
	 */
	@RequiresPermissions("test:onetomany:testDataMain:del")
	@RequestMapping(value = "delete")
	public String delete(TestDataMain testDataMain, RedirectAttributes redirectAttributes) {
		testDataMainService.delete(testDataMain);
		addMessage(redirectAttributes, "删除订票成功");
		return "redirect:"+Global.getAdminPath()+"/test/onetomany/testDataMain/?repage";
	}

	/**
	 * 删除订票（逻辑删除，更新del_flag字段为1,在表包含字段del_flag时，可以调用此方法，将数据隐藏）
	 */
	@RequiresPermissions(value={"test:onetomany:testDataMain:del","test:onetomany:testDataMain:delByLogic"},logical=Logical.OR)
	@RequestMapping(value = "deleteByLogic")
	public String deleteByLogic(TestDataMain testDataMain, RedirectAttributes redirectAttributes) {
		testDataMainService.deleteByLogic(testDataMain);
		addMessage(redirectAttributes, "逻辑删除订票成功");
		return "redirect:"+Global.getAdminPath()+"/test/onetomany/testDataMain/?repage";
	}

	/**
	 * 批量删除订票
	 */
	@RequiresPermissions("test:onetomany:testDataMain:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String[] idArray = ids.split(",");
		for(String id : idArray){
			testDataMainService.delete(testDataMainService.get(id));
		}
		addMessage(redirectAttributes, "删除订票成功");
		return "redirect:"+Global.getAdminPath()+"/test/onetomany/testDataMain/?repage";
	}

	/**
	 * 批量删除订票（逻辑删除，更新del_flag字段为1,在表包含字段del_flag时，可以调用此方法，将数据隐藏）
	 */
	@RequiresPermissions(value={"test:onetomany:testDataMain:del","test:onetomany:testDataMain:delByLogic"},logical=Logical.OR)
	@RequestMapping(value = "deleteAllByLogic")
	public String deleteAllByLogic(String ids, RedirectAttributes redirectAttributes) {
		String[] idArray = ids.split(",");
		for(String id : idArray){
			testDataMainService.deleteByLogic(testDataMainService.get(id));
		}
		addMessage(redirectAttributes, "删除订票成功");
		return "redirect:"+Global.getAdminPath()+"/test/onetomany/testDataMain/?repage";
	}

	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("test:onetomany:testDataMain:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(TestDataMain testDataMain, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "订票"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<TestDataMain> page = testDataMainService.findPage(new Page<TestDataMain>(request, response, -1), testDataMain);
    		new ExportExcel("订票", TestDataMain.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出订票记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/test/onetomany/testDataMain/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("test:onetomany:testDataMain:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<TestDataMain> list = ei.getDataList(TestDataMain.class);
			for (TestDataMain testDataMain : list){
				testDataMainService.save(testDataMain);
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条订票记录");
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入订票失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/test/onetomany/testDataMain/?repage";
    }
	
	/**
	 * 下载导入订票数据模板
	 */
	@RequiresPermissions("test:onetomany:testDataMain:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "订票数据导入模板.xlsx";
    		List<TestDataMain> list = Lists.newArrayList(); 
    		new ExportExcel("订票数据", TestDataMain.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/test/onetomany/testDataMain/?repage";
    }
	

}