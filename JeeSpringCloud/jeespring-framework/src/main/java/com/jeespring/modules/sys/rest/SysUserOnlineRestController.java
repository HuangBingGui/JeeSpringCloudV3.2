/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.jeespring.modules.sys.rest;

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
import com.jeespring.modules.sys.entity.SysUserOnline;
import com.jeespring.modules.sys.service.SysUserOnlineService;
import org.springframework.web.bind.annotation.RestController;
import com.jeespring.common.web.Result;
import com.jeespring.common.web.ResultFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * 在线用户记录Controller
 * @author JeeSpring
 * @version 2018-08-16
 */
@RestController
@RequestMapping(value = "/rest/sys/sysUserOnline")
@Api(value="在线用户记录接口", description="在线用户记录接口")
public class SysUserOnlineRestController extends AbstractBaseController {

	@Autowired
	private SysUserOnlineService sysUserOnlineService;

	/**
	 * 在线用户记录信息
	 */
	@RequestMapping(value = {"get"},method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="在线用户记录信息(Content-Type为text/html)", notes="在线用户记录信息(Content-Type为text/html)")
	@ApiImplicitParam(name = "id", value = "在线用户记录id", required = false, dataType = "String",paramType="query")
	public Result getRequestParam(@RequestParam(required=false) String id) {
		return get(id);
	}

	@RequestMapping(value = {"get/json"},method ={RequestMethod.POST})
	@ApiOperation(value="在线用户记录信息(Content-Type为application/json)", notes="在线用户记录信息(Content-Type为application/json)")
	@ApiImplicitParam(name = "id", value = "在线用户记录id", required = false, dataType = "String",paramType="body")
	public Result getRequestBody(@RequestBody(required=false) String id) {
		return get(id);
	}

