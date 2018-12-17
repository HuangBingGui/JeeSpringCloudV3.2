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
import com.jeespring.modules.sys.utils.UserUtils;

/**
 * 收件箱Controller
 * * * * @author 黄炳桂 516821420@qq.com
 * @version 2015-11-13
 */
@Controller
@RequestMapping(value = "${adminPath}/iim/mailBox")
public class MailBoxController extends AbstractBaseController {

	@Autowired
	private MailComposeService mailComposeService;
	
	@Autowired
	private MailBoxService mailBoxService;
	
	@ModelAttribute
	public MailBox get(@RequestParam(required=false) String id) {
		MailBox entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = mailBoxService.get(id);
		}
		if (entity == null){
			entity = new MailBox();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(MailBox mailBox, HttpServletRequest request, HttpServletResponse response, Model model) {
		mailBox.setReceiver(UserUtils.getUser());
		Page<MailBox> page = mailBoxService.findPage(new MailPage<MailBox>(request, response), mailBox); 
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
	
		return "modules/iim/mailBoxList";
	}

	@RequestMapping(value = "detail")
	public String detail(MailBox mailBox, Model model) {
		if("0".equals(mailBox.getReadstatus())){//更改未读状态为已读状态
			mailBox.setReadstatus("1");//1表示已读
			mailBoxService.save(mailBox);
		}
		model.addAttribute("mailBox", mailBox);
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
		return "modules/iim/mailBoxDetail";
	}

	@RequestMapping(value = "save")
	public String save(MailBox mailBox, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, mailBox)){
			return detail(mailBox, model);
		}
	    Date date = new Date(System.currentTimeMillis());
		mailBox.setSender( UserUtils.getUser());
		mailBox.setSendtime(date);
		mailBoxService.save(mailBox);
		addMessage(redirectAttributes, "保存站内信成功");
		return "redirect:"+Global.getAdminPath()+"/iim/mailBox/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(MailBox mailBox, RedirectAttributes redirectAttributes) {
		mailBoxService.delete(mailBox);
		addMessage(redirectAttributes, "删除站内信成功");
		return "redirect:"+Global.getAdminPath()+"/iim/mailBox/?repage";
	}

	/**
	 * 批量删除
	 */
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
        String[] idArray = ids.split(",");
		for(String id : idArray){
			mailBoxService.delete(mailBoxService.get(id));
		}
		addMessage(redirectAttributes, "删除站内信成功");
		return "redirect:"+Global.getAdminPath()+"/iim/mailBox/?repage";
	}
}