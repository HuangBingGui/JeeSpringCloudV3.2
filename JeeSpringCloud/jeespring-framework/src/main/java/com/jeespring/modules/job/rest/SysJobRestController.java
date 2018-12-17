/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.jeespring.modules.job.rest;

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
import com.jeespring.modules.job.entity.SysJob;
import com.jeespring.modules.job.service.SysJobService;
import org.springframework.web.bind.annotation.RestController;
import com.jeespring.common.web.Result;
import com.jeespring.common.web.ResultFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * 定时任务调度Controller
 * @author JeeSpring
 * @version 2018-08-16
 */
@RestController
@RequestMapping(value = "/rest/job/sysJob")
@Api(value="定时任务调度接口", description="定时任务调度接口")
public class SysJobRestController extends AbstractBaseController {

	@Autowired
	private SysJobService sysJobService;

	/**
	 * 定时任务调度信息
	 */
	@RequestMapping(value = {"get"},method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="定时任务调度信息(Content-Type为text/html)", notes="定时任务调度信息(Content-Type为text/html)")
	@ApiImplicitParam(name = "id", value = "定时任务调度id", required = false, dataType = "String",paramType="query")
	public Result getRequestParam(@RequestParam(required=false) String id) {
		return get(id);
	}

	@RequestMapping(value = {"get/json"},method ={RequestMethod.POST})
	@ApiOperation(value="定时任务调度信息(Content-Type为application/json)", notes="定时任务调度信息(Content-Type为application/json)")
	@ApiImplicitParam(name = "id", value = "定时任务调度id", required = false, dataType = "String",paramType="body")
	public Result getRequestBody(@RequestBody(required=false) String id) {
		return get(id);
	}

