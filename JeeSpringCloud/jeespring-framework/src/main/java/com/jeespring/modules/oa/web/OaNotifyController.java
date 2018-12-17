/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.jeespring.modules.oa.web;

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

import com.jeespring.common.utils.StringUtils;
import com.jeespring.common.persistence.Page;
import com.jeespring.common.web.AbstractBaseController;
import com.jeespring.modules.oa.entity.OaNotify;
import com.jeespring.modules.oa.service.OaNotifyService;


/**
 * 通知通告Controller
 * @author 黄炳桂 516821420@qq.com
 * @version 2014-05-16
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/oaNotify")
public class OaNotifyController extends AbstractBaseController {

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
		Page<OaNotify> page=new Page<OaNotify>();
		page = oaNotifyService.find(new Page<OaNotify>(request, response), oaNotify);
		model.addAttribute("page", page);
		return "modules/oa/oaNotifyList";
	}

	/**
	 * 查看，增加，编辑报告表单页面
	 */
	@RequiresPermissions(value={"oa:oaNotify:view","oa:oaNotify:add","oa:oaNotify:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(OaNotify oaNotify, Model model,HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("action", request.getParameter("action"));
		if (StringUtils.isNotBlank(oaNotify.getId())){
			oaNotify = oaNotifyService.getRecordList(oaNotify);
		}
		model.addAttribute("oaNotify", oaNotify);
		return "modules/oa/oaNotifyForm";
	}

	@RequiresPermissions(value={"oa:oaNotify:add","oa:oaNotify:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(OaNotify oaNotify, Model model,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, oaNotify)){
			return form(oaNotify, model,request,response);
		}
		// 如果是修改，则状态为已发布，则不能再进行操作
		if (StringUtils.isNotBlank(oaNotify.getId())){
			OaNotify e = oaNotifyService.get(oaNotify.getId());
			if ("1".equals(e.getStatus())){
				addMessage(redirectAttributes, "已发布，不能操作！");
				return "redirect:" + adminPath + "/oa/oaNotify/?repage";
			}
		}


	     	 oaNotifyService.save(oaNotify);

		addMessage(redirectAttributes, "保存通知'" + oaNotify.getTitle() + "'成功");
		return "redirect:" + adminPath + "/oa/oaNotify/?repage";
	}
	
	@RequiresPermissions("oa:oaNotify:del")
	@RequestMapping(value = "delete")
	public String delete(OaNotify oaNotify, RedirectAttributes redirectAttributes) {
		
		
		  oaNotifyService.delete(oaNotify);

		addMessage(redirectAttributes, "删除通知成功");
		return "redirect:" + adminPath + "/oa/oaNotify/?repage";
	}
	
	@RequiresPermissions("oa:oaNotify:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {


        String[] idArray = ids.split(",");
		long successCount=0,failureCount=0;
		String failureMsg="";
		
		
		for(String id : idArray){
			OaNotify oaNotify=oaNotifyService.get(id);
			if(oaNotify==null){
				failureCount++;
				failureMsg+="id:"+id+",查询数据为空;";
				continue;
			}
			
				 oaNotifyService.delete(oaNotifyService.get(id));

		   successCount++;
		}
		
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
		Page<OaNotify> page=new Page<OaNotify>();
		
		   page = oaNotifyService.find(new Page<OaNotify>(request, response), oaNotify); 

		
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