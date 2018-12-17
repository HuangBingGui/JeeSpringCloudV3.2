/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.jeespring.modules.sys.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeespring.modules.oa.entity.OaNotify;
import com.jeespring.modules.oa.service.OaNotifyService;
import com.jeespring.modules.sys.entity.Dict;
import com.jeespring.modules.sys.entity.SysConfig;
import com.jeespring.modules.sys.service.DictService;
import com.jeespring.modules.sys.service.SysConfigService;
import com.jeespring.modules.sys.service.SysUserOnlineService;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.collect.Maps;
import com.jeespring.common.config.Global;
import com.jeespring.common.servlet.ValidateCodeServlet;
import com.jeespring.common.utils.CacheUtils;
import com.jeespring.common.utils.CookieUtils;
import com.jeespring.common.utils.IdGen;
import com.jeespring.common.utils.StringUtils;
import com.jeespring.common.json.AjaxJson;
import com.jeespring.common.persistence.Page;
import com.jeespring.common.security.shiro.session.SessionDAO;
import com.jeespring.common.web.AbstractBaseController;
import com.jeespring.modules.sys.security.FormAuthenticationFilter;
import com.jeespring.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.jeespring.modules.sys.utils.UserUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 登录Controller
 * @author 黄炳桂 516821420@qq.com
 * @version 2013-5-31
 */
@Controller
public class LoginController extends AbstractBaseController {
	
	@Autowired
	private SessionDAO sessionDAO;

	@Autowired
	private OaNotifyService oaNotifyService;

	@Autowired
	private SysConfigService sysConfigService;

	@Autowired
	private DictService dictService;

