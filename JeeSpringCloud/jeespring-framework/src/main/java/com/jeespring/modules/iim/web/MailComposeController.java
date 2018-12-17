/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.jeespring.modules.iim.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jeespring.common.config.Global;
import com.jeespring.common.utils.StringUtils;
import com.jeespring.common.persistence.Page;
import com.jeespring.common.web.AbstractBaseController;
import com.jeespring.modules.iim.entity.MailBox;
import com.jeespring.modules.iim.entity.MailCompose;
import com.jeespring.modules.iim.entity.MailPage;
import com.jeespring.modules.iim.service.MailBoxService;
import com.jeespring.modules.iim.service.MailComposeService;
import com.jeespring.modules.iim.service.MailService;
import com.jeespring.modules.sys.entity.User;
import com.jeespring.modules.sys.service.SystemService;
import com.jeespring.modules.sys.utils.UserUtils;

/**
 * 发件箱Controller
 * @author 黄炳桂 516821420@qq.com
 * @version 2015-11-13
 */
@Controller
@RequestMapping(value = "${adminPath}/iim/mailCompose")
public class MailComposeController extends AbstractBaseController {

	@Autowired
	private MailComposeService mailComposeService;
	
	@Autowired
	private MailBoxService mailBoxService;
	
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private MailService mailService;
	
