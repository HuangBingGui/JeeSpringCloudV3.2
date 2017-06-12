/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeertd.org/">jeertd</a> All rights reserved.
 */
package com.jeertd.modules.oa.web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jeertd.common.config.Global;
import com.jeertd.common.persistence.BaseEntity;
import com.jeertd.common.persistence.Page;
import com.jeertd.common.utils.IdGen;
import com.jeertd.common.utils.StringUtils;
import com.jeertd.common.web.BaseController;
import com.jeertd.modules.oa.entity.OaNotify;
import com.jeertd.modules.oa.service.OaNotifyService;
import com.jeertd.modules.sys.entity.User;
import com.jeertd.modules.sys.utils.UserUtils;
import com.jeertd.rtd.api.extension.ClientCallDubbo;
import com.jeertd.rtd.api.extension.PropertiesUtil;


/**
 * 通知通告Controller
 * @author jeertd
 * @version 2014-05-16
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/oaNotify")
public class OaNotifyController extends BaseController {

	private static final String dubboserverurl = Global.getDubboServicePath();

	@Autowired
	private OaNotifyService oaNotifyService;
	
	@ModelAttribute
	public OaNotify get(@RequestParam(required=false) String id) {
		OaNotify entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oaNotifyService.get(id);
		}
		if (entity == null){
			entity = new OaNotify();
		}
		return entity;
	}
	
	@RequiresPermissions("oa:oaNotify:list")
	@RequestMapping(value = {"list", ""})
	public String list(OaNotify oaNotify, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		//Page<OaNotify> page = oaNotifyService.find(new Page<OaNotify>(request, response), oaNotify);
		String url= dubboserverurl+"/oaNotify/findPage";
	    String str=ClientCallDubbo.Post(request, url,"");
		Page<OaNotify> page =(Page<OaNotify>)JSON.parseObject(str, new TypeReference<Page<OaNotify>>(){});
		model.addAttribute("page", page);
		return "modules/oa/oaNotifyList";
	}

	/**
	 * 查看，增加，编辑报告表单页面
	 */
	@RequiresPermissions(value={"oa:oaNotify:view","oa:oaNotify:add","oa:oaNotify:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(OaNotify oaNotify, Model model) {
		if (StringUtils.isNotBlank(oaNotify.getId())){
			oaNotify = oaNotifyService.getRecordList(oaNotify);
		}
		model.addAttribute("oaNotify", oaNotify);
		return "modules/oa/oaNotifyForm";
	}

	@RequiresPermissions(value={"oa:oaNotify:add","oa:oaNotify:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(OaNotify oaNotify, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, oaNotify)){
			return form(oaNotify, model);
		}
		// 如果是修改，则状态为已发布，则不能再进行操作
		if (StringUtils.isNotBlank(oaNotify.getId())){
			OaNotify e = oaNotifyService.get(oaNotify.getId());
			if ("1".equals(e.getStatus())){
				addMessage(redirectAttributes, "已发布，不能操作！");
				return "redirect:" + adminPath + "/oa/oaNotify/?repage";
			}
		}
		User u=UserUtils.getUser();
		
		
		
		oaNotify.setCreateBy(u.getCreateBy());
		//oaNotify.setCreateDate(new Date());
		oaNotify.setUpdateBy(u.getUpdateBy());
		//oaNotify.setUpdateDate(new Date());
		//oaNotify.setId(IdGen.uuid());
		//oaNotify.setIsNewRecord(false);
		//String json ="{'content': 'testdubbotestdubbotestdubbo','currentUser': { 'dbName': 'mysql','id': 1,'isNewRecord': false,'name': '总公司'}}";
		String url= dubboserverurl+"/oaNotify/save";	
		String params=JSON.toJSONString(oaNotify);
	    try {
	    	System.out.print(params);
	        params = "oaNotify="+URLEncoder.encode(params, "utf-8");
			String str=ClientCallDubbo.Post(null, url,params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addMessage(redirectAttributes, "保存通知'" + oaNotify.getTitle() + "'失败:"+e.getMessage());
			return "redirect:" + adminPath + "/oa/oaNotify/?repage";
		}
	    
		//oaNotifyService.save(oaNotify);
		addMessage(redirectAttributes, "保存通知'" + oaNotify.getTitle() + "'成功");
		return "redirect:" + adminPath + "/oa/oaNotify/?repage";
	}
	
	@RequiresPermissions("oa:oaNotify:del")
	@RequestMapping(value = "delete")
	public String delete(OaNotify oaNotify, RedirectAttributes redirectAttributes) {
		
		String url= dubboserverurl+"/oaNotify/delete";	
		String params=JSON.toJSONString(oaNotify);
		
		  try {
		    	System.out.print(params);
		        params = "oaNotify="+URLEncoder.encode(params, "utf-8");
				String str=ClientCallDubbo.Post(null, url,params);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				addMessage(redirectAttributes, "删除通知'" + oaNotify.getTitle() + "'失败:"+e.getMessage());
				return "redirect:" + adminPath + "/oa/oaNotify/?repage";
			}
		    
		//oaNotifyService.delete(oaNotify);
		addMessage(redirectAttributes, "删除通知成功");
		return "redirect:" + adminPath + "/oa/oaNotify/?repage";
	}
	
	@RequiresPermissions("oa:oaNotify:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		long successCount=0,failureCount=0;
		String failureMsg="";
		String url= dubboserverurl+"/oaNotify/delete";	
		
		for(String id : idArray){
			OaNotify oaNotify=oaNotifyService.get(id);
			if(oaNotify==null){
				failureCount++;
				failureMsg+="id:"+id+",查询数据为空;";
				continue;
			}
			String params=JSON.toJSONString(oaNotify);
			  try {
			    	System.out.print(params);
			        params = "oaNotify="+URLEncoder.encode(params, "utf-8");
					String str=ClientCallDubbo.Post(null, url,params);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					failureCount++;
					e.printStackTrace();
					failureMsg=failureMsg+"id:"+id+"异常信息:"+e.getMessage()+";";
					continue;
					//addMessage(redirectAttributes, "删除通知'" + oaNotify.getTitle() + "'失败:"+e.getMessage());
					//return "redirect:" + adminPath + "/oa/oaNotify/?repage";
				}
			  successCount++;
		}
		//oaNotifyService.delete(oaNotifyService.get(id));
		addMessage(redirectAttributes, "删除通知成功条数:"+successCount+",删除通知失败条数："+failureCount+",失败信息:"+failureMsg+"");
		return "redirect:" + adminPath + "/oa/oaNotify/?repage";
	}
	
	/**
	 * 我的通知列表
	 * @throws Exception 
	 */
	@RequestMapping(value = "self")
	public String selfList(OaNotify oaNotify, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		oaNotify.setSelf(true);
		//Page<OaNotify> page = oaNotifyService.find(new Page<OaNotify>(request, response), oaNotify); 
		String url= dubboserverurl+"/oaNotify/findPage";
	    String str=ClientCallDubbo.Post(request, url,"");
		Page<OaNotify> page =(Page<OaNotify>)JSON.parseObject(str, new TypeReference<Page<OaNotify>>(){});
		model.addAttribute("page", page);
		return "modules/oa/oaNotifyList";
	}

	/**
	 * 我的通知列表-数据
	 */
	@RequiresPermissions("oa:oaNotify:view")
	@RequestMapping(value = "selfData")
	@ResponseBody
	public Page<OaNotify> listData(OaNotify oaNotify, HttpServletRequest request, HttpServletResponse response, Model model) {
		oaNotify.setSelf(true);
		Page<OaNotify> page = oaNotifyService.find(new Page<OaNotify>(request, response), oaNotify);
		return page;
	}
	
	/**
	 * 查看我的通知,重定向在当前页面打开
	 */
	@RequestMapping(value = "view")
	public String view(OaNotify oaNotify, Model model) {
		if (StringUtils.isNotBlank(oaNotify.getId())){
			oaNotifyService.updateReadFlag(oaNotify);
			oaNotify = oaNotifyService.getRecordList(oaNotify);
			model.addAttribute("oaNotify", oaNotify);
			return "modules/oa/oaNotifyForm";
		}
		return "redirect:" + adminPath + "/oa/oaNotify/self?repage";
	}

	/**
	 * 查看我的通知-数据
	 */
	@RequestMapping(value = "viewData")
	@ResponseBody
	public OaNotify viewData(OaNotify oaNotify, Model model) {
		if (StringUtils.isNotBlank(oaNotify.getId())){
			oaNotifyService.updateReadFlag(oaNotify);
			return oaNotify;
		}
		return null;
	}
	
	/**
	 * 查看我的通知-发送记录
	 */
	@RequestMapping(value = "viewRecordData")
	@ResponseBody
	public OaNotify viewRecordData(OaNotify oaNotify, Model model) {
		if (StringUtils.isNotBlank(oaNotify.getId())){
			oaNotify = oaNotifyService.getRecordList(oaNotify);
			return oaNotify;
		}
		return null;
	}
	
	/**
	 * 获取我的通知数目
	 */
	@RequestMapping(value = "self/count")
	@ResponseBody
	public String selfCount(OaNotify oaNotify, Model model) {
		oaNotify.setSelf(true);
		oaNotify.setReadFlag("0");
		return String.valueOf(oaNotifyService.findCount(oaNotify));
	}
}