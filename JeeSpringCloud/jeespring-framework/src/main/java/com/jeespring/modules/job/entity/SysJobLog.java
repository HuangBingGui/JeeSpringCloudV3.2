/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.jeespring.modules.job.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jeespring.common.persistence.AbstractBaseEntity;
import com.jeespring.common.utils.excel.annotation.ExcelField;
import com.jeespring.modules.sys.utils.DictUtils;

/**
 * 定时任务调度日志表Entity
 * @author JeeSpring
 * @version 2018-08-16
 */
public class SysJobLog extends AbstractBaseEntity<SysJobLog> {
	
	private static final long serialVersionUID = 1L;
	private String jobName;		// 任务名称
	private String jobGroup;		// 任务组名
	private String methodName;		// 任务方法
	private String methodParams;		// 方法参数
	private String jobMessage;		// 日志信息
	private String status;		// 执行状态（0正常 1失败）
	private String statusLabel;		// 执行状态（0正常 1失败）Label
	private String statusPicture;		// 执行状态（0正常 1失败）Picture
	private String exceptionInfo;		// 异常信息
	private java.util.Date beginCreateDate;		// 开始 创建时间
	private java.util.Date endCreateDate;		// 结束 创建时间
	
	public SysJobLog() {
		super();
	}

	public SysJobLog(String id){
		super(id);
	}

	@Length(min=1, max=64, message="任务名称长度必须介于 1 和 64 之间")
				@ExcelField(title="任务名称", align=2, sort=1)
	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}


	@Length(min=1, max=64, message="任务组名长度必须介于 1 和 64 之间")
				@ExcelField(title="任务组名", align=2, sort=2)
	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}


	@Length(min=0, max=500, message="任务方法长度必须介于 0 和 500 之间")
				@ExcelField(title="任务方法", align=2, sort=3)
	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}


	@Length(min=0, max=200, message="方法参数长度必须介于 0 和 200 之间")
				@ExcelField(title="方法参数", align=2, sort=4)
	public String getMethodParams() {
		return methodParams;
	}

	public void setMethodParams(String methodParams) {
		this.methodParams = methodParams;
	}


	@Length(min=0, max=500, message="日志信息长度必须介于 0 和 500 之间")
				@ExcelField(title="日志信息", align=2, sort=5)
	public String getJobMessage() {
		return jobMessage;
	}

	public void setJobMessage(String jobMessage) {
		this.jobMessage = jobMessage;
	}


	@Length(min=0, max=1, message="执行状态（0正常 1失败）长度必须介于 0 和 1 之间")
				@ExcelField(title="执行状态（0正常 1失败）", dictType="job_status", align=2, sort=6)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public String getStatusLabel() {
		return DictUtils.getDictLabel(status,"job_status","");
	}
	public String getStatusPicture() {
		return DictUtils.getDictPicture(status,"job_status","");
	}
				@ExcelField(title="异常信息", align=2, sort=7)
	public String getExceptionInfo() {
		return exceptionInfo;
	}

	public void setExceptionInfo(String exceptionInfo) {
		this.exceptionInfo = exceptionInfo;
	}


	public Date getBeginCreateDate() {
		return beginCreateDate;
	}

	public void setBeginCreateDate(Date beginCreateDate) {
		this.beginCreateDate = beginCreateDate;
	}
	
	public Date getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(Date endCreateDate) {
		this.endCreateDate = endCreateDate;
	}
		
}