	private Result get(String id) {
		SysJob entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysJobService.getCache(id);
			//entity = sysJobService.get(id);
		}
		if (entity == null){
			entity = new SysJob();
		}
		Result result = ResultFactory.getSuccessResult();
		result.setResultObject(entity);
		return result;
	}

	/**
	 * 定时任务调度列表(不包含页信息)
	 */
	//RequiresPermissions("job:sysJob:findList")
	@RequestMapping(value = {"findList"},method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="定时任务调度列表(不包含页信息)(Content-Type为text/html)", notes="定时任务调度列表(不包含页信息)(Content-Type为text/html)")
	@ApiImplicitParam(name = "sysJob", value = "定时任务调度", dataType = "SysJob",paramType="query")
	public Result findListRequestParam(SysJob sysJob, HttpServletRequest request, HttpServletResponse response, Model model) {
		return findList( sysJob,model);
	}

	@RequestMapping(value = {"findList/json"},method ={RequestMethod.POST})
	@ApiOperation(value="定时任务调度列表(不包含页信息)(Content-Type为application/json)", notes="定时任务调度列表(不包含页信息)(Content-Type为application/json)")
	@ApiImplicitParam(name = "sysJob", value = "定时任务调度", dataType = "SysJob",paramType="body")
	public Result findListRequestBody(@RequestBody SysJob sysJob, Model model) {
		return findList( sysJob,model);
	}

	private Result findList(SysJob sysJob, Model model) {
		List<SysJob> list = sysJobService.findListCache(sysJob);
		//List<SysJob> list = sysJobService.findList(sysJob);
		Result result = ResultFactory.getSuccessResult();
		result.setResultObject(list);
		return result;
	}

	/**
	 * 定时任务调度列表(包含页信息)
	 */
	//RequiresPermissions("job:sysJob:list")
	@RequestMapping(value = {"list"},method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="定时任务调度列表(包含页信息)(Content-Type为text/html)", notes="定时任务调度列表(包含页信息)(Content-Type为text/html)")
	@ApiImplicitParam(name = "sysJob", value = "定时任务调度", dataType = "SysJob",paramType="query")
	public Result listRequestParam(SysJob sysJob, HttpServletRequest request, HttpServletResponse response, Model model) {
		return list(sysJob,model);
	}

	@RequestMapping(value = {"list/json"},method ={RequestMethod.POST})
	@ApiOperation(value="定时任务调度列表(包含页信息)(Content-Type为application/json)", notes="定时任务调度列表(包含页信息)(Content-Type为application/json)")
	@ApiImplicitParam(name = "sysJob", value = "定时任务调度", dataType = "SysJob",paramType="body")
	public Result listRequestBody(@RequestBody SysJob sysJob, Model model) {
		return list(sysJob,model);
	}

	private Result list(SysJob sysJob, Model model) {
		Page<SysJob> page = sysJobService.findPageCache(new Page<SysJob>(sysJob.getPageNo(),sysJob.getPageSize(),sysJob.getOrderBy()), sysJob);
		//Page<SysJob> page = sysJobService.findPage(new Page<SysJob>(sysJob.getPageNo(),sysJob.getPageSize(),sysJob.getOrderBy()), sysJob);
		Result result = ResultFactory.getSuccessResult();
		result.setResultObject(page);
		return result;
	}

	/**
	 * 定时任务调度获取列表第一条记录
	 */
	//RequiresPermissions("job:sysJob:listFrist")
	@RequestMapping(value = {"listFrist"},method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="定时任务调度获取列表第一条记录(Content-Type为text/html)", notes="定时任务调度获取列表第一条记录(Content-Type为text/html)")
	@ApiImplicitParam(name = "sysJob", value = "定时任务调度", dataType = "SysJob",paramType="query")
	public Result listFristRequestParam(SysJob sysJob, HttpServletRequest request, HttpServletResponse response, Model model) {
		return listFrist(sysJob,model);
	}

	@RequestMapping(value = {"listFrist/json"},method ={RequestMethod.POST})
	@ApiOperation(value="定时任务调度获取列表第一条记录(Content-Type为application/json)", notes="定时任务调度获取列表第一条记录(Content-Type为application/json)")
	@ApiImplicitParam(name = "sysJob", value = "定时任务调度", dataType = "SysJob",paramType="body")
	public Result listFristRequestBody(@RequestBody SysJob sysJob, Model model) {
		return listFrist(sysJob,model);
	}

	private Result listFrist(SysJob sysJob, Model model) {
		Page<SysJob> page = sysJobService.findPageCache(new Page<SysJob>(sysJob.getPageNo(),sysJob.getPageSize(),sysJob.getOrderBy()), sysJob);
		//Page<SysJob> page = sysJobService.findPage(new Page<SysJob>(sysJob.getPageNo(),sysJob.getPageSize(),sysJob.getOrderBy()), sysJob);
		Result result = ResultFactory.getSuccessResult();
		if(page.getList().size()>0){
			result.setResultObject(page.getList().get(0));
		}else{
			result=ResultFactory.getErrorResult("没有记录！");
		}
		return result;
	}

	/**
	 * 保存定时任务调度
	 */
	//RequiresPermissions(value={"job:sysJob:add","job:sysJob:edit"},logical=Logical.OR)
	@RequestMapping(value = "save",method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="保存定时任务调度(Content-Type为text/html)", notes="保存定时任务调度(Content-Type为text/html)")
	@ApiImplicitParam(name = "sysJob", value = "定时任务调度", dataType = "SysJob",paramType="query")
	public Result saveRequestParam(SysJob sysJob, Model model, RedirectAttributes redirectAttributes) {
		return save(sysJob,model,redirectAttributes);
	}

	@RequestMapping(value = "save/json",method ={RequestMethod.POST})
	@ApiOperation(value="保存定时任务调度(Content-Type为application/json)", notes="保存定时任务调度(Content-Type为application/json)")
	@ApiImplicitParam(name = "sysJob", value = "定时任务调度", dataType = "SysJob",paramType="body")
	public Result saveRequestBody(@RequestBody SysJob sysJob, Model model, RedirectAttributes redirectAttributes) {
		return save(sysJob,model,redirectAttributes);
	}

	private Result save(SysJob sysJob, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysJob)){
			Result result = ResultFactory.getErrorResult("数据验证失败");
		}
		sysJobService.save(sysJob);
		Result result = ResultFactory.getSuccessResult("保存定时任务调度成功");
		return result;
	}

	/**
	 * 删除定时任务调度
	 */
	//RequiresPermissions("job:sysJob:del")
	@RequestMapping(value = "delete",method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="删除定时任务调度(Content-Type为text/html)", notes="删除定时任务调度(Content-Type为text/html)")
	@ApiImplicitParam(name = "sysJob", value = "定时任务调度", dataType = "SysJob",paramType="query")
	public Result deleteRequestParam(SysJob sysJob, RedirectAttributes redirectAttributes) {
		return delete(sysJob,redirectAttributes);
	}

	@RequestMapping(value = "delete/json",method ={RequestMethod.POST})
	@ApiOperation(value="删除定时任务调度(Content-Type为application/json)", notes="删除定时任务调度(Content-Type为application/json)")
	@ApiImplicitParam(name = "sysJob", value = "定时任务调度", dataType = "SysJob",paramType="body")
	public Result deleteRequestBody(@RequestBody SysJob sysJob, RedirectAttributes redirectAttributes) {
		return delete(sysJob,redirectAttributes);
	}

	private Result delete(SysJob sysJob, RedirectAttributes redirectAttributes) {
		sysJobService.delete(sysJob);
		Result result = ResultFactory.getSuccessResult("删除定时任务调度成功");
		return result;
	}

	/**
	 * 删除定时任务调度（逻辑删除，更新del_flag字段为1,在表包含字段del_flag时，可以调用此方法，将数据隐藏）
	 */
	@RequestMapping(value = "deleteByLogic",method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="逻辑删除定时任务调度(Content-Type为text/html)", notes="逻辑删除定时任务调度(Content-Type为text/html)")
	@ApiImplicitParam(name = "sysJob", value = "定时任务调度", dataType = "SysJob",paramType="query")
	public Result deleteByLogicRequestParam(SysJob sysJob, RedirectAttributes redirectAttributes) {
		return deleteByLogic(sysJob,redirectAttributes);
	}

	/**
	 * 删除定时任务调度（逻辑删除，更新del_flag字段为1,在表包含字段del_flag时，可以调用此方法，将数据隐藏）
	 */
	@RequestMapping(value = "deleteByLogic/json",method ={RequestMethod.POST})
	@ApiOperation(value="逻辑删除定时任务调度(Content-Type为application/json)", notes="逻辑删除定时任务调度(Content-Type为application/json)")
	@ApiImplicitParam(name = "sysJob", value = "定时任务调度", dataType = "SysJob",paramType="body")
	public Result deleteByLogicRequestBody(@RequestBody SysJob sysJob, RedirectAttributes redirectAttributes) {
		return deleteByLogic(sysJob,redirectAttributes);
	}

	private Result deleteByLogic(SysJob sysJob, RedirectAttributes redirectAttributes) {
		sysJobService.deleteByLogic(sysJob);
		Result result = ResultFactory.getSuccessResult("删除定时任务调度成功");
		return result;
	}

	/**
	 * 批量删除定时任务调度
	 */
	//RequiresPermissions("job:sysJob:del")
	@RequestMapping(value = "deleteAll",method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="批量删除定时任务调度(Content-Type为text/html)", notes="批量删除定时任务调度(Content-Type为text/html)")
	@ApiImplicitParam(name = "ids", value = "定时任务调度ids,用,隔开", required = false, dataType = "String",paramType="query")
	public Result deleteAllRequestParam(String ids, RedirectAttributes redirectAttributes) {
		return deleteAll(ids,redirectAttributes);
	}

	@RequestMapping(value = "deleteAll/json",method ={RequestMethod.POST})
	@ApiOperation(value="批量删除定时任务调度(Content-Type为application/json)", notes="批量删除定时任务调度(Content-Type为application/json)")
	@ApiImplicitParam(name = "ids", value = "定时任务调度ids,用,隔开", required = false, dataType = "String",paramType="body")
	public Result deleteAllRequestBody(@RequestBody String ids, RedirectAttributes redirectAttributes) {
		return deleteAll(ids,redirectAttributes);
	}

	private Result deleteAll(String ids, RedirectAttributes redirectAttributes) {
        String[] idArray = ids.split(",");
		for(String id : idArray){
			sysJobService.delete(sysJobService.get(id));
		}
        Result result = ResultFactory.getSuccessResult("删除定时任务调度成功");
		return result;
	}

	/**
	 * 批量删除定时任务调度（逻辑删除，更新del_flag字段为1,在表包含字段del_flag时，可以调用此方法，将数据隐藏）
	 */
	@RequestMapping(value = "deleteAllByLogic",method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="逻辑批量删除定时任务调度(Content-Type为text/html)", notes="逻辑批量删除定时任务调度(Content-Type为text/html)")
	@ApiImplicitParam(name = "ids", value = "定时任务调度ids,用,隔开", required = false, dataType = "String",paramType="query")
	public Result deleteAllByLogicRequestParam(String ids, RedirectAttributes redirectAttributes) {
		return deleteAllByLogic(ids,redirectAttributes);
	}

	/**
	 * 批量删除定时任务调度（逻辑删除，更新del_flag字段为1,在表包含字段del_flag时，可以调用此方法，将数据隐藏）
	 */
	@RequestMapping(value = "deleteAllByLogic/json",method ={RequestMethod.POST})
	@ApiOperation(value="逻辑批量删除定时任务调度(Content-Type为application/json)", notes="逻辑批量删除定时任务调度(Content-Type为application/json)")
	@ApiImplicitParam(name = "ids", value = "定时任务调度ids,用,隔开", required = false, dataType = "String",paramType="body")
	public Result deleteAllByLogicRequestBody(@RequestBody String ids, RedirectAttributes redirectAttributes) {
		return deleteAllByLogic(ids,redirectAttributes);
	}

	private Result deleteAllByLogic(String ids, RedirectAttributes redirectAttributes) {
        String[] idArray = ids.split(",");
		for(String id : idArray){
			sysJobService.deleteByLogic(sysJobService.get(id));
		}
        Result result = ResultFactory.getSuccessResult("删除定时任务调度成功");
		return result;
	}

}