/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/jeeSpringCloud">JeeSpringCloud</a> All rights reserved..
 */
package com.company.project.modules.ylttrip.rest;

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
import com.company.project.modules.ylttrip.entity.TfTicket;
import com.company.project.modules.ylttrip.service.ITfTicketService;
import org.springframework.web.bind.annotation.RestController;
import com.jeespring.common.web.Result;
import com.jeespring.common.web.ResultFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * 订单Controller
 * @author JeeSpring
 * @version 2018-12-13
 */
@RestController
@RequestMapping(value = "/rest/ylttrip/tfTicket")
@Api(value="订单接口", description="订单接口")
public class TfTicketRestController extends AbstractBaseController {

	//调用dubbo服务器是，要去Reference注解,注解Autowired
	//@Reference(version = "1.0.0")
	@Autowired
	private ITfTicketService tfTicketService;

	/**
	 * 订单信息
	 */
	@RequestMapping(value = {"get"},method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="订单信息(Content-Type为text/html)", notes="订单信息(Content-Type为text/html)")
	@ApiImplicitParam(name = "id", value = "订单id", required = false, dataType = "String",paramType="query")
	public Result getRequestParam(@RequestParam(required=false) String id) {
		return get(id);
	}

	@RequestMapping(value = {"get/json"},method ={RequestMethod.POST})
	@ApiOperation(value="订单信息(Content-Type为application/json)", notes="订单信息(Content-Type为application/json)")
	@ApiImplicitParam(name = "id", value = "订单id", required = false, dataType = "String",paramType="body")
	public Result getRequestBody(@RequestBody(required=false) String id) {
		return get(id);
	}

