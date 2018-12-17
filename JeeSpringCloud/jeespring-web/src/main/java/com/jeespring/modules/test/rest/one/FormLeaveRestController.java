/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/jeeSpringCloud">JeeSpringCloud</a> All rights reserved..
 */
package com.jeespring.modules.test.rest.one;

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
import com.jeespring.modules.test.entity.one.FormLeave;
import com.jeespring.modules.test.service.one.IFormLeaveService;
import org.springframework.web.bind.annotation.RestController;
import com.jeespring.common.web.Result;
import com.jeespring.common.web.ResultFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * 请假Controller
 * @author JeeSpring
 * @version 2018-10-12
 */
@RestController
@RequestMapping(value = "/rest/test/one/formLeave")
@Api(value="请假接口", description="请假接口")
public class FormLeaveRestController extends AbstractBaseController {

	@Autowired
	private IFormLeaveService formLeaveService;

	/**
	 * 请假信息
	 */
	@RequestMapping(value = {"get"},method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="请假信息(Content-Type为text/html)", notes="请假信息(Content-Type为text/html)")
	@ApiImplicitParam(name = "id", value = "请假id", required = false, dataType = "String",paramType="query")
	public Result getRequestParam(@RequestParam(required=false) String id) {
		return get(id);
	}

	@RequestMapping(value = {"get/json"},method ={RequestMethod.POST})
	@ApiOperation(value="请假信息(Content-Type为application/json)", notes="请假信息(Content-Type为application/json)")
	@ApiImplicitParam(name = "id", value = "请假id", required = false, dataType = "String",paramType="body")
	public Result getRequestBody(@RequestBody(required=false) String id) {
		return get(id);
	}

