/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/jeeSpringCloud">JeeSpringCloud</a> All rights reserved..
 */
package com.jeespring.modules.test.rest.onetomany;

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
import com.jeespring.common.utils.DateUtils;
import com.jeespring.common.config.Global;
import com.jeespring.common.persistence.Page;
import com.jeespring.common.web.AbstractBaseController;
import com.jeespring.common.utils.StringUtils;
import com.jeespring.common.utils.excel.ExportExcel;
import com.jeespring.common.utils.excel.ImportExcel;
import com.jeespring.modules.test.entity.onetomany.TestDataMain;
import com.jeespring.modules.test.service.onetomany.ITestDataMainService;
import org.springframework.web.bind.annotation.RestController;
import com.jeespring.common.web.Result;
import com.jeespring.common.web.ResultFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * 订票Controller
 * @author JeeSpring
 * @version 2018-10-12
 */
@RestController
@RequestMapping(value = "/rest/test/onetomany/testDataMain")
@Api(value="订票接口", description="订票接口")
public class TestDataMainRestController extends AbstractBaseController {

	@Autowired
	private ITestDataMainService testDataMainService;

	/**
	 * 订票信息
	 */
	@RequestMapping(value = {"get"},method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="订票信息(Content-Type为text/html)", notes="订票信息(Content-Type为text/html)")
	@ApiImplicitParam(name = "id", value = "订票id", required = false, dataType = "String",paramType="query")
	public Result getRequestParam(@RequestParam(required=false) String id) {
		return get(id);
	}

	@RequestMapping(value = {"get/json"},method ={RequestMethod.POST})
	@ApiOperation(value="订票信息(Content-Type为application/json)", notes="订票信息(Content-Type为application/json)")
	@ApiImplicitParam(name = "id", value = "订票id", required = false, dataType = "String",paramType="body")
	public Result getRequestBody(@RequestBody(required=false) String id) {
		return get(id);
	}