	private Result get(String id) {
		TfTicket entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tfTicketService.getCache(id);
			//entity = tfTicketService.get(id);
		}
		if (entity == null){
			entity = new TfTicket();
		}
		Result result = ResultFactory.getSuccessResult();
		result.setResultObject(entity);
		return result;
	}

	/**
	 * 订单列表(不包含页信息)
	 */
	//RequiresPermissions("ylttrip:tfTicket:findList")
	@RequestMapping(value = {"findList"},method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="订单列表(不包含页信息)(Content-Type为text/html)", notes="订单列表(不包含页信息)(Content-Type为text/html)")
	@ApiImplicitParam(name = "tfTicket", value = "订单", dataType = "TfTicket",paramType="query")
	public Result findListRequestParam(TfTicket tfTicket, HttpServletRequest request, HttpServletResponse response, Model model) {
		return findList( tfTicket,model);
	}

	@RequestMapping(value = {"findList/json"},method ={RequestMethod.POST})
	@ApiOperation(value="订单列表(不包含页信息)(Content-Type为application/json)", notes="订单列表(不包含页信息)(Content-Type为application/json)")
	@ApiImplicitParam(name = "tfTicket", value = "订单", dataType = "TfTicket",paramType="body")
	public Result findListRequestBody(@RequestBody TfTicket tfTicket, Model model) {
		return findList( tfTicket,model);
	}

	private Result findList(TfTicket tfTicket, Model model) {
		List<TfTicket> list = tfTicketService.findListCache(tfTicket);
		//List<TfTicket> list = tfTicketService.findList(tfTicket);
		Result result = ResultFactory.getSuccessResult();
		result.setResultObject(list);
		return result;
	}

	/**
	 * 订单列表(包含页信息)
	 */
	//RequiresPermissions("ylttrip:tfTicket:list")
	@RequestMapping(value = {"list"},method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="订单列表(包含页信息)(Content-Type为text/html)", notes="订单列表(包含页信息)(Content-Type为text/html)")
	@ApiImplicitParam(name = "tfTicket", value = "订单", dataType = "TfTicket",paramType="query")
	public Result listRequestParam(TfTicket tfTicket, HttpServletRequest request, HttpServletResponse response, Model model) {
		return list(tfTicket,model);
	}

	@RequestMapping(value = {"list/json"},method ={RequestMethod.POST})
	@ApiOperation(value="订单列表(包含页信息)(Content-Type为application/json)", notes="订单列表(包含页信息)(Content-Type为application/json)")
	@ApiImplicitParam(name = "tfTicket", value = "订单", dataType = "TfTicket",paramType="body")
	public Result listRequestBody(@RequestBody TfTicket tfTicket, Model model) {
		return list(tfTicket,model);
	}

	private Result list(TfTicket tfTicket, Model model) {
		Page<TfTicket> page = tfTicketService.findPageCache(new Page<TfTicket>(tfTicket.getPageNo(),tfTicket.getPageSize(),tfTicket.getOrderBy()), tfTicket);
		//Page<TfTicket> page = tfTicketService.findPage(new Page<TfTicket>(tfTicket.getPageNo(),tfTicket.getPageSize(),tfTicket.getOrderBy()), tfTicket);
		Result result = ResultFactory.getSuccessResult();
		result.setResultObject(page);
		return result;
	}

	/**
	 * 订单获取列表第一条记录
	 */
	//RequiresPermissions("ylttrip:tfTicket:listFrist")
	@RequestMapping(value = {"listFrist"},method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="订单获取列表第一条记录(Content-Type为text/html)", notes="订单获取列表第一条记录(Content-Type为text/html)")
	@ApiImplicitParam(name = "tfTicket", value = "订单", dataType = "TfTicket",paramType="query")
	public Result listFristRequestParam(TfTicket tfTicket, HttpServletRequest request, HttpServletResponse response, Model model) {
		return listFrist(tfTicket,model);
	}

	@RequestMapping(value = {"listFrist/json"},method ={RequestMethod.POST})
	@ApiOperation(value="订单获取列表第一条记录(Content-Type为application/json)", notes="订单获取列表第一条记录(Content-Type为application/json)")
	@ApiImplicitParam(name = "tfTicket", value = "订单", dataType = "TfTicket",paramType="body")
	public Result listFristRequestBody(@RequestBody TfTicket tfTicket, Model model) {
		return listFrist(tfTicket,model);
	}

	private Result listFrist(TfTicket tfTicket, Model model) {
		Page<TfTicket> page = tfTicketService.findPageCache(new Page<TfTicket>(tfTicket.getPageNo(),tfTicket.getPageSize(),tfTicket.getOrderBy()), tfTicket);
		//Page<TfTicket> page = tfTicketService.findPage(new Page<TfTicket>(tfTicket.getPageNo(),tfTicket.getPageSize(),tfTicket.getOrderBy()), tfTicket);
		Result result = ResultFactory.getSuccessResult();
		if(page.getList().size()>0){
			result.setResultObject(page.getList().get(0));
		}else{
			result=ResultFactory.getErrorResult("没有记录！");
		}
		return result;
	}

	/**
	 * 保存订单
	 */
	//RequiresPermissions(value={"ylttrip:tfTicket:add","ylttrip:tfTicket:edit"},logical=Logical.OR)
	@RequestMapping(value = "save",method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="保存订单(Content-Type为text/html)", notes="保存订单(Content-Type为text/html)")
	@ApiImplicitParam(name = "tfTicket", value = "订单", dataType = "TfTicket",paramType="query")
	public Result saveRequestParam(TfTicket tfTicket, Model model, RedirectAttributes redirectAttributes) {
		return save(tfTicket,model,redirectAttributes);
	}

	@RequestMapping(value = "save/json",method ={RequestMethod.POST})
	@ApiOperation(value="保存订单(Content-Type为application/json)", notes="保存订单(Content-Type为application/json)")
	@ApiImplicitParam(name = "tfTicket", value = "订单", dataType = "TfTicket",paramType="body")
	public Result saveRequestBody(@RequestBody TfTicket tfTicket, Model model, RedirectAttributes redirectAttributes) {
		return save(tfTicket,model,redirectAttributes);
	}

	private Result save(TfTicket tfTicket, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tfTicket)){
			Result result = ResultFactory.getErrorResult("数据验证失败");
		}
		tfTicketService.save(tfTicket);
		Result result = ResultFactory.getSuccessResult("保存订单成功");
		return result;
	}

	/**
	 * 删除订单
	 */
	//RequiresPermissions("ylttrip:tfTicket:del")
	@RequestMapping(value = "delete",method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="删除订单(Content-Type为text/html)", notes="删除订单(Content-Type为text/html)")
	@ApiImplicitParam(name = "tfTicket", value = "订单", dataType = "TfTicket",paramType="query")
	public Result deleteRequestParam(TfTicket tfTicket, RedirectAttributes redirectAttributes) {
		return delete(tfTicket,redirectAttributes);
	}

	@RequestMapping(value = "delete/json",method ={RequestMethod.POST})
	@ApiOperation(value="删除订单(Content-Type为application/json)", notes="删除订单(Content-Type为application/json)")
	@ApiImplicitParam(name = "tfTicket", value = "订单", dataType = "TfTicket",paramType="body")
	public Result deleteRequestBody(@RequestBody TfTicket tfTicket, RedirectAttributes redirectAttributes) {
		return delete(tfTicket,redirectAttributes);
	}

	private Result delete(TfTicket tfTicket, RedirectAttributes redirectAttributes) {
		tfTicketService.delete(tfTicket);
		Result result = ResultFactory.getSuccessResult("删除订单成功");
		return result;
	}

	/**
	 * 删除订单（逻辑删除，更新del_flag字段为1,在表包含字段del_flag时，可以调用此方法，将数据隐藏）
	 */
	@RequestMapping(value = "deleteByLogic",method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="逻辑删除订单(Content-Type为text/html)", notes="逻辑删除订单(Content-Type为text/html)")
	@ApiImplicitParam(name = "tfTicket", value = "订单", dataType = "TfTicket",paramType="query")
	public Result deleteByLogicRequestParam(TfTicket tfTicket, RedirectAttributes redirectAttributes) {
		return deleteByLogic(tfTicket,redirectAttributes);
	}

	/**
	 * 删除订单（逻辑删除，更新del_flag字段为1,在表包含字段del_flag时，可以调用此方法，将数据隐藏）
	 */
	@RequestMapping(value = "deleteByLogic/json",method ={RequestMethod.POST})
	@ApiOperation(value="逻辑删除订单(Content-Type为application/json)", notes="逻辑删除订单(Content-Type为application/json)")
	@ApiImplicitParam(name = "tfTicket", value = "订单", dataType = "TfTicket",paramType="body")
	public Result deleteByLogicRequestBody(@RequestBody TfTicket tfTicket, RedirectAttributes redirectAttributes) {
		return deleteByLogic(tfTicket,redirectAttributes);
	}

	private Result deleteByLogic(TfTicket tfTicket, RedirectAttributes redirectAttributes) {
		tfTicketService.deleteByLogic(tfTicket);
		Result result = ResultFactory.getSuccessResult("删除订单成功");
		return result;
	}

	/**
	 * 批量删除订单
	 */
	//RequiresPermissions("ylttrip:tfTicket:del")
	@RequestMapping(value = "deleteAll",method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="批量删除订单(Content-Type为text/html)", notes="批量删除订单(Content-Type为text/html)")
	@ApiImplicitParam(name = "ids", value = "订单ids,用,隔开", required = false, dataType = "String",paramType="query")
	public Result deleteAllRequestParam(String ids, RedirectAttributes redirectAttributes) {
		return deleteAll(ids,redirectAttributes);
	}

	@RequestMapping(value = "deleteAll/json",method ={RequestMethod.POST})
	@ApiOperation(value="批量删除订单(Content-Type为application/json)", notes="批量删除订单(Content-Type为application/json)")
	@ApiImplicitParam(name = "ids", value = "订单ids,用,隔开", required = false, dataType = "String",paramType="body")
	public Result deleteAllRequestBody(@RequestBody String ids, RedirectAttributes redirectAttributes) {
		return deleteAll(ids,redirectAttributes);
	}

	private Result deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			tfTicketService.delete(tfTicketService.get(id));
		}
        Result result = ResultFactory.getSuccessResult("删除订单成功");
		return result;
	}

	/**
	 * 批量删除订单（逻辑删除，更新del_flag字段为1,在表包含字段del_flag时，可以调用此方法，将数据隐藏）
	 */
	@RequestMapping(value = "deleteAllByLogic",method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="逻辑批量删除订单(Content-Type为text/html)", notes="逻辑批量删除订单(Content-Type为text/html)")
	@ApiImplicitParam(name = "ids", value = "订单ids,用,隔开", required = false, dataType = "String",paramType="query")
	public Result deleteAllByLogicRequestParam(String ids, RedirectAttributes redirectAttributes) {
		return deleteAllByLogic(ids,redirectAttributes);
	}

	/**
	 * 批量删除订单（逻辑删除，更新del_flag字段为1,在表包含字段del_flag时，可以调用此方法，将数据隐藏）
	 */
	@RequestMapping(value = "deleteAllByLogic/json",method ={RequestMethod.POST})
	@ApiOperation(value="逻辑批量删除订单(Content-Type为application/json)", notes="逻辑批量删除订单(Content-Type为application/json)")
	@ApiImplicitParam(name = "ids", value = "订单ids,用,隔开", required = false, dataType = "String",paramType="body")
	public Result deleteAllByLogicRequestBody(@RequestBody String ids, RedirectAttributes redirectAttributes) {
		return deleteAllByLogic(ids,redirectAttributes);
	}

	private Result deleteAllByLogic(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			tfTicketService.deleteByLogic(tfTicketService.get(id));
		}
        Result result = ResultFactory.getSuccessResult("删除订单成功");
		return result;
	}

}