	@ModelAttribute
	public MailCompose get(@RequestParam(required=false) String id) {
		MailCompose entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = mailComposeService.get(id);
		}
		if (entity == null){
			entity = new MailCompose();
		}
		return entity;
	}
	
	
	
	/*
	 * 写站内信
	 */
	@RequestMapping(value = {"sendLetter"})
	public String sendLetter(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		user = systemService.getUser(user.getId());
		model.addAttribute("receiver", user);
		
		//查询未读的条数
		MailBox serachBox = new MailBox();
		serachBox.setReadstatus("0");
		serachBox.setReceiver(UserUtils.getUser());
		model.addAttribute("noReadCount", mailBoxService.getCount(serachBox));
		
		//查询总条数
		MailBox serachBox2 = new MailBox();
		serachBox2.setReceiver(UserUtils.getUser());
		model.addAttribute("mailBoxCount", mailBoxService.getCount(serachBox2));
		
		//查询已发送条数
		MailCompose serachBox3 = new MailCompose();
		serachBox3.setSender(UserUtils.getUser());
		serachBox3.setStatus("1");//已发送
		model.addAttribute("mailComposeCount", mailComposeService.getCount(serachBox3));
		
		//查询草稿箱条数
		MailCompose serachBox4 = new MailCompose();
		serachBox4.setSender(UserUtils.getUser());
		serachBox4.setStatus("0");//草稿
		model.addAttribute("mailDraftCount", mailComposeService.getCount(serachBox4));
		
		return "modules/iim/mail_send";
	}
	
	/*
	 * 回复站内信
	 */
	@RequestMapping(value = {"replyLetter"})
	public String replyLetter(MailBox mailBox, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("mailBox",  mailBoxService.get(mailBox.getId()));
		
		//查询未读的条数
		MailBox serachBox = new MailBox();
		serachBox.setReadstatus("0");
		serachBox.setReceiver(UserUtils.getUser());
		model.addAttribute("noReadCount", mailBoxService.getCount(serachBox));
		
		//查询总条数
		MailBox serachBox2 = new MailBox();
		serachBox2.setReceiver(UserUtils.getUser());
		model.addAttribute("mailBoxCount", mailBoxService.getCount(serachBox2));
		
		//查询已发送条数
		MailCompose serachBox3 = new MailCompose();
		serachBox3.setSender(UserUtils.getUser());
		serachBox3.setStatus("1");//已发送
		model.addAttribute("mailComposeCount", mailComposeService.getCount(serachBox3));
		
		//查询草稿箱条数
		MailCompose serachBox4 = new MailCompose();
		serachBox4.setSender(UserUtils.getUser());
		serachBox4.setStatus("0");//草稿
		model.addAttribute("mailDraftCount", mailComposeService.getCount(serachBox4));
		
		return "modules/iim/mail_reply";
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(MailCompose mailCompose, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		Page<MailCompose> page = mailComposeService.findPage(new MailPage<MailCompose>(request, response), mailCompose); 
		model.addAttribute("page", page);
		
		//查询未读的条数
		MailBox serachBox = new MailBox();
		serachBox.setReadstatus("0");
		serachBox.setReceiver(UserUtils.getUser());
		model.addAttribute("noReadCount", mailBoxService.getCount(serachBox));
		
		//查询总条数
		MailBox serachBox2 = new MailBox();
		serachBox2.setReceiver(UserUtils.getUser());
		model.addAttribute("mailBoxCount", mailBoxService.getCount(serachBox2));
		
		//查询已发送条数
		MailCompose serachBox3 = new MailCompose();
		serachBox3.setSender(UserUtils.getUser());
		serachBox3.setStatus("1");//已发送
		model.addAttribute("mailComposeCount", mailComposeService.getCount(serachBox3));
		
		//查询草稿箱条数
		MailCompose serachBox4 = new MailCompose();
		serachBox4.setSender(UserUtils.getUser());
		serachBox4.setStatus("0");//草稿
		model.addAttribute("mailDraftCount", mailComposeService.getCount(serachBox4));
		
		
		if(mailCompose.getStatus()== null || "0".equals(mailCompose.getStatus())){
			return "modules/iim/mailDraftList";//草稿箱
		}
		return "modules/iim/mailComposeList";//已发送
	}

	@RequestMapping(value = "detail")//打开已发送信件
	public String detail(MailCompose mailCompose, Model model) {
		model.addAttribute("mailCompose", mailCompose);
		
		
		//查询未读的条数
		MailBox serachBox = new MailBox();
		serachBox.setReadstatus("0");
		serachBox.setReceiver(UserUtils.getUser());
		model.addAttribute("noReadCount", mailBoxService.getCount(serachBox));
		
		//查询总条数
		MailBox serachBox2 = new MailBox();
		serachBox2.setReceiver(UserUtils.getUser());
		model.addAttribute("mailBoxCount", mailBoxService.getCount(serachBox2));
		
		//查询已发送条数
		MailCompose serachBox3 = new MailCompose();
		serachBox3.setSender(UserUtils.getUser());
		serachBox3.setStatus("1");//已发送
		model.addAttribute("mailComposeCount", mailComposeService.getCount(serachBox3));
		
		//查询草稿箱条数
		MailCompose serachBox4 = new MailCompose();
		serachBox4.setSender(UserUtils.getUser());
		serachBox4.setStatus("0");//草稿
		model.addAttribute("mailDraftCount", mailComposeService.getCount(serachBox4));
		
		return "modules/iim/mailComposeDetail";
	}
	
	@RequestMapping(value = "draftDetail")//打开草稿
	public String draftDetail(MailCompose mailCompose, Model model) {
		//查询未读的条数
		MailBox serachBox = new MailBox();
		serachBox.setReadstatus("0");
		serachBox.setReceiver(UserUtils.getUser());
		model.addAttribute("noReadCount", mailBoxService.getCount(serachBox));
		
		//查询总条数
		MailBox serachBox2 = new MailBox();
		serachBox2.setReceiver(UserUtils.getUser());
		model.addAttribute("mailBoxCount", mailBoxService.getCount(serachBox2));
		
		//查询已发送条数
		MailCompose serachBox3 = new MailCompose();
		serachBox3.setSender(UserUtils.getUser());
		serachBox3.setStatus("1");//已发送
		model.addAttribute("mailComposeCount", mailComposeService.getCount(serachBox3));
		
		//查询草稿箱条数
		MailCompose serachBox4 = new MailCompose();
		serachBox4.setSender(UserUtils.getUser());
		serachBox4.setStatus("0");//草稿
		model.addAttribute("mailDraftCount", mailComposeService.getCount(serachBox4));
		
		mailCompose = mailComposeService.get(mailCompose.getId());
		model.addAttribute("mailCompose", mailCompose);
		return "modules/iim/mailDraftDetail";
	}

	@RequestMapping(value = "save")
	public String save(MailCompose mailCompose, Model model, HttpServletRequest request, HttpServletResponse response) {
		if (!beanValidator(model, mailCompose.getMail())){
			return detail(mailCompose, model);
		}
		mailService.saveOnlyMain(mailCompose.getMail());
		Date date = new Date(System.currentTimeMillis());
		mailCompose.setSender(UserUtils.getUser());
		mailCompose.setSendtime(date);
		for(User receiver : mailCompose.getReceiverList()){
			mailCompose.setReceiver(receiver);
			mailCompose.setId(null);//标记为新纪录，每次往发件箱插入一条记录
			mailComposeService.save(mailCompose);//0 显示在草稿箱，1 显示在已发送需同时保存到收信人的收件箱。
		
		
			if("1".equals(mailCompose.getStatus()))//已发送，同时保存到收信人的收件箱
			{
				MailBox mailBox = new MailBox();
				mailBox.setReadstatus("0");
				mailBox.setReceiver(receiver);
				mailBox.setSender(UserUtils.getUser());
				mailBox.setMail(mailCompose.getMail());
				mailBox.setSendtime(date);
				mailBoxService.save(mailBox);
			}
		}
		
		request.setAttribute("mailCompose", mailCompose);
		return "modules/iim/mail_compose_success";
	}
	
	@RequestMapping(value = "delete")
	public String delete(MailCompose mailCompose, RedirectAttributes redirectAttributes) {
		mailComposeService.delete(mailCompose);
		addMessage(redirectAttributes, "删除站内信成功");
		return "redirect:"+Global.getAdminPath()+"/iim/mailCompose/?repage&orderBy=sendtime desc&status="+mailCompose.getStatus();
	}
	
	/**
	 * 批量删除已发送
	 */
	@RequestMapping(value = "deleteAllCompose")
	public String deleteAllCompose(String ids, Model model, RedirectAttributes redirectAttributes) {
        String[] idArray = ids.split(",");
		for(String id : idArray){
			mailComposeService.delete(mailComposeService.get(id));
		}
		
		
		
		
		addMessage(redirectAttributes, "删除邮件成功");
		return "redirect:"+Global.getAdminPath()+"/iim/mailCompose/?repage&status=1&orderBy=sendtime desc";
	}
	
	/**
	 * 批量删除草稿箱
	 */
	@RequestMapping(value = "deleteAllDraft")
	public String deleteAllDraft(String ids, RedirectAttributes redirectAttributes) {
        String[] idArray = ids.split(",");
		for(String id : idArray){
			mailComposeService.delete(mailComposeService.get(id));
		}
		addMessage(redirectAttributes, "删除邮件成功");
		return "redirect:"+Global.getAdminPath()+"/iim/mailCompose/?repage&status=0&orderBy=sendtime desc";
	}

}