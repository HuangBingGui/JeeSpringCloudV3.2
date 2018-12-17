/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.jeespring.modules.iim.entity;

import org.hibernate.validator.constraints.Length;

import com.jeespring.common.utils.excel.annotation.ExcelField;
import com.jeespring.common.persistence.AbstractBaseEntity;

/**
 * 聊天记录Entity
 * @author 黄炳桂 516821420@qq.com
 * @version 2015-12-29
 */
public class ChatHistory extends AbstractBaseEntity<ChatHistory> {
	
	private static final long serialVersionUID = 1L;
	private String userid1;		// userid1 发送者id
	private String userid2;		// userid2 接收者id
	private String msg;		// msg
	private String status;		// status
	
	public ChatHistory() {
		super();
	}

	public ChatHistory(String id){
		super(id);
	}

	@Length(min=0, max=64, message="userid1长度必须介于 0 和 64 之间")
	@ExcelField(title="userid1", align=2, sort=1)
	public String getUserid1() {
		return userid1;
	}

	public void setUserid1(String userid1) {
		this.userid1 = userid1;
	}
	
	@Length(min=0, max=64, message="userid2长度必须介于 0 和 64 之间")
	@ExcelField(title="userid2", align=2, sort=2)
	public String getUserid2() {
		return userid2;
	}

	public void setUserid2(String userid2) {
		this.userid2 = userid2;
	}
	
	@Length(min=0, max=1024, message="msg长度必须介于 0 和 1024 之间")
	@ExcelField(title="msg", align=2, sort=3)
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	@Length(min=0, max=45, message="status长度必须介于 0 和 45 之间")
	@ExcelField(title="status", align=2, sort=4)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}