/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.jeespring.modules.oauth.rest;

import com.jeespring.common.web.AbstractBaseController;
import com.jeespring.common.web.Result;
import com.jeespring.common.web.ResultFactory;
import com.jeespring.modules.oauth.service.OauthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统配置Controller
 * @author 黄炳桂 516821420@qq.com
 * @version 2017-11-17
 */
@RestController
@RequestMapping(value = "/rest/oauth")
@Api(value="Oauth平台授权接口(分布式)", description="Oauth平台授权接口(分布式)")
public class oauthRestController extends AbstractBaseController {

	@Autowired
	private OauthService oauthService;

	@RequestMapping(value = {"test"},method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="token test平台授权测试接口(Content-Type为text/html)", notes="token test平台授权测试接口(Content-Type为text/html)")
	public Result test() {
		return ResultFactory.getSuccessResult("测试成功！");
	}

	@RequestMapping(value = {"token"},method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="token平台授权接口(Content-Type为text/html)", notes="token平台授权接口(Content-Type为text/html)")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "oauthId", value = "客户id", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "oauthSecret", value = "客户密钥", required = false, dataType = "String", paramType = "query")
	})
	public Result tokenRequestParam(@RequestParam(required=false) String oauthId, @RequestParam(required=false) String oauthSecret, HttpServletRequest request, HttpServletResponse response) {
		return oauthService.token(oauthId,oauthSecret,request.getRemoteAddr());
	}

	@RequestMapping(value = {"token/json"},method ={RequestMethod.POST})
	@ApiOperation(value="系统配置信息(Content-Type为application/json)", notes="系统配置信息(Content-Type为application/json)")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "oauthId", value = "客户id", required = false, dataType = "String", paramType = "body"),
			@ApiImplicitParam(name = "oauthSecret", value = "客户密钥", required = false, dataType = "String", paramType = "body")
	})
	public Result tokenJsonRequestBody(@RequestBody String oauthId,@RequestBody String oauthSecret, HttpServletRequest request, HttpServletResponse response) {
		return oauthService.token(oauthId,oauthSecret,request.getRemoteAddr());
	}

	@RequestMapping(value = {"checkToken"},method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="checkToken平台Token检查接口(Content-Type为text/html)", notes="checkToken平台Token检查接口(Content-Type为text/html)")
	@ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String", paramType = "query")
	public Result checkTokenRequestParam(@RequestParam(required=false) String token, HttpServletRequest request, HttpServletResponse response){
		return oauthService.checkToken(token,request.getRemoteAddr());
	}

	@RequestMapping(value = {"checkToken/json"},method ={RequestMethod.POST})
	@ApiOperation(value="checkToken平台Token检查接口(Content-Type为application/json)", notes="checkToken平台Token检查接口(Content-Type为application/json)")
	@ApiImplicitParam(name = "token", value = "token", required = false, dataType = "String", paramType = "body")
	public Result checkTokenRequestBody(@RequestBody(required=false) String token, HttpServletRequest request, HttpServletResponse response){
		return oauthService.checkToken(token,request.getRemoteAddr());
	}

	@RequestMapping(value = {"faild"},method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="授权平台接口失败(Content-Type为application/html)", notes="授权平台接口失败(Content-Type为application/html)")
	public Result faild( HttpServletRequest request, HttpServletResponse response){
		return ResultFactory.getErrorResult("oauth token授权失败！");
	}
	@RequestMapping(value = {"apiTimeLimiFaild"},method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="授权平台调用次数失败(Content-Type为application/html)", notes="授权平台调用次数失败(Content-Type为application/html)")
	public Result apiTimeLimiFaild( HttpServletRequest request, HttpServletResponse response){
		String apiTimeLimi=request.getParameter("apiTimeLimi");
		if(apiTimeLimi==null) {
            apiTimeLimi = "";
        }
		return ResultFactory.getErrorResult("调用失败，接口允许最多调用"+apiTimeLimi+"次数！15分钟后解锁！");
	}
	@RequestMapping(value = {"userOnlineAmountFaild"},method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="在线用户数量已满失败(Content-Type为application/html)", notes="在线用户数量已满失败(Content-Type为application/html)")
	public Result userOnlineAmountFaild( HttpServletRequest request, HttpServletResponse response){
		return oauthService.userOnlineAmount();
	}

	@RequestMapping(value = {"userOnlineAmount"},method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="在线用户数量(Content-Type为application/html)", notes="在线用户数量(Content-Type为application/html)")
	public Result userOnlineAmount( HttpServletRequest request, HttpServletResponse response){
		return oauthService.userOnlineAmount();
	}

	@RequestMapping(value = {"getApiTimeLimi"},method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="授权平台调用次数(Content-Type为application/html)", notes="授权平台调用次数(Content-Type为application/html)")
	public Result getApiTimeLimi( HttpServletRequest request, HttpServletResponse response){
		return oauthService.getApiTimeLimi(request.getRemoteAddr());
	}

	@RequestMapping(value = {"getApiTime"},method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="调用次数(Content-Type为application/html)", notes="调用次数(Content-Type为application/html)")
	public Result getApiTime( HttpServletRequest request, HttpServletResponse response){
		return oauthService.getApiTime();
	}

}