	private Result get(String id) {
		SysUserOnline entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysUserOnlineService.getCache(id);
			//entity = sysUserOnlineService.get(id);
		}
		if (entity == null){
			entity = new SysUserOnline();
		}
		Result result = ResultFactory.getSuccessResult();
		result.setResultObject(entity);
		return result;
	}

	/**
	 * 在线用户记录列表(不包含页信息)
	 */
	//RequiresPermissions("sys:sysUserOnline:findList")
	@RequestMapping(value = {"findList"},method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="在线用户记录列表(不包含页信息)(Content-Type为text/html)", notes="在线用户记录列表(不包含页信息)(Content-Type为text/html)")
	@ApiImplicitParam(name = "sysUserOnline", value = "在线用户记录", dataType = "SysUserOnline",paramType="query")
	public Result findListRequestParam(SysUserOnline sysUserOnline, HttpServletRequest request, HttpServletResponse response, Model model) {
		return findList( sysUserOnline,model);
	}

	@RequestMapping(value = {"findList/json"},method ={RequestMethod.POST})
	@ApiOperation(value="在线用户记录列表(不包含页信息)(Content-Type为application/json)", notes="在线用户记录列表(不包含页信息)(Content-Type为application/json)")
	@ApiImplicitParam(name = "sysUserOnline", value = "在线用户记录", dataType = "SysUserOnline",paramType="body")
	public Result findListRequestBody(@RequestBody SysUserOnline sysUserOnline, Model model) {
		return findList( sysUserOnline,model);
	}

	private Result findList(SysUserOnline sysUserOnline, Model model) {
		List<SysUserOnline> list = sysUserOnlineService.findListCache(sysUserOnline);
		//List<SysUserOnline> list = sysUserOnlineService.findList(sysUserOnline);
		Result result = ResultFactory.getSuccessResult();
		result.setResultObject(list);
		return result;
	}

	/**
	 * 在线用户记录列表(包含页信息)
	 */
	//RequiresPermissions("sys:sysUserOnline:list")
	@RequestMapping(value = {"list"},method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="在线用户记录列表(包含页信息)(Content-Type为text/html)", notes="在线用户记录列表(包含页信息)(Content-Type为text/html)")
	@ApiImplicitParam(name = "sysUserOnline", value = "在线用户记录", dataType = "SysUserOnline",paramType="query")
	public Result listRequestParam(SysUserOnline sysUserOnline, HttpServletRequest request, HttpServletResponse response, Model model) {
		return list(sysUserOnline,model);
	}

	@RequestMapping(value = {"list/json"},method ={RequestMethod.POST})
	@ApiOperation(value="在线用户记录列表(包含页信息)(Content-Type为application/json)", notes="在线用户记录列表(包含页信息)(Content-Type为application/json)")
	@ApiImplicitParam(name = "sysUserOnline", value = "在线用户记录", dataType = "SysUserOnline",paramType="body")
	public Result listRequestBody(@RequestBody SysUserOnline sysUserOnline, Model model) {
		return list(sysUserOnline,model);
	}

	private Result list(SysUserOnline sysUserOnline, Model model) {
		Page<SysUserOnline> page = sysUserOnlineService.findPageCache(new Page<SysUserOnline>(sysUserOnline.getPageNo(),sysUserOnline.getPageSize(),sysUserOnline.getOrderBy()), sysUserOnline);
		//Page<SysUserOnline> page = sysUserOnlineService.findPage(new Page<SysUserOnline>(sysUserOnline.getPageNo(),sysUserOnline.getPageSize(),sysUserOnline.getOrderBy()), sysUserOnline);
		Result result = ResultFactory.getSuccessResult();
		result.setResultObject(page);
		return result;
	}

	/**
	 * 在线用户记录获取列表第一条记录
	 */
	//RequiresPermissions("sys:sysUserOnline:listFrist")
	@RequestMapping(value = {"listFrist"},method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="在线用户记录获取列表第一条记录(Content-Type为text/html)", notes="在线用户记录获取列表第一条记录(Content-Type为text/html)")
	@ApiImplicitParam(name = "sysUserOnline", value = "在线用户记录", dataType = "SysUserOnline",paramType="query")
	public Result listFristRequestParam(SysUserOnline sysUserOnline, HttpServletRequest request, HttpServletResponse response, Model model) {
		return listFrist(sysUserOnline,model);
	}

	@RequestMapping(value = {"listFrist/json"},method ={RequestMethod.POST})
	@ApiOperation(value="在线用户记录获取列表第一条记录(Content-Type为application/json)", notes="在线用户记录获取列表第一条记录(Content-Type为application/json)")
	@ApiImplicitParam(name = "sysUserOnline", value = "在线用户记录", dataType = "SysUserOnline",paramType="body")
	public Result listFristRequestBody(@RequestBody SysUserOnline sysUserOnline, Model model) {
		return listFrist(sysUserOnline,model);
	}

	private Result listFrist(SysUserOnline sysUserOnline, Model model) {
		Page<SysUserOnline> page = sysUserOnlineService.findPageCache(new Page<SysUserOnline>(sysUserOnline.getPageNo(),sysUserOnline.getPageSize(),sysUserOnline.getOrderBy()), sysUserOnline);
		//Page<SysUserOnline> page = sysUserOnlineService.findPage(new Page<SysUserOnline>(sysUserOnline.getPageNo(),sysUserOnline.getPageSize(),sysUserOnline.getOrderBy()), sysUserOnline);
		Result result = ResultFactory.getSuccessResult();
		if(page.getList().size()>0){
			result.setResultObject(page.getList().get(0));
		}else{
			result=ResultFactory.getErrorResult("没有记录！");
		}
		return result;
	}

	/**
	 * 保存在线用户记录
	 */
	//RequiresPermissions(value={"sys:sysUserOnline:add","sys:sysUserOnline:edit"},logical=Logical.OR)
	@RequestMapping(value = "save",method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="保存在线用户记录(Content-Type为text/html)", notes="保存在线用户记录(Content-Type为text/html)")
	@ApiImplicitParam(name = "sysUserOnline", value = "在线用户记录", dataType = "SysUserOnline",paramType="query")
	public Result saveRequestParam(SysUserOnline sysUserOnline, Model model, RedirectAttributes redirectAttributes) {
		return save(sysUserOnline,model,redirectAttributes);
	}

	@RequestMapping(value = "save/json",method ={RequestMethod.POST})
	@ApiOperation(value="保存在线用户记录(Content-Type为application/json)", notes="保存在线用户记录(Content-Type为application/json)")
	@ApiImplicitParam(name = "sysUserOnline", value = "在线用户记录", dataType = "SysUserOnline",paramType="body")
	public Result saveRequestBody(@RequestBody SysUserOnline sysUserOnline, Model model, RedirectAttributes redirectAttributes) {
		return save(sysUserOnline,model,redirectAttributes);
	}

	private Result save(SysUserOnline sysUserOnline, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysUserOnline)){
			Result result = ResultFactory.getErrorResult("数据验证失败");
		}
		sysUserOnlineService.save(sysUserOnline);
		Result result = ResultFactory.getSuccessResult("保存在线用户记录成功");
		return result;
	}

	/**
	 * 删除在线用户记录
	 */
	//RequiresPermissions("sys:sysUserOnline:del")
	@RequestMapping(value = "delete",method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="删除在线用户记录(Content-Type为text/html)", notes="删除在线用户记录(Content-Type为text/html)")
	@ApiImplicitParam(name = "sysUserOnline", value = "在线用户记录", dataType = "SysUserOnline",paramType="query")
	public Result deleteRequestParam(SysUserOnline sysUserOnline, RedirectAttributes redirectAttributes) {
		return delete(sysUserOnline,redirectAttributes);
	}

	@RequestMapping(value = "delete/json",method ={RequestMethod.POST})
	@ApiOperation(value="删除在线用户记录(Content-Type为application/json)", notes="删除在线用户记录(Content-Type为application/json)")
	@ApiImplicitParam(name = "sysUserOnline", value = "在线用户记录", dataType = "SysUserOnline",paramType="body")
	public Result deleteRequestBody(@RequestBody SysUserOnline sysUserOnline, RedirectAttributes redirectAttributes) {
		return delete(sysUserOnline,redirectAttributes);
	}

	private Result delete(SysUserOnline sysUserOnline, RedirectAttributes redirectAttributes) {
		sysUserOnlineService.delete(sysUserOnline);
		Result result = ResultFactory.getSuccessResult("删除在线用户记录成功");
		return result;
	}

	/**
	 * 删除在线用户记录（逻辑删除，更新del_flag字段为1,在表包含字段del_flag时，可以调用此方法，将数据隐藏）
	 */
	@RequestMapping(value = "deleteByLogic",method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="逻辑删除在线用户记录(Content-Type为text/html)", notes="逻辑删除在线用户记录(Content-Type为text/html)")
	@ApiImplicitParam(name = "sysUserOnline", value = "在线用户记录", dataType = "SysUserOnline",paramType="query")
	public Result deleteByLogicRequestParam(SysUserOnline sysUserOnline, RedirectAttributes redirectAttributes) {
		return deleteByLogic(sysUserOnline,redirectAttributes);
	}

	/**
	 * 删除在线用户记录（逻辑删除，更新del_flag字段为1,在表包含字段del_flag时，可以调用此方法，将数据隐藏）
	 */
	@RequestMapping(value = "deleteByLogic/json",method ={RequestMethod.POST})
	@ApiOperation(value="逻辑删除在线用户记录(Content-Type为application/json)", notes="逻辑删除在线用户记录(Content-Type为application/json)")
	@ApiImplicitParam(name = "sysUserOnline", value = "在线用户记录", dataType = "SysUserOnline",paramType="body")
	public Result deleteByLogicRequestBody(@RequestBody SysUserOnline sysUserOnline, RedirectAttributes redirectAttributes) {
		return deleteByLogic(sysUserOnline,redirectAttributes);
	}

	private Result deleteByLogic(SysUserOnline sysUserOnline, RedirectAttributes redirectAttributes) {
		sysUserOnlineService.deleteByLogic(sysUserOnline);
		Result result = ResultFactory.getSuccessResult("删除在线用户记录成功");
		return result;
	}

	/**
	 * 批量删除在线用户记录
	 */
	//RequiresPermissions("sys:sysUserOnline:del")
	@RequestMapping(value = "deleteAll",method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="批量删除在线用户记录(Content-Type为text/html)", notes="批量删除在线用户记录(Content-Type为text/html)")
	@ApiImplicitParam(name = "ids", value = "在线用户记录ids,用,隔开", required = false, dataType = "String",paramType="query")
	public Result deleteAllRequestParam(String ids, RedirectAttributes redirectAttributes) {
		return deleteAll(ids,redirectAttributes);
	}

	@RequestMapping(value = "deleteAll/json",method ={RequestMethod.POST})
	@ApiOperation(value="批量删除在线用户记录(Content-Type为application/json)", notes="批量删除在线用户记录(Content-Type为application/json)")
	@ApiImplicitParam(name = "ids", value = "在线用户记录ids,用,隔开", required = false, dataType = "String",paramType="body")
	public Result deleteAllRequestBody(@RequestBody String ids, RedirectAttributes redirectAttributes) {
		return deleteAll(ids,redirectAttributes);
	}

	private Result deleteAll(String ids, RedirectAttributes redirectAttributes) {
        String[] idArray = ids.split(",");
		for(String id : idArray){
			sysUserOnlineService.delete(sysUserOnlineService.get(id));
		}
        Result result = ResultFactory.getSuccessResult("删除在线用户记录成功");
		return result;
	}

	/**
	 * 批量删除在线用户记录（逻辑删除，更新del_flag字段为1,在表包含字段del_flag时，可以调用此方法，将数据隐藏）
	 */
	@RequestMapping(value = "deleteAllByLogic",method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="逻辑批量删除在线用户记录(Content-Type为text/html)", notes="逻辑批量删除在线用户记录(Content-Type为text/html)")
	@ApiImplicitParam(name = "ids", value = "在线用户记录ids,用,隔开", required = false, dataType = "String",paramType="query")
	public Result deleteAllByLogicRequestParam(String ids, RedirectAttributes redirectAttributes) {
		return deleteAllByLogic(ids,redirectAttributes);
	}

	/**
	 * 批量删除在线用户记录（逻辑删除，更新del_flag字段为1,在表包含字段del_flag时，可以调用此方法，将数据隐藏）
	 */
	@RequestMapping(value = "deleteAllByLogic/json",method ={RequestMethod.POST})
	@ApiOperation(value="逻辑批量删除在线用户记录(Content-Type为application/json)", notes="逻辑批量删除在线用户记录(Content-Type为application/json)")
	@ApiImplicitParam(name = "ids", value = "在线用户记录ids,用,隔开", required = false, dataType = "String",paramType="body")
	public Result deleteAllByLogicRequestBody(@RequestBody String ids, RedirectAttributes redirectAttributes) {
		return deleteAllByLogic(ids,redirectAttributes);
	}

	private Result deleteAllByLogic(String ids, RedirectAttributes redirectAttributes) {
        String[] idArray = ids.split(",");
		for(String id : idArray){
			sysUserOnlineService.deleteByLogic(sysUserOnlineService.get(id));
		}
        Result result = ResultFactory.getSuccessResult("删除在线用户记录成功");
		return result;
	}

}