/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.company.project.modules.duiba;


import cn.com.duiba.credits.sdk.BulidUrl;
import cn.com.duiba.credits.sdk.CreditTool;
import cn.com.duiba.credits.sdk.entity.CreditConsumeParams;
import cn.com.duiba.credits.sdk.result.CreditConsumeResult;
import com.jeespring.common.web.AbstractBaseController;
import com.jeespring.common.web.Result;
import com.jeespring.common.web.ResultFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 订单Controller
 * @author 黄炳桂
 * @version 2017-11-13
 */
@RestController
@RequestMapping(value = "/duiba")
@Api(value="duiba", description="积分兑换")
public class duibaRestController extends AbstractBaseController {
	@RequestMapping(value = {"index"},method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="积分兑换首页(Content-Type为text/html)", notes="积分兑换首页(Content-Type为text/html)")
	public Result index(HttpServletRequest request, HttpServletResponse response) {
		Long credits=Long.valueOf(0);
		String redirect=null;
		if(request.getParameter("credits")!=null){
			credits=Long.valueOf(request.getParameter("credits"));
		}
		if(request.getParameter("redirect")!=null){
			redirect=request.getParameter("redirect");
		}
		if(request.getParameter("dbredirect")!=null){
			redirect=request.getParameter("dbredirect");
		}
		Result result = ResultFactory.getSuccessResult();
		result.setResultObject(BulidUrl.buildAutoLoginRequest(request.getParameter("uid"),credits ,redirect));
		return result;
	}

	@RequestMapping(value = {"signIn"},method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="签到(Content-Type为text/html)", notes="签到(Content-Type为text/html)")
	public Result signIn(HttpServletRequest request, HttpServletResponse response) {
		Long credits=Long.valueOf(0);
		String redirect=null;
		if(request.getParameter("credits")!=null){
			credits=Long.valueOf(request.getParameter("credits"));
		}
		if(request.getParameter("redirect")!=null){
			redirect=request.getParameter("redirect");
		}else{
			//redirect="http://activity.m.duiba.com.cn/activity/acShareIndex?url=https%3A%2F%2Factivity-1.m.duiba.com.cn%2Fnewtools%2Findex%3Fid%3D2685169&apk=3dqRcUoEnKDLAj5ziF63DgkZBT3z";
			redirect="https://activity.m.duiba.com.cn/signactivity/index?id=90&dpm=43346.41.1.0&dcm=216.90.51.0&appKey=7HWwRiKJXPiyh7pF4QnYAieVPQL&open4share=tongdun";
		}
		Result result = ResultFactory.getSuccessResult();
		result.setResultObject(BulidUrl.buildAutoLoginRequest(request.getParameter("uid"),credits ,redirect));
		return result;
	}

	@RequestMapping(value = {"cancle"},method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="扣积分(Content-Type为text/html)", notes="扣积分(Content-Type为text/html)")
	public void cancle(HttpServletRequest request, HttpServletResponse response) {
		CreditTool tool=new CreditTool(BulidUrl.AppKey, BulidUrl.AppSecret);
		try {
			CreditConsumeParams params= tool.parseCreditConsume(request);//利用tool来解析这个请求
			String uid=params.getUid();//用户id
			Long credits=params.getCredits();
			String type=params.getType();//获取兑换类型
			String alipay=params.getAlipay();//获取支付宝账号
			//其他参数参见 params的属性字段

			//TODO 开发者系统对uid用户扣除credits个积分

			String bizId="001";//返回开发者系统中的业务订单id
			CreditConsumeResult result=new CreditConsumeResult(true);
			result.setBizId(bizId);
			response.getWriter().write(result.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = {"add"},method ={RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="加积分(Content-Type为text/html)", notes="加积分(Content-Type为text/html)")
	public String add(HttpServletRequest request, HttpServletResponse response) {
		return "{'status': 'ok','errorMessage': '失败原因''credits': '100'}";
	}
}