	private Result get(String id) {
		FormLeave entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = formLeaveService.getCache(id);
			//entity = formLeaveService.get(id);
		}
		if (entity == null){
			entity = new FormLeave();
		}
		Result result = ResultFactory.getSuccessResult();
		result.setResultObject(entity);
		return result;
	}

	/**
	 * 请假列表(不包含页信息)
	 */
	//RequiresPermissions("test:one:formLeave:findList")
	@RequestMapping(value = {"findList"},method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="请假列表(不包含页信息)(Content-Type为text/html)", notes="请假列表(不包含页信息)(Content-Type为text/html)")
	@ApiImplicitParam(name = "formLeave", value = "请假", dataType = "FormLeave",paramType="query")
	public Result findListRequestParam(FormLeave formLeave, HttpServletRequest request, HttpServletResponse response, Model model) {
		return findList( formLeave,model);
	}

	@RequestMapping(value = {"findList/json"},method ={RequestMethod.POST})
	@ApiOperation(value="请假列表(不包含页信息)(Content-Type为application/json)", notes="请假列表(不包含页信息)(Content-Type为application/json)")
	@ApiImplicitParam(name = "formLeave", value = "请假", dataType = "FormLeave",paramType="body")
	public Result findListRequestBody(@RequestBody FormLeave formLeave, Model model) {
		return findList( formLeave,model);
	}

	private Result findList(FormLeave formLeave, Model model) {
		List<FormLeave> list = formLeaveService.findListCache(formLeave);
		//List<FormLeave> list = formLeaveService.findList(formLeave);
		Result result = ResultFactory.getSuccessResult();
		result.setResultObject(list);
		return result;
	}

	/**
	 * 请假列表(包含页信息)
	 */
	//RequiresPermissions("test:one:formLeave:list")
	@RequestMapping(value = {"list"},method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="请假列表(包含页信息)(Content-Type为text/html)", notes="请假列表(包含页信息)(Content-Type为text/html)")
	@ApiImplicitParam(name = "formLeave", value = "请假", dataType = "FormLeave",paramType="query")
	public Result listRequestParam(FormLeave formLeave, HttpServletRequest request, HttpServletResponse response, Model model) {
		return list(formLeave,model);
	}

	@RequestMapping(value = {"list/json"},method ={RequestMethod.POST})
	@ApiOperation(value="请假列表(包含页信息)(Content-Type为application/json)", notes="请假列表(包含页信息)(Content-Type为application/json)")
	@ApiImplicitParam(name = "formLeave", value = "请假", dataType = "FormLeave",paramType="body")
	public Result listRequestBody(@RequestBody FormLeave formLeave, Model model) {
		return list(formLeave,model);
	}

	private Result list(FormLeave formLeave, Model model) {
		Page<FormLeave> page = formLeaveService.findPageCache(new Page<FormLeave>(formLeave.getPageNo(),formLeave.getPageSize(),formLeave.getOrderBy()), formLeave);
		//Page<FormLeave> page = formLeaveService.findPage(new Page<FormLeave>(formLeave.getPageNo(),formLeave.getPageSize(),formLeave.getOrderBy()), formLeave);
		Result result = ResultFactory.getSuccessResult();
		result.setResultObject(page);
		return result;
	}

	/**
	 * 请假获取列表第一条记录
	 */
	//RequiresPermissions("test:one:formLeave:listFrist")
	@RequestMapping(value = {"listFrist"},method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="请假获取列表第一条记录(Content-Type为text/html)", notes="请假获取列表第一条记录(Content-Type为text/html)")
	@ApiImplicitParam(name = "formLeave", value = "请假", dataType = "FormLeave",paramType="query")
	public Result listFristRequestParam(FormLeave formLeave, HttpServletRequest request, HttpServletResponse response, Model model) {
		return listFrist(formLeave,model);
	}

	@RequestMapping(value = {"listFrist/json"},method ={RequestMethod.POST})
	@ApiOperation(value="请假获取列表第一条记录(Content-Type为application/json)", notes="请假获取列表第一条记录(Content-Type为application/json)")
	@ApiImplicitParam(name = "formLeave", value = "请假", dataType = "FormLeave",paramType="body")
	public Result listFristRequestBody(@RequestBody FormLeave formLeave, Model model) {
		return listFrist(formLeave,model);
	}

	private Result listFrist(FormLeave formLeave, Model model) {
		Page<FormLeave> page = formLeaveService.findPageCache(new Page<FormLeave>(formLeave.getPageNo(),formLeave.getPageSize(),formLeave.getOrderBy()), formLeave);
		//Page<FormLeave> page = formLeaveService.findPage(new Page<FormLeave>(formLeave.getPageNo(),formLeave.getPageSize(),formLeave.getOrderBy()), formLeave);
		Result result = ResultFactory.getSuccessResult();
		if(page.getList().size()>0){
			result.setResultObject(page.getList().get(0));
		}else{
			result=ResultFactory.getErrorResult("没有记录！");
		}
		return result;
	}

	/**
	 * 保存请假
	 */
	//RequiresPermissions(value={"test:one:formLeave:add","test:one:formLeave:edit"},logical=Logical.OR)
	@RequestMapping(value = "save",method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="保存请假(Content-Type为text/html)", notes="保存请假(Content-Type为text/html)")
	@ApiImplicitParam(name = "formLeave", value = "请假", dataType = "FormLeave",paramType="query")
	public Result saveRequestParam(FormLeave formLeave, Model model, RedirectAttributes redirectAttributes) {
		return save(formLeave,model,redirectAttributes);
	}

	@RequestMapping(value = "save/json",method ={RequestMethod.POST})
	@ApiOperation(value="保存请假(Content-Type为application/json)", notes="保存请假(Content-Type为application/json)")
	@ApiImplicitParam(name = "formLeave", value = "请假", dataType = "FormLeave",paramType="body")
	public Result saveRequestBody(@RequestBody FormLeave formLeave, Model model, RedirectAttributes redirectAttributes) {
		return save(formLeave,model,redirectAttributes);
	}

	private Result save(FormLeave formLeave, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, formLeave)){
			Result result = ResultFactory.getErrorResult("数据验证失败");
		}
		formLeaveService.save(formLeave);
		Result result = ResultFactory.getSuccessResult("保存请假成功");
		return result;
	}

	/**
	 * 删除请假
	 */
	//RequiresPermissions("test:one:formLeave:del")
	@RequestMapping(value = "delete",method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="删除请假(Content-Type为text/html)", notes="删除请假(Content-Type为text/html)")
	@ApiImplicitParam(name = "formLeave", value = "请假", dataType = "FormLeave",paramType="query")
	public Result deleteRequestParam(FormLeave formLeave, RedirectAttributes redirectAttributes) {
		return delete(formLeave,redirectAttributes);
	}

	@RequestMapping(value = "delete/json",method ={RequestMethod.POST})
	@ApiOperation(value="删除请假(Content-Type为application/json)", notes="删除请假(Content-Type为application/json)")
	@ApiImplicitParam(name = "formLeave", value = "请假", dataType = "FormLeave",paramType="body")
	public Result deleteRequestBody(@RequestBody FormLeave formLeave, RedirectAttributes redirectAttributes) {
		return delete(formLeave,redirectAttributes);
	}

	private Result delete(FormLeave formLeave, RedirectAttributes redirectAttributes) {
		formLeaveService.delete(formLeave);
		Result result = ResultFactory.getSuccessResult("删除请假成功");
		return result;
	}

	/**
	 * 删除请假（逻辑删除，更新del_flag字段为1,在表包含字段del_flag时，可以调用此方法，将数据隐藏）
	 */
	@RequestMapping(value = "deleteByLogic",method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="逻辑删除请假(Content-Type为text/html)", notes="逻辑删除请假(Content-Type为text/html)")
	@ApiImplicitParam(name = "formLeave", value = "请假", dataType = "FormLeave",paramType="query")
	public Result deleteByLogicRequestParam(FormLeave formLeave, RedirectAttributes redirectAttributes) {
		return deleteByLogic(formLeave,redirectAttributes);
	}

	/**
	 * 删除请假（逻辑删除，更新del_flag字段为1,在表包含字段del_flag时，可以调用此方法，将数据隐藏）
	 */
	@RequestMapping(value = "deleteByLogic/json",method ={RequestMethod.POST})
	@ApiOperation(value="逻辑删除请假(Content-Type为application/json)", notes="逻辑删除请假(Content-Type为application/json)")
	@ApiImplicitParam(name = "formLeave", value = "请假", dataType = "FormLeave",paramType="body")
	public Result deleteByLogicRequestBody(@RequestBody FormLeave formLeave, RedirectAttributes redirectAttributes) {
		return deleteByLogic(formLeave,redirectAttributes);
	}

	private Result deleteByLogic(FormLeave formLeave, RedirectAttributes redirectAttributes) {
		formLeaveService.deleteByLogic(formLeave);
		Result result = ResultFactory.getSuccessResult("删除请假成功");
		return result;
	}

	/**
	 * 批量删除请假
	 */
	//RequiresPermissions("test:one:formLeave:del")
	@RequestMapping(value = "deleteAll",method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="批量删除请假(Content-Type为text/html)", notes="批量删除请假(Content-Type为text/html)")
	@ApiImplicitParam(name = "ids", value = "请假ids,用,隔开", required = false, dataType = "String",paramType="query")
	public Result deleteAllRequestParam(String ids, RedirectAttributes redirectAttributes) {
		return deleteAll(ids,redirectAttributes);
	}

	@RequestMapping(value = "deleteAll/json",method ={RequestMethod.POST})
	@ApiOperation(value="批量删除请假(Content-Type为application/json)", notes="批量删除请假(Content-Type为application/json)")
	@ApiImplicitParam(name = "ids", value = "请假ids,用,隔开", required = false, dataType = "String",paramType="body")
	public Result deleteAllRequestBody(@RequestBody String ids, RedirectAttributes redirectAttributes) {
		return deleteAll(ids,redirectAttributes);
	}

	private Result deleteAll(String ids, RedirectAttributes redirectAttributes) {
        String[] idArray = ids.split(",");
		for(String id : idArray){
			formLeaveService.delete(formLeaveService.get(id));
		}
        Result result = ResultFactory.getSuccessResult("删除请假成功");
		return result;
	}

	/**
	 * 批量删除请假（逻辑删除，更新del_flag字段为1,在表包含字段del_flag时，可以调用此方法，将数据隐藏）
	 */
	@RequestMapping(value = "deleteAllByLogic",method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="逻辑批量删除请假(Content-Type为text/html)", notes="逻辑批量删除请假(Content-Type为text/html)")
	@ApiImplicitParam(name = "ids", value = "请假ids,用,隔开", required = false, dataType = "String",paramType="query")
	public Result deleteAllByLogicRequestParam(String ids, RedirectAttributes redirectAttributes) {
		return deleteAllByLogic(ids,redirectAttributes);
	}

	/**
	 * 批量删除请假（逻辑删除，更新del_flag字段为1,在表包含字段del_flag时，可以调用此方法，将数据隐藏）
	 */
	@RequestMapping(value = "deleteAllByLogic/json",method ={RequestMethod.POST})
	@ApiOperation(value="逻辑批量删除请假(Content-Type为application/json)", notes="逻辑批量删除请假(Content-Type为application/json)")
	@ApiImplicitParam(name = "ids", value = "请假ids,用,隔开", required = false, dataType = "String",paramType="body")
	public Result deleteAllByLogicRequestBody(@RequestBody String ids, RedirectAttributes redirectAttributes) {
		return deleteAllByLogic(ids,redirectAttributes);
	}

	private Result deleteAllByLogic(String ids, RedirectAttributes redirectAttributes) {
        String[] idArray = ids.split(",");
		for(String id : idArray){
			formLeaveService.deleteByLogic(formLeaveService.get(id));
		}
        Result result = ResultFactory.getSuccessResult("删除请假成功");
		return result;
	}

}