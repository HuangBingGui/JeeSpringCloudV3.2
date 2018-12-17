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
 * 定时任务调度Entity
 * @author JeeSpring
 * @version 2018-08-16
 */
public class SysJob extends AbstractBaseEntity<SysJob> {
	
	private static final long serialVersionUID = 1L;
	private String jobName;		// 任务名称
	private String jobGroup;		// 任务组名
	private String methodName;		// 任务方法
	private String methodParams;		// 方法参数
	private String cronExpression;		// cron执行表达式
	private String misfirePolicy;		// 计划执行错误策略（0默认 1继续 2等待 3放弃）
	private String misfirePolicyLabel;		// 计划执行错误策略（0默认 1继续 2等待 3放弃）Label
	private String misfirePolicyPicture;		// 计划执行错误策略（0默认 1继续 2等待 3放弃）Picture
	private String status;		// 状态（0正常 1暂停）
	private String statusLabel;		// 状态（0正常 1暂停）Label
	private String statusPicture;		// 状态（0正常 1暂停）Picture
	private String remark;		// 备注信息
	private java.util.Date beginCreateDate;		// 开始 创建时间
	private java.util.Date endCreateDate;		// 结束 创建时间
	private java.util.Date beginUpdateDate;		// 开始 更新时间
	private java.util.Date endUpdateDate;		// 结束 更新时间
	
	public SysJob() {
		super();
	}

	public SysJob(String id){
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


	@Length(min=0, max=255, message="cron执行表达式长度必须介于 0 和 255 之间")
				@ExcelField(title="cron执行表达式", align=2, sort=5)
	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}


	@Length(min=0, max=20, message="计划执行错误策略（0默认 1继续 2等待 3放弃）长度必须介于 0 和 20 之间")
				@ExcelField(title="计划执行错误策略（0默认 1继续 2等待 3放弃）", align=2, sort=6)
	public String getMisfirePolicy() {
		return misfirePolicy;
	}

	public void setMisfirePolicy(String misfirePolicy) {
		this.misfirePolicy = misfirePolicy;
	}


	@Length(min=0, max=1, message="状态（0正常 1暂停）长度必须介于 0 和 1 之间")
				@ExcelField(title="状态（0正常 1暂停）", dictType="job_status", align=2, sort=7)
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

	public String getMisfirePolicyLabel() {
		return DictUtils.getDictLabel(misfirePolicy,"misfire_policy","");
	}
	public String getMisfirePolicyPicture() {
		return DictUtils.getDictPicture(misfirePolicy,"misfire_policy","");
	}

	@Length(min=0, max=500, message="备注信息长度必须介于 0 和 500 之间")
				@ExcelField(title="备注信息", align=2, sort=12)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
		
	public Date getBeginUpdateDate() {
		return beginUpdateDate;
	}

	public void setBeginUpdateDate(Date beginUpdateDate) {
		this.beginUpdateDate = beginUpdateDate;
	}
	
	public Date getEndUpdateDate() {
		return endUpdateDate;
	}

	public void setEndUpdateDate(Date endUpdateDate) {
		this.endUpdateDate = endUpdateDate;
	}
		
}