	private Result get(String id) {
		TestDataMain entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = testDataMainService.getCache(id);
			//entity = testDataMainService.get(id);
		}
		if (entity == null){
			entity = new TestDataMain();
		}
		Result result = ResultFactory.getSuccessResult();
		result.setResultObject(entity);
		return result;
	}

	/**
	 * 订票列表(不包含页信息)
	 */
	//RequiresPermissions("test:onetomany:testDataMain:findList")
	@RequestMapping(value = {"findList"},method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="订票列表(不包含页信息)(Content-Type为text/html)", notes="订票列表(不包含页信息)(Content-Type为text/html)")
	@ApiImplicitParam(name = "testDataMain", value = "订票", dataType = "TestDataMain",paramType="query")
	public Result findListRequestParam(TestDataMain testDataMain, HttpServletRequest request, HttpServletResponse response, Model model) {
		return findList( testDataMain,model);
	}

	@RequestMapping(value = {"findList/json"},method ={RequestMethod.POST})
	@ApiOperation(value="订票列表(不包含页信息)(Content-Type为application/json)", notes="订票列表(不包含页信息)(Content-Type为application/json)")
	@ApiImplicitParam(name = "testDataMain", value = "订票", dataType = "TestDataMain",paramType="body")
	public Result findListRequestBody(@RequestBody TestDataMain testDataMain, Model model) {
		return findList( testDataMain,model);
	}

	private Result findList(TestDataMain testDataMain, Model model) {
		List<TestDataMain> list = testDataMainService.findListCache(testDataMain);
		//List<TestDataMain> list = testDataMainService.findList(testDataMain);
		Result result = ResultFactory.getSuccessResult();
		result.setResultObject(list);
		return result;
	}

	/**
	 * 订票列表(包含页信息)
	 */
	//RequiresPermissions("test:onetomany:testDataMain:list")
	@RequestMapping(value = {"list"},method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="订票列表(包含页信息)(Content-Type为text/html)", notes="订票列表(包含页信息)(Content-Type为text/html)")
	@ApiImplicitParam(name = "testDataMain", value = "订票", dataType = "TestDataMain",paramType="query")
	public Result listRequestParam(TestDataMain testDataMain, HttpServletRequest request, HttpServletResponse response, Model model) {
		return list(testDataMain,model);
	}

	@RequestMapping(value = {"list/json"},method ={RequestMethod.POST})
	@ApiOperation(value="订票列表(包含页信息)(Content-Type为application/json)", notes="订票列表(包含页信息)(Content-Type为application/json)")
	@ApiImplicitParam(name = "testDataMain", value = "订票", dataType = "TestDataMain",paramType="body")
	public Result listRequestBody(@RequestBody TestDataMain testDataMain, Model model) {
		return list(testDataMain,model);
	}

	private Result list(TestDataMain testDataMain, Model model) {
		Page<TestDataMain> page = testDataMainService.findPageCache(new Page<TestDataMain>(testDataMain.getPageNo(),testDataMain.getPageSize(),testDataMain.getOrderBy()), testDataMain);
		//Page<TestDataMain> page = testDataMainService.findPage(new Page<TestDataMain>(testDataMain.getPageNo(),testDataMain.getPageSize(),testDataMain.getOrderBy()), testDataMain);
		Result result = ResultFactory.getSuccessResult();
		result.setResultObject(page);
		return result;
	}

	/**
	 * 订票获取列表第一条记录
	 */
	//RequiresPermissions("test:onetomany:testDataMain:listFrist")
	@RequestMapping(value = {"listFrist"},method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="订票获取列表第一条记录(Content-Type为text/html)", notes="订票获取列表第一条记录(Content-Type为text/html)")
	@ApiImplicitParam(name = "testDataMain", value = "订票", dataType = "TestDataMain",paramType="query")
	public Result listFristRequestParam(TestDataMain testDataMain, HttpServletRequest request, HttpServletResponse response, Model model) {
		return listFrist(testDataMain,model);
	}

	@RequestMapping(value = {"listFrist/json"},method ={RequestMethod.POST})
	@ApiOperation(value="订票获取列表第一条记录(Content-Type为application/json)", notes="订票获取列表第一条记录(Content-Type为application/json)")
	@ApiImplicitParam(name = "testDataMain", value = "订票", dataType = "TestDataMain",paramType="body")
	public Result listFristRequestBody(@RequestBody TestDataMain testDataMain, Model model) {
		return listFrist(testDataMain,model);
	}

	private Result listFrist(TestDataMain testDataMain, Model model) {
		Page<TestDataMain> page = testDataMainService.findPageCache(new Page<TestDataMain>(testDataMain.getPageNo(),testDataMain.getPageSize(),testDataMain.getOrderBy()), testDataMain);
		//Page<TestDataMain> page = testDataMainService.findPage(new Page<TestDataMain>(testDataMain.getPageNo(),testDataMain.getPageSize(),testDataMain.getOrderBy()), testDataMain);
		Result result = ResultFactory.getSuccessResult();
		if(page.getList().size()>0){
			result.setResultObject(page.getList().get(0));
		}else{
			result=ResultFactory.getErrorResult("没有记录！");
		}
		return result;
	}

	/**
	 * 保存订票
	 */
	//RequiresPermissions(value={"test:onetomany:testDataMain:add","test:onetomany:testDataMain:edit"},logical=Logical.OR)
	@RequestMapping(value = "save",method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="保存订票(Content-Type为text/html)", notes="保存订票(Content-Type为text/html)")
	@ApiImplicitParam(name = "testDataMain", value = "订票", dataType = "TestDataMain",paramType="query")
	public Result saveRequestParam(TestDataMain testDataMain, Model model, RedirectAttributes redirectAttributes) {
		return save(testDataMain,model,redirectAttributes);
	}

	@RequestMapping(value = "save/json",method ={RequestMethod.POST})
	@ApiOperation(value="保存订票(Content-Type为application/json)", notes="保存订票(Content-Type为application/json)")
	@ApiImplicitParam(name = "testDataMain", value = "订票", dataType = "TestDataMain",paramType="body")
	public Result saveRequestBody(@RequestBody TestDataMain testDataMain, Model model, RedirectAttributes redirectAttributes) {
		return save(testDataMain,model,redirectAttributes);
	}

	private Result save(TestDataMain testDataMain, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, testDataMain)){
			Result result = ResultFactory.getErrorResult("数据验证失败");
		}
		testDataMainService.save(testDataMain);
		Result result = ResultFactory.getSuccessResult("保存订票成功");
		return result;
	}

	/**
	 * 删除订票
	 */
	//RequiresPermissions("test:onetomany:testDataMain:del")
	@RequestMapping(value = "delete",method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="删除订票(Content-Type为text/html)", notes="删除订票(Content-Type为text/html)")
	@ApiImplicitParam(name = "testDataMain", value = "订票", dataType = "TestDataMain",paramType="query")
	public Result deleteRequestParam(TestDataMain testDataMain, RedirectAttributes redirectAttributes) {
		return delete(testDataMain,redirectAttributes);
	}

	@RequestMapping(value = "delete/json",method ={RequestMethod.POST})
	@ApiOperation(value="删除订票(Content-Type为application/json)", notes="删除订票(Content-Type为application/json)")
	@ApiImplicitParam(name = "testDataMain", value = "订票", dataType = "TestDataMain",paramType="body")
	public Result deleteRequestBody(@RequestBody TestDataMain testDataMain, RedirectAttributes redirectAttributes) {
		return delete(testDataMain,redirectAttributes);
	}

	private Result delete(TestDataMain testDataMain, RedirectAttributes redirectAttributes) {
		testDataMainService.delete(testDataMain);
		Result result = ResultFactory.getSuccessResult("删除订票成功");
		return result;
	}

	/**
	 * 删除订票（逻辑删除，更新del_flag字段为1,在表包含字段del_flag时，可以调用此方法，将数据隐藏）
	 */
	@RequestMapping(value = "deleteByLogic",method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="逻辑删除订票(Content-Type为text/html)", notes="逻辑删除订票(Content-Type为text/html)")
	@ApiImplicitParam(name = "testDataMain", value = "订票", dataType = "TestDataMain",paramType="query")
	public Result deleteByLogicRequestParam(TestDataMain testDataMain, RedirectAttributes redirectAttributes) {
		return deleteByLogic(testDataMain,redirectAttributes);
	}

	/**
	 * 删除订票（逻辑删除，更新del_flag字段为1,在表包含字段del_flag时，可以调用此方法，将数据隐藏）
	 */
	@RequestMapping(value = "deleteByLogic/json",method ={RequestMethod.POST})
	@ApiOperation(value="逻辑删除订票(Content-Type为application/json)", notes="逻辑删除订票(Content-Type为application/json)")
	@ApiImplicitParam(name = "testDataMain", value = "订票", dataType = "TestDataMain",paramType="body")
	public Result deleteByLogicRequestBody(@RequestBody TestDataMain testDataMain, RedirectAttributes redirectAttributes) {
		return deleteByLogic(testDataMain,redirectAttributes);
	}

	private Result deleteByLogic(TestDataMain testDataMain, RedirectAttributes redirectAttributes) {
		testDataMainService.deleteByLogic(testDataMain);
		Result result = ResultFactory.getSuccessResult("删除订票成功");
		return result;
	}

	/**
	 * 批量删除订票
	 */
	//RequiresPermissions("test:onetomany:testDataMain:del")
	@RequestMapping(value = "deleteAll",method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="批量删除订票(Content-Type为text/html)", notes="批量删除订票(Content-Type为text/html)")
	@ApiImplicitParam(name = "ids", value = "订票ids,用,隔开", required = false, dataType = "String",paramType="query")
	public Result deleteAllRequestParam(String ids, RedirectAttributes redirectAttributes) {
		return deleteAll(ids,redirectAttributes);
	}

	@RequestMapping(value = "deleteAll/json",method ={RequestMethod.POST})
	@ApiOperation(value="批量删除订票(Content-Type为application/json)", notes="批量删除订票(Content-Type为application/json)")
	@ApiImplicitParam(name = "ids", value = "订票ids,用,隔开", required = false, dataType = "String",paramType="body")
	public Result deleteAllRequestBody(@RequestBody String ids, RedirectAttributes redirectAttributes) {
		return deleteAll(ids,redirectAttributes);
	}

	private Result deleteAll(String ids, RedirectAttributes redirectAttributes) {
        String[] idArray = ids.split(",");
		for(String id : idArray){
			testDataMainService.delete(testDataMainService.get(id));
		}
        Result result = ResultFactory.getSuccessResult("删除订票成功");
		return result;
	}

	/**
	 * 批量删除订票（逻辑删除，更新del_flag字段为1,在表包含字段del_flag时，可以调用此方法，将数据隐藏）
	 */
	@RequestMapping(value = "deleteAllByLogic",method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="逻辑批量删除订票(Content-Type为text/html)", notes="逻辑批量删除订票(Content-Type为text/html)")
	@ApiImplicitParam(name = "ids", value = "订票ids,用,隔开", required = false, dataType = "String",paramType="query")
	public Result deleteAllByLogicRequestParam(String ids, RedirectAttributes redirectAttributes) {
		return deleteAllByLogic(ids,redirectAttributes);
	}

	/**
	 * 批量删除订票（逻辑删除，更新del_flag字段为1,在表包含字段del_flag时，可以调用此方法，将数据隐藏）
	 */
	@RequestMapping(value = "deleteAllByLogic/json",method ={RequestMethod.POST})
	@ApiOperation(value="逻辑批量删除订票(Content-Type为application/json)", notes="逻辑批量删除订票(Content-Type为application/json)")
	@ApiImplicitParam(name = "ids", value = "订票ids,用,隔开", required = false, dataType = "String",paramType="body")
	public Result deleteAllByLogicRequestBody(@RequestBody String ids, RedirectAttributes redirectAttributes) {
		return deleteAllByLogic(ids,redirectAttributes);
	}

	private Result deleteAllByLogic(String ids, RedirectAttributes redirectAttributes) {
        String[] idArray = ids.split(",");
		for(String id : idArray){
			testDataMainService.deleteByLogic(testDataMainService.get(id));
		}
        Result result = ResultFactory.getSuccessResult("删除订票成功");
		return result;
	}

}