	/**
	 * 管理登录
	 * @throws IOException
	 */
	@RequestMapping(value = "${adminPath}/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		Principal principal = UserUtils.getPrincipal();

		// 默认页签模式
		String tabmode = CookieUtils.getCookie(request, "tabmode");
		if (tabmode == null){
			CookieUtils.setCookie(response, "tabmode", "1");
		}

		if (logger.isDebugEnabled()){
			logger.debug("login, active session size: {}", sessionDAO.getActiveSessions(false).size());
		}

		// 如果已登录，再次访问主页，则退出原账号。
		if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))){
			CookieUtils.setCookie(response, "LOGINED", "false");
		}

		// 如果已经登录，则跳转到管理首页
		if(principal != null && !principal.isMobileLogin()){
			return "redirect:" + adminPath;
		}


		SavedRequest savedRequest = WebUtils.getSavedRequest(request);//获取跳转到login之前的URL
		// 如果是手机没有登录跳转到到login，则返回JSON字符串
		if(savedRequest != null){
			String queryStr = savedRequest.getQueryString();
			if(	queryStr!=null &&( queryStr.contains("__ajax") || queryStr.contains("mobileLogin"))){
				AjaxJson j = new AjaxJson();
				j.setSuccess(false);
				j.setErrorCode("0");
				j.setMsg("没有登录!");
				return renderString(response, j);
			}
		}
		SysConfig sysConfig = new SysConfig();
		sysConfig.setType("loginImgUrl");
		List<SysConfig> loginImgUrlSysConfig= sysConfigService.findList(sysConfig);
		String loginImgUrl="";
		if(loginImgUrlSysConfig.size()>0){
			Random rand = new Random();
			loginImgUrl=loginImgUrlSysConfig.get(rand.nextInt(loginImgUrlSysConfig.size())).getPicture();
		}

		SysConfig validateCodeSysConfig = new SysConfig();
		validateCodeSysConfig.setType("validateCode");
		validateCodeSysConfig= sysConfigService.findListFirstCache(validateCodeSysConfig);
		SysConfig versionSysConfig = new SysConfig();
		versionSysConfig.setType("version");
		versionSysConfig= sysConfigService.findListFirstCache(versionSysConfig);
		model.addAttribute("loginImgUrl",loginImgUrl);
		model.addAttribute("systemMode", sysConfigService.systemMode());
		model.addAttribute("validateCode",validateCodeSysConfig.getValue());
		model.addAttribute("version",versionSysConfig.getValue());
		//return "modules/sys/sysLogin";
		return "base/login";
	}

	@RequestMapping(value = "${adminPath}/register", method = RequestMethod.GET)
	public String register(HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) throws IOException {
		Principal principal = UserUtils.getPrincipal();

		// 默认页签模式
		String tabmode = CookieUtils.getCookie(request, "tabmode");
		if (tabmode == null){
			CookieUtils.setCookie(response, "tabmode", "1");
		}

		if (logger.isDebugEnabled()){
			logger.debug("login, active session size: {}", sessionDAO.getActiveSessions(false).size());
		}

		// 如果已登录，再次访问主页，则退出原账号。
		if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))){
			CookieUtils.setCookie(response, "LOGINED", "false");
		}

		// 如果已经登录，则跳转到管理首页
		if(principal != null && !principal.isMobileLogin()){
			return "redirect:" + adminPath;
		}


		SavedRequest savedRequest = WebUtils.getSavedRequest(request);//获取跳转到login之前的URL
		// 如果是手机没有登录跳转到到login，则返回JSON字符串
		if(savedRequest != null){
			String queryStr = savedRequest.getQueryString();
			if(	queryStr!=null &&( queryStr.contains("__ajax") || queryStr.contains("mobileLogin"))){
				AjaxJson j = new AjaxJson();
				j.setSuccess(false);
				j.setErrorCode("0");
				j.setMsg("没有登录!");
				return renderString(response, j);
			}
		}

		if(sysConfigService.isDemoMode()){
			addMessage(redirectAttributes, sysConfigService.isDemoModeDescription());
			return "redirect:" + adminPath + "/login";
		}

		//return "modules/sys/sysRegister";
		return "base/register";
	}

	/**
	 * 登录失败，真正登录的POST请求由Filter完成
	 */
	@RequestMapping(value = "${adminPath}/login", method = RequestMethod.POST)
	public String loginFail(HttpServletRequest request, HttpServletResponse response, Model model) {
		Principal principal = UserUtils.getPrincipal();
		
		// 如果已经登录，则跳转到管理首页
		if(principal != null){
			return "redirect:" + adminPath;
		}

		String username = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_USERNAME_PARAM);
		boolean rememberMe = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM);
		boolean mobile = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_MOBILE_PARAM);
		String exception = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		String message = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM);
		
		if (StringUtils.isBlank(message) || StringUtils.equals(message, "null")){
			message = "用户或密码错误, 请重试.";
		}

		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, username);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM, rememberMe);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_MOBILE_PARAM, mobile);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, exception);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM, message);
		
		if (logger.isDebugEnabled()){
			logger.debug("login fail, active session size: {}, message: {}, exception: {}", 
					sessionDAO.getActiveSessions(false).size(), message, exception);
		}
		
		// 非授权异常，登录失败，验证码加1。
		if (!UnauthorizedException.class.getName().equals(exception)){
			model.addAttribute("isValidateCodeLogin", isValidateCodeLogin(username, true, false));
		}
		
		// 验证失败清空验证码
		request.getSession().setAttribute(ValidateCodeServlet.VALIDATE_CODE, IdGen.uuid());
		model.addAttribute("systemMode", sysConfigService.systemMode());

		return "base/login";
	}

	/**
	 * 管理登录
	 * @throws IOException 
	 */
	@RequestMapping(value = "${adminPath}/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		Principal principal = UserUtils.getPrincipal();
		// 如果已经登录，则跳转到管理首页
		if(principal != null){
			UserUtils.getSubject().logout();
			
		}
	   // 如果是手机客户端退出跳转到login，则返回JSON字符串
			String ajax = request.getParameter("__ajax");
			if(	ajax!=null){
				model.addAttribute("success", "1");
				model.addAttribute("msg", "退出成功");
				return renderString(response, model);
			}
		 return "redirect:" + adminPath+"/login";
	}

	/**
	 * 登录成功，进入管理首页
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "${adminPath}")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model,RedirectAttributes redirectAttributes) {
		Principal principal = UserUtils.getPrincipal();
		// 登录成功后，验证码计算器清零
		isValidateCodeLogin(principal.getLoginName(), false, true);
		
		if (logger.isDebugEnabled()){
			logger.debug("show index, active session size: {}", sessionDAO.getActiveSessions(false).size());
		}
		
		// 如果已登录，再次访问主页，则退出原账号。
		if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))){
			String logined = CookieUtils.getCookie(request, "LOGINED");
			if (StringUtils.isBlank(logined) || "false".equals(logined)){
				CookieUtils.setCookie(response, "LOGINED", "true");
			}else if (StringUtils.equals(logined, "true")){
				UserUtils.getSubject().logout();
				return "redirect:" + adminPath + "/login";
			}
		}

		SysConfig sysConfig = new SysConfig();
		sysConfig.setType("IMEnable");
		SysConfig IMEnableSysConfig= sysConfigService.findListFirstCache(sysConfig);
		sysConfig.setType("tabmode");
		SysConfig tabmodeSysConfig= sysConfigService.findListFirstCache(sysConfig);
		sysConfig.setType("skinSetttings");
		SysConfig skinSetttingsSysConfig= sysConfigService.findListFirstCache(sysConfig);
		sysConfig.setType("version");
		SysConfig versionSysConfig= sysConfigService.findListFirstCache(sysConfig);
		model.addAttribute("IMEnable", IMEnableSysConfig.getValue());
		model.addAttribute("tabmode", tabmodeSysConfig.getValue());
		model.addAttribute("skinSetttings", skinSetttingsSysConfig.getValue());
		model.addAttribute("systemMode", sysConfigService.systemMode());
		model.addAttribute("version", versionSysConfig.getValue());
		// 如果是手机登录，则返回JSON字符串
		if (principal.isMobileLogin()){
			if (request.getParameter("login") != null){
				return renderString(response, principal);
			}
			if (request.getParameter("index") != null){
				return "modules/sys/sysIndex";
			}
			return "redirect:" + adminPath + "/login";
		}
		
//		// 登录成功后，获取上次登录的当前站点ID
//		UserUtils.putCache("siteId", StringUtils.toLong(CookieUtils.getCookie(request, "siteId")));

//		System.out.println("==========================a");
//		try {
//			byte[] bytes = com.jeespring.common.utils.FileUtils.readFileToByteArray(
//					com.jeespring.common.utils.FileUtils.getFile("c:\\sxt.dmp"));
//			UserUtils.getSession().setAttribute("kkk", bytes);
//			UserUtils.getSession().setAttribute("kkk2", bytes);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
////		for (int i=0; i<1000000; i++){
////			//UserUtils.getSession().setAttribute("a", "a");
////			request.getSession().setAttribute("aaa", "aa");
////		}
//		System.out.println("==========================b");
		//
		//

		//return "modules/sys/sysIndex";
		return "base/index";
	}
	
	/**
	 * 获取主题方案
	 */
	@RequestMapping(value = "/theme/{theme}")
	public String getThemeInCookie(@PathVariable String theme, HttpServletRequest request, HttpServletResponse response){
		if (StringUtils.isNotBlank(theme)){
			CookieUtils.setCookie(response, "theme", theme);
		}else{
			theme = CookieUtils.getCookie(request, "theme");
		}
		return "redirect:"+request.getParameter("url");
	}
	
	/**
	 * 是否是验证码登录
	 * @param useruame 用户名
	 * @param isFail 计数加1
	 * @param clean 计数清零
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isValidateCodeLogin(String useruame, boolean isFail, boolean clean){
		Map<String, Integer> loginFailMap = (Map<String, Integer>)CacheUtils.get("loginFailMap");
		if (loginFailMap==null){
			loginFailMap = Maps.newHashMap();
			CacheUtils.put("loginFailMap", loginFailMap);
		}
		Integer loginFailNum = loginFailMap.get(useruame);
		if (loginFailNum==null){
			loginFailNum = 0;
		}
		if (isFail){
			loginFailNum++;
			loginFailMap.put(useruame, loginFailNum);
		}
		if (clean){
			loginFailMap.remove(useruame);
		}
		return loginFailNum >= 3;
	}
	
	
	/**
	 * 首页
	 * @throws IOException 
	 */
	@RequestMapping(value = "${adminPath}/home")
	public String home(HttpServletRequest request, HttpServletResponse response, Model model,RedirectAttributes redirectAttributes) throws IOException {
		SysConfig sysConfig = new SysConfig(); //new一个新的xxx对象
		sysConfig.setType("homePageAboveInfomation");//传值
		List<SysConfig> indexSysConfig= sysConfigService.findList(sysConfig);//查询系统配置项
		sysConfig.setType("homePageTechnical");//传值
		SysConfig homePageTechnicalSysConfig= sysConfigService.findListFirstCache(sysConfig);//查询系统配置项
		sysConfig.setType("homePageAuthorization");//传值
		SysConfig homePageAuthorizationSysConfig= sysConfigService.findListFirstCache(sysConfig);//查询系统配置项
		sysConfig.setType("homePageContactInfo");//传值
		SysConfig homePageContactInfoSysConfig= sysConfigService.findListFirstCache(sysConfig);//查询系统配置项
		sysConfig.setType("homePageInfomation");//传值
		SysConfig homePageInfomationSysConfig= sysConfigService.findListFirstCache(sysConfig);//查询系统配置项
		sysConfig.setType("version");//传值
		SysConfig versionSysConfig= sysConfigService.findListFirstCache(sysConfig);//查询系统配置项
		model.addAttribute("homePageTechnical",homePageTechnicalSysConfig.getDescription().toString());
		model.addAttribute("homePageAuthorization",homePageAuthorizationSysConfig.getDescription().toString());
		model.addAttribute("homePageContactInfo",homePageContactInfoSysConfig.getDescription().toString());
		model.addAttribute("homePageInfomation",homePageInfomationSysConfig.getDescription().toString());
		model.addAttribute("version",versionSysConfig.getValue());

		if(indexSysConfig.size() == 0 ) {
			indexSysConfig.add(new SysConfig());
		}

		OaNotify oaNotify = new OaNotify();
		Dict dict = new Dict();
		dict.setValue("1");  //传一个数据值
		//dict.setLabel("会议通知"); //传一个标签名
		List<Dict> getDict =dictService.findList(dict);//按传的值查数据字典
		if(getDict != null){
			if(getDict.size() > 0){
				oaNotify.setType(getDict.get(0).getValue());
			}
		}
		Page<OaNotify>  pageOaNotify = oaNotifyService.find(new Page<OaNotify>(request, response), oaNotify);
		model.addAttribute("pageOaNotify",pageOaNotify);

		dict = new Dict();
		oaNotify = new OaNotify();
		dict.setValue("4");
		//dict.setLabel("升级日志");
		List<Dict> getDictLog =dictService.findList(dict);
		if(getDictLog != null){
			if(getDictLog.size() > 0){
				oaNotify.setType(getDictLog.get(0).getValue());
			}
		}
		Page<OaNotify>  pageOaNotifyLog = oaNotifyService.find(new Page<OaNotify>(request, response), oaNotify);
		model.addAttribute("pageOaNotifyLog",pageOaNotifyLog);

		dict = new Dict();
		oaNotify = new OaNotify();
		dict.setValue("5");
		//dict.setLabel("技术支持");
		List<Dict> getDictTechnology =dictService.findList(dict);
		if(getDictTechnology != null){
			if(getDictTechnology.size() > 0){
				oaNotify.setType(getDictTechnology.get(0).getValue());
			}
		}
		Page<OaNotify>  pageOaNotifyTechnology = oaNotifyService.find(new Page<OaNotify>(request, response), oaNotify);
		model.addAttribute("pageOaNotifyTechnology",pageOaNotifyTechnology);

		model.addAttribute("indexSysConfig",indexSysConfig.get(0));
		//return "modules/sys/sysHome";
		return "base/home";
	}
}
