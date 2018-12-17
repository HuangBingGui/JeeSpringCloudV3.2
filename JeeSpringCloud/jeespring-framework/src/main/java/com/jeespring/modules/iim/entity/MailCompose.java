/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.jeespring.modules.iim.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.jeespring.common.utils.Collections3;
import com.jeespring.common.utils.StringUtils;
import com.jeespring.common.persistence.AbstractBaseEntity;
import com.jeespring.modules.sys.entity.User;


/**
 * 发件箱Entity
 * @author 黄炳桂 516821420@qq.com
 * @version 2015-11-15
 */
public class MailCompose extends AbstractBaseEntity<MailCompose> {
	
	private static final long serialVersionUID = 1L;
	private String status;		// 状态 0 草稿 1 已发送
	private String readstatus;		// 状态 0 未读 1 已读
	private User sender;		// 发送者
	private User receiver;		// 接收者
	private List<User> receiverList;		// 接收者
	private Date sendtime;		// 发送时间
	private Mail mail;		// 邮件id 父类
	
	public MailCompose() {
		super();
	}

	public MailCompose(String id){
		super(id);
	}

	public MailCompose(Mail mail){
		this.mail = mail;
	}

	@Length(min=0, max=45, message="状态 0 草稿 1 已发送长度必须介于 0 和 45 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=45, message="状态 0 未读 1 已读长度必须介于 0 和 45 之间")
	public String getReadstatus() {
		return readstatus;
	}

	public void setReadstatus(String readstatus) {
		this.readstatus = readstatus;
	}
	
	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}
	
	
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSendtime() {
		return sendtime;
	}

	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}
	
	@Length(min=0, max=64, message="邮件id长度必须介于 0 和 64 之间")
	public Mail getMail() {
		return mail;
	}

	public void setMail(Mail mail) {
		this.mail = mail;
	}


	public void setReceiverList(List<User> receiverList) {
		this.receiverList = receiverList;
	}

	public List<User> getReceiverList() {
		return receiverList;
	}


	/**
	 * 获取收件人用户ID
	 * @return
	 */
	public String getReceiverIds() {
		return Collections3.extractToString(receiverList, "user.id", ",") ;
	}
	
	/**
	 * 设置收件人用户ID
	 * @return
	 */
	public void setReceiverIds(String receiverIds) {
		this.receiverList = Lists.newArrayList();
		for (String id : StringUtils.split(receiverIds, ",")){
			
			this.receiverList.add(new User(id));
		}
	}

	/**
	 * 获取收件人用户Name
	 * @return
	 */
	public String getReceiverNames() {
		return Collections3.extractToString(receiverList, "user.name", ",") ;
	}
	
	/**
	 * 设置发件人用户Name
	 * @return
	 */
	public void setReceiverNames(String receiverNames) {
		// 什么也不做
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	public User getReceiver() {
		return receiver;
	}
	
	
}