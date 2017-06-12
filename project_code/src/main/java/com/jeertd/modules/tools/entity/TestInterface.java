/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeertd.org/">jeertd</a> All rights reserved.
 */
package com.jeertd.modules.tools.entity;

import org.hibernate.validator.constraints.Length;

import com.jeertd.common.persistence.DataEntity;
import com.jeertd.common.utils.excel.annotation.ExcelField;

/**
 * 接口Entity
 * @author lgf
 * @version 2016-01-07
 */
public class TestInterface extends DataEntity<TestInterface> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 接口名称
	private String type;		// 接口类型
	private String url;		// 请求URL
	private String body;		// 请求body
	private String successmsg;		// 成功时返回消息
	private String errormsg;		// 失败时返回消息
	private String comment;		// 备注
	
	public TestInterface() {
		super();
	}

	public TestInterface(String id){
		super(id);
	}

	@Length(min=0, max=1024, message="接口名称长度必须介于 0 和 1024 之间")
	@ExcelField(title="接口名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=16, message="接口类型长度必须介于 0 和 16 之间")
	@ExcelField(title="接口类型", dictType="type", align=2, sort=2)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=256, message="请求URL长度必须介于 0 和 256 之间")
	@ExcelField(title="请求URL", align=2, sort=3)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Length(min=0, max=2048, message="请求body长度必须介于 0 和 2048 之间")
	@ExcelField(title="请求body", align=2, sort=4)
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	@Length(min=0, max=512, message="成功时返回消息长度必须介于 0 和 512 之间")
	@ExcelField(title="成功时返回消息", align=2, sort=5)
	public String getSuccessmsg() {
		return successmsg;
	}

	public void setSuccessmsg(String successmsg) {
		this.successmsg = successmsg;
	}
	
	@Length(min=0, max=512, message="失败时返回消息长度必须介于 0 和 512 之间")
	@ExcelField(title="失败时返回消息", align=2, sort=6)
	public String getErrormsg() {
		return errormsg;
	}

	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}
	
	@Length(min=0, max=512, message="备注长度必须介于 0 和 512 之间")
	@ExcelField(title="备注", align=2, sort=7)
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
}