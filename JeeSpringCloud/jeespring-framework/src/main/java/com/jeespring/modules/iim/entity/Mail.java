/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.jeespring.modules.iim.entity;

import org.hibernate.validator.constraints.Length;

import java.util.List;

import com.google.common.collect.Lists;
import com.jeespring.common.persistence.AbstractBaseEntity;


/**
 * 发件箱Entity
 * @author 黄炳桂 516821420@qq.com
 * @version 2015-11-15
 */
public class Mail extends AbstractBaseEntity<Mail> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 标题
	private String overview;		// 内容概要
	private String content;		// 内容
	private List<MailBox> mailBoxList = Lists.newArrayList();		// 子表列表
	private List<MailCompose> mailComposeList = Lists.newArrayList();		// 子表列表
	
	public Mail() {
		super();
	}

	public Mail(String id){
		super(id);
	}

	@Length(min=0, max=128, message="标题长度必须介于 0 和 128 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=128, message="内容概要长度必须介于 0 和 128 之间")
	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}
	
	@Length(min=0, max=5096, message="内容长度必须介于 0 和 5096 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public List<MailBox> getMailBoxList() {
		return mailBoxList;
	}

	public void setMailBoxList(List<MailBox> mailBoxList) {
		this.mailBoxList = mailBoxList;
	}
	public List<MailCompose> getMailComposeList() {
		return mailComposeList;
	}

	public void setMailComposeList(List<MailCompose> mailComposeList) {
		this.mailComposeList = mailComposeList;
	}
}