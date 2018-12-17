/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.jeespring.modules.sys.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jeespring.common.persistence.Page;
import com.jeespring.common.web.AbstractBaseController;
import com.jeespring.common.utils.StringUtils;
import com.jeespring.modules.sys.entity.SysConfig;
import com.jeespring.modules.sys.service.SysConfigService;
import org.springframework.web.bind.annotation.RestController;
import com.jeespring.common.web.Result;
import com.jeespring.common.web.ResultFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * 系统配置Controller
 * @author 黄炳桂 516821420@qq.com
 * @version 2017-11-17
 */
@RestController
@RequestMapping(value = "/rest/sys/sysConfig")
@Api(value="SysConfig系统配置接口", description="SysConfig系统配置接口")
public class SysConfigRestController extends AbstractBaseController {

	@Autowired
	private SysConfigService sysConfigService;

	/**
	 * 系统配置信息
	 */
	@RequestMapping(value = {"get"},method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="系统配置信息(Content-Type为text/html)", notes="系统配置信息(Content-Type为text/html)")
	@ApiImplicitParam(name = "id", value = "系统配置id", required = false, dataType = "String",paramType="query")
	public Result get(@RequestParam(required=false) String id) {
		SysConfig entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysConfigService.get(id);
		}
		if (entity == null){
			entity = new SysConfig();
		}
		Result result = ResultFactory.getSuccessResult();
		result.setResultObject(entity);
		return result;
	}

	@RequestMapping(value = {"get/json"},method ={RequestMethod.POST})
	@ApiOperation(value="系统配置信息(Content-Type为application/json)", notes="系统配置信息(Content-Type为application/json)")
	@ApiImplicitParam(name = "id", value = "系统配置id", required = false, dataType = "String",paramType="body")
	public Result getJson(@RequestBody(required=false) String id) {
		SysConfig entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysConfigService.get(id);
		}
		if (entity == null){
			entity = new SysConfig();
		}
		Result result = ResultFactory.getSuccessResult();
		result.setResultObject(entity);
		return result;
	}

	/**
	 * 系统配置列表（不包含页信息）
	 */
	//RequiresPermissions("sys:sysConfig:findList")
	@RequestMapping(value = {"findList"},method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="系统配置列表（不包含页信息）(Content-Type为text/html)", notes="系统配置列表（不包含页信息）(Content-Type为text/html)")
	@ApiImplicitParam(name = "sysConfig", value = "系统配置", dataType = "SysConfig",paramType="query")
	public Result findList(SysConfig sysConfig, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<SysConfig> list = sysConfigService.findList(sysConfig);
		Result result = ResultFactory.getSuccessResult();
		result.setResultObject(list);
		return result;
	}

	@RequestMapping(value = {"findList/json"},method ={RequestMethod.POST})
	@ApiOperation(value="系统配置列表（不包含页信息）(Content-Type为application/json)", notes="系统配置列表（不包含页信息）(Content-Type为application/json)")
	@ApiImplicitParam(name = "sysConfig", value = "系统配置", dataType = "SysConfig",paramType="body")
	public Result findListJson(@RequestBody SysConfig sysConfig, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<SysConfig> list = sysConfigService.findList(sysConfig);
		Result result = ResultFactory.getSuccessResult();
		result.setResultObject(list);
		return result;
	}

	/**
	 * 系统配置列表（包含页信息）
	 */
	//RequiresPermissions("sys:sysConfig:list")
	@RequestMapping(value = {"list"},method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="系统配置列表（包含页信息）(Content-Type为text/html)", notes="系统配置列表（包含页信息）(Content-Type为text/html)")
	@ApiImplicitParam(name = "sysConfig", value = "系统配置", dataType = "SysConfig",paramType="query")
	public Result list(SysConfig sysConfig, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysConfig> page = sysConfigService.findPage(new Page<SysConfig>(request, response), sysConfig);
		Result result = ResultFactory.getSuccessResult();
		result.setResultObject(page);
		return result;
	}

	@RequestMapping(value = {"list/json"},method ={RequestMethod.POST})
	@ApiOperation(value="系统配置列表（包含页信息）(Content-Type为application/json)", notes="系统配置列表（包含页信息）(Content-Type为application/json)")
	@ApiImplicitParam(name = "sysConfig", value = "系统配置", dataType = "SysConfig",paramType="body")
	public Result listJson(@RequestBody SysConfig sysConfig, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysConfig> page = sysConfigService.findPage(new Page<SysConfig>(sysConfig.getPageNo(),sysConfig.getPageSize(),sysConfig.getOrderBy()), sysConfig);
		Result result = ResultFactory.getSuccessResult();
		result.setResultObject(page);
		return result;
	}

	/**
	 * 系统配置获取列表第一条记录
	 */
	//RequiresPermissions("sys:sysConfig:listFrist")
	@RequestMapping(value = {"listFrist"},method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="系统配置获取列表第一条记录(Content-Type为text/html)", notes="系统配置获取列表第一条记录(Content-Type为text/html)")
	@ApiImplicitParam(name = "sysConfig", value = "系统配置", dataType = "SysConfig",paramType="query")
	public Result listFrist(SysConfig sysConfig, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysConfig> page = sysConfigService.findPage(new Page<SysConfig>(request, response), sysConfig);
		Result result = ResultFactory.getSuccessResult();
		if(page.getList().size()>0){
			result.setResultObject(page.getList().get(0));
		}else{
			result=ResultFactory.getErrorResult("没有记录！");
		}
		return result;
	}

	@RequestMapping(value = {"listFrist/json"},method ={RequestMethod.POST})
	@ApiOperation(value="系统配置获取列表第一条记录(Content-Type为application/json)", notes="系统配置获取列表第一条记录(Content-Type为application/json)")
	@ApiImplicitParam(name = "sysConfig", value = "系统配置", dataType = "SysConfig",paramType="body")
	public Result listFristJson(@RequestBody SysConfig sysConfig, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysConfig> page = sysConfigService.findPage(new Page<SysConfig>(sysConfig.getPageNo(),sysConfig.getPageSize(),sysConfig.getOrderBy()), sysConfig);
		Result result = ResultFactory.getSuccessResult();
		if(page.getList().size()>0){
			result.setResultObject(page.getList().get(0));
		}else{
			result=ResultFactory.getErrorResult("没有记录！");
		}
		return result;
	}

	/**
	 * 保存系统配置
	 */
	//RequiresPermissions(value={"sys:sysConfig:add","sys:sysConfig:edit"},logical=Logical.OR)
	@RequestMapping(value = "save",method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="保存系统配置(Content-Type为text/html)", notes="保存系统配置(Content-Type为text/html)")
	@ApiImplicitParam(name = "sysConfig", value = "系统配置", dataType = "SysConfig",paramType="query")
	public Result save(SysConfig sysConfig, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysConfig)){
			Result result = ResultFactory.getErrorResult("数据验证失败");
		}
		sysConfigService.save(sysConfig);
		Result result = ResultFactory.getSuccessResult("保存系统配置成功");
		return result;
	}

	@RequestMapping(value = "save/json",method ={RequestMethod.POST})
	@ApiOperation(value="保存系统配置(Content-Type为application/json)", notes="保存系统配置(Content-Type为application/json)")
	@ApiImplicitParam(name = "sysConfig", value = "系统配置", dataType = "SysConfig",paramType="body")
	public Result saveJson(@RequestBody SysConfig sysConfig, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysConfig)){
			Result result = ResultFactory.getErrorResult("数据验证失败");
		}
		sysConfigService.save(sysConfig);
		Result result = ResultFactory.getSuccessResult("保存系统配置成功");
		return result;
	}

	/**
	 * 删除系统配置
	 */
	//RequiresPermissions("sys:sysConfig:del")
	@RequestMapping(value = "delete",method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="删除系统配置(Content-Type为text/html)", notes="删除系统配置(Content-Type为text/html)")
	@ApiImplicitParam(name = "sysConfig", value = "系统配置", dataType = "SysConfig",paramType="query")
	public Result delete(SysConfig sysConfig, RedirectAttributes redirectAttributes) {
		sysConfigService.delete(sysConfig);
		Result result = ResultFactory.getSuccessResult("删除系统配置成功");
		return result;
	}

	/**
	 * 删除系统配置（逻辑删除，更新del_flag字段为1,在表包含字段del_flag时，可以调用此方法，将数据隐藏）
	 */
	@RequestMapping(value = "deleteByLogic",method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="逻辑删除系统配置(Content-Type为text/html)", notes="逻辑删除系统配置(Content-Type为text/html)")
	@ApiImplicitParam(name = "sysConfig", value = "系统配置", dataType = "SysConfig",paramType="query")
	public Result deleteByLogic(SysConfig sysConfig, RedirectAttributes redirectAttributes) {
		sysConfigService.deleteByLogic(sysConfig);
		Result result = ResultFactory.getSuccessResult("删除系统配置成功");
		return result;
	}

	@RequestMapping(value = "delete/json",method ={RequestMethod.POST})
	@ApiOperation(value="删除系统配置(Content-Type为application/json)", notes="删除系统配置(Content-Type为application/json)")
	@ApiImplicitParam(name = "sysConfig", value = "系统配置", dataType = "SysConfig",paramType="body")
	public Result deleteJson(@RequestBody SysConfig sysConfig, RedirectAttributes redirectAttributes) {
		sysConfigService.delete(sysConfig);
		Result result = ResultFactory.getSuccessResult("删除系统配置成功");
		return result;
	}

	/**
	 * 删除系统配置（逻辑删除，更新del_flag字段为1,在表包含字段del_flag时，可以调用此方法，将数据隐藏）
	 */
	@RequestMapping(value = "deleteByLogic/json",method ={RequestMethod.POST})
	@ApiOperation(value="逻辑删除系统配置(Content-Type为application/json)", notes="逻辑删除系统配置(Content-Type为application/json)")
	@ApiImplicitParam(name = "sysConfig", value = "系统配置", dataType = "SysConfig",paramType="body")
	public Result deleteByLogicJson(@RequestBody SysConfig sysConfig, RedirectAttributes redirectAttributes) {
		sysConfigService.deleteByLogic(sysConfig);
		Result result = ResultFactory.getSuccessResult("删除系统配置成功");
		return result;
	}

	/**
	 * 批量删除系统配置
	 */
	//RequiresPermissions("sys:sysConfig:del")
	@RequestMapping(value = "deleteAll",method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="批量删除系统配置(Content-Type为text/html)", notes="批量删除系统配置(Content-Type为text/html)")
	@ApiImplicitParam(name = "ids", value = "系统配置ids,用,隔开", required = false, dataType = "String",paramType="query")
	public Result deleteAll(String ids, RedirectAttributes redirectAttributes) {
        String[] idArray = ids.split(",");
		for(String id : idArray){
			sysConfigService.delete(sysConfigService.get(id));
		}
        Result result = ResultFactory.getSuccessResult("删除系统配置成功");
		return result;
	}

	/**
	 * 批量删除系统配置（逻辑删除，更新del_flag字段为1,在表包含字段del_flag时，可以调用此方法，将数据隐藏）
	 */
	@RequestMapping(value = "deleteAllByLogic",method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="逻辑批量删除系统配置(Content-Type为text/html)", notes="逻辑批量删除系统配置(Content-Type为text/html)")
	@ApiImplicitParam(name = "ids", value = "系统配置ids,用,隔开", required = false, dataType = "String",paramType="query")
	public Result deleteAllByLogic(String ids, RedirectAttributes redirectAttributes) {
        String[] idArray = ids.split(",");
		for(String id : idArray){
			sysConfigService.deleteByLogic(sysConfigService.get(id));
		}
        Result result = ResultFactory.getSuccessResult("删除系统配置成功");
		return result;
	}

	@RequestMapping(value = "deleteAll/json",method ={RequestMethod.POST})
	@ApiOperation(value="批量删除系统配置(Content-Type为application/json)", notes="批量删除系统配置(Content-Type为application/json)")
	@ApiImplicitParam(name = "ids", value = "系统配置ids,用,隔开", required = false, dataType = "String",paramType="body")
	public Result deleteAllJson(@RequestBody String ids, RedirectAttributes redirectAttributes) {
        String[] idArray = ids.split(",");
		for(String id : idArray){
			sysConfigService.delete(sysConfigService.get(id));
		}
        Result result = ResultFactory.getSuccessResult("删除系统配置成功");
		return result;
	}

	/**
	 * 批量删除系统配置（逻辑删除，更新del_flag字段为1,在表包含字段del_flag时，可以调用此方法，将数据隐藏）
	 */
	@RequestMapping(value = "deleteAllByLogic/json",method ={RequestMethod.POST})
	@ApiOperation(value="逻辑批量删除系统配置(Content-Type为application/json)", notes="逻辑批量删除系统配置(Content-Type为application/json)")
	@ApiImplicitParam(name = "ids", value = "系统配置ids,用,隔开", required = false, dataType = "String",paramType="body")
	public Result deleteAllByLogicJson(@RequestBody String ids, RedirectAttributes redirectAttributes) {
        String[] idArray = ids.split(",");
		for(String id : idArray){
			sysConfigService.deleteByLogic(sysConfigService.get(id));
		}
        Result result = ResultFactory.getSuccessResult("删除系统配置成功");
		return result;
	}

}