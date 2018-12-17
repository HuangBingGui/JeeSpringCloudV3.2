/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.jeespring.modules.utils;

import com.jeespring.common.utils.SendMailUtil;
import com.jeespring.common.web.AbstractBaseController;
import com.jeespring.modules.sys.entity.SysConfig;
import com.jeespring.modules.sys.service.SysConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 邮箱Controller
 * @author 黄炳桂 516821420@qq.com
 * @version 2014-05-16
 */
@RestController
@RequestMapping(value = "rest/sys/email")
@Api(value="email邮件云接口", description="email邮件云接口")
public class EmailRestController extends AbstractBaseController {
	@Autowired
	private SysConfigService sysConfigService;

	@RequestMapping(value = {"sendMailException"},method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="发送邮件(Content-Type为text/html)", notes="发送邮件(Content-Type为text/html)")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "subject", value = "邮件主题", required = false, dataType = "String",paramType="query"),
			@ApiImplicitParam(name = "message", value = "邮件内容", required = false, dataType = "String",paramType="query")
	})
	public void sendMailException(@RequestParam(required=false) String subject, @RequestParam(required=false) String message) {
		//SendMailUtil.sendCommonMail("1638077616@qq.com","jeespring",message);
		SysConfig entity = new SysConfig();
		entity.setType("toExceptionMailAddr");
		List<SysConfig> sysConfigList=sysConfigService.findList(entity);
		if(sysConfigList.size()>=0){
			SendMailUtil.sendCommonMail(sysConfigList.get(0).getValue(),"(异常邮件)"+subject,message);
		}
	}

	@RequestMapping(value = {"sendMailException/json"},method ={RequestMethod.POST})
	@ApiOperation(value="发送邮件(Content-Type为application/json)", notes="发送邮件(application/json)")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "subject", value = "邮件主题", required = false, dataType = "String",paramType="body"),
			@ApiImplicitParam(name = "message", value = "邮件内容", required = false, dataType = "String",paramType="body")
	})
	public void sendMailExceptionJson(@RequestBody(required=false) String subject, @RequestBody(required=false) String message) {
		//SendMailUtil.sendCommonMail("1638077616@qq.com","jeespring",message);
		SysConfig entity = new SysConfig();
		entity.setType("toExceptionMailAddr");
		List<SysConfig> sysConfigList=sysConfigService.findList(entity);
		if(sysConfigList.size()>=0){
			SendMailUtil.sendCommonMail(sysConfigList.get(0).getValue(),"(异常邮件)"+subject,message);
		}
	}

	@RequestMapping(value = {"sendMail"},method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="发送邮件(Content-Type为text/html)", notes="发送邮件(Content-Type为text/html)")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "toMailAddr", value = "接收邮箱", required = false, dataType = "String",paramType="query"),
		@ApiImplicitParam(name = "subject", value = "邮件主题", required = false, dataType = "String",paramType="query"),
		@ApiImplicitParam(name = "message", value = "邮件内容", required = false, dataType = "String",paramType="query")
	})
	public void sendMail(@RequestParam(required=false) String toMailAddr, @RequestParam(required=false) String subject, @RequestParam(required=false) String message) {
		//SendMailUtil.sendCommonMail("1638077616@qq.com","jeespring",message);
		SendMailUtil.sendCommonMail(toMailAddr,subject,message);
	}

	@RequestMapping(value = {"sendMail/json"},method ={RequestMethod.POST})
	@ApiOperation(value="发送邮件(Content-Type为application/json)", notes="发送邮件(application/json)")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "toMailAddr", value = "接收邮箱", required = false, dataType = "String",paramType="body"),
			@ApiImplicitParam(name = "subject", value = "邮件主题", required = false, dataType = "String",paramType="body"),
			@ApiImplicitParam(name = "message", value = "邮件内容", required = false, dataType = "String",paramType="body")
	})
	public void sendMailJson(@RequestBody(required=false) String toMailAddr, @RequestBody(required=false) String subject, @RequestBody(required=false) String message) {
		//SendMailUtil.sendCommonMail("1638077616@qq.com","jeespring",message);
		SendMailUtil.sendCommonMail(toMailAddr,subject,message);
	}

	@RequestMapping(value = {"sendMailFromTo"},method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="发送邮件(Content-Type为text/html)", notes="发送邮件(Content-Type为text/html)")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "fromMailAddr", value = "发送邮箱", required = false, dataType = "String",paramType="query"),
			@ApiImplicitParam(name = "fromMailName", value = "发送邮箱名称", required = false, dataType = "String",paramType="query"),
			@ApiImplicitParam(name = "fromMailUsername", value = "发送邮箱用户名", required = false, dataType = "String",paramType="query"),
			@ApiImplicitParam(name = "fromMailPassword", value = "发送邮箱密码", required = false, dataType = "String",paramType="query"),
			@ApiImplicitParam(name = "toMailAddr", value = "接收邮箱", required = false, dataType = "String",paramType="query"),
			@ApiImplicitParam(name = "subject", value = "邮件主题", required = false, dataType = "String",paramType="query"),
			@ApiImplicitParam(name = "message", value = "邮件内容", required = false, dataType = "String",paramType="query")
	})
	public void sendMailFromTo(@RequestParam(required=false) String fromMailAddr,@RequestParam(required=false) String fromMailName,
							   @RequestParam(required=false) String fromMailUsername,@RequestParam(required=false) String fromMailPassword,
							   @RequestParam(required=false) String toMailAddr, @RequestParam(required=false) String subject,
							   @RequestParam(required=false)  String message) {
		//SendMailUtil.sendCommonMail("516821420@qq.com","jeespring","516821420@qq.com","lzxfcmfbrtmdcbcg","1638077616@qq.com","jeespring",message);
		SendMailUtil.sendCommonMailFromTo(fromMailAddr,fromMailName,fromMailUsername,fromMailPassword,toMailAddr,subject,message);
	}

	@RequestMapping(value = {"sendMailFromTo/json"},method ={RequestMethod.POST})
	@ApiOperation(value="发送邮件(Content-Type为application/json)", notes="发送邮件(Content-Type为application/json)")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "fromMailAddr", value = "发送邮箱", required = false, dataType = "String",paramType="body"),
			@ApiImplicitParam(name = "fromMailName", value = "发送邮箱名称", required = false, dataType = "String",paramType="body"),
			@ApiImplicitParam(name = "fromMailUsername", value = "发送邮箱用户名", required = false, dataType = "String",paramType="body"),
			@ApiImplicitParam(name = "fromMailPassword", value = "发送邮箱密码", required = false, dataType = "String",paramType="body"),
			@ApiImplicitParam(name = "toMailAddr", value = "接收邮箱", required = false, dataType = "String",paramType="body"),
			@ApiImplicitParam(name = "subject", value = "邮件主题", required = false, dataType = "String",paramType="body"),
			@ApiImplicitParam(name = "message", value = "邮件内容", required = false, dataType = "String",paramType="body")
	})
	public void sendMailFromToJson(@RequestBody(required=false) String fromMailAddr,@RequestBody(required=false) String fromMailName,
							   @RequestBody(required=false) String fromMailUsername,@RequestBody(required=false) String fromMailPassword,
							   @RequestBody(required=false) String toMailAddr, @RequestBody(required=false) String subject,
							   @RequestBody(required=false)  String message) {
		//SendMailUtil.sendCommonMail("516821420@qq.com","jeespring","516821420@qq.com","lzxfcmfbrtmdcbcg","1638077616@qq.com","jeespring",message);
		SendMailUtil.sendCommonMailFromTo(fromMailAddr,fromMailName,fromMailUsername,fromMailPassword,toMailAddr,subject,message);
	}

}
