/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.jeespring.modules.sys.entity;

import com.jeespring.common.utils.AddressUtils;
import com.jeespring.modules.monitor.entity.OnlineSession;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jeespring.common.persistence.AbstractBaseEntity;
import com.jeespring.common.utils.excel.annotation.ExcelField;
import com.jeespring.modules.sys.utils.DictUtils;

/**
 * 在线用户记录Entity
 * @author JeeSpring
 * @version 2018-08-16
 */
public class SysUserOnline extends AbstractBaseEntity<SysUserOnline> {

	private static final long serialVersionUID = 1L;
	private String loginName;		// 登录账号
	private String deptName;		// 部门名称
	private String ipaddr;		// 登录IP地址
	private String loginLocation;		// 登录地点
	private String browser;		// 浏览器类型
	private String os;		// 操作系统
	private String status;		// 在线状态on_line在线off_line离线
	private String statusLabel;		// 在线状态on_line在线off_line离线Label
	private String statusPicture;		// 在线状态on_line在线off_line离线Picture
	private java.util.Date startTimestsamp;		// session创建时间
	private java.util.Date lastAccessTime;		// session最后访问时间
	private Integer expireTime;		// 超时时间，单位为分钟
	private java.util.Date beginStartTimestsamp;		// 开始 session创建时间
	private java.util.Date endStartTimestsamp;		// 结束 session创建时间
	private java.util.Date beginLastAccessTime;		// 开始 session最后访问时间
	private java.util.Date endLastAccessTime;		// 结束 session最后访问时间
	private java.util.Date beginCreateDate;		// 开始 创建时间
	private java.util.Date endCreateDate;		// 结束 创建时间
	private OnlineSession session; /** 备份的当前用户会话 */

	public SysUserOnline() {
		super();
	}

	public SysUserOnline(String id){
		super(id);
	}

	@Length(min=0, max=50, message="登录账号长度必须介于 0 和 50 之间")
				@ExcelField(title="登录账号", align=2, sort=1)
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}


	@Length(min=0, max=50, message="部门名称长度必须介于 0 和 50 之间")
				@ExcelField(title="部门名称", align=2, sort=2)
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}


	@Length(min=0, max=50, message="登录IP地址长度必须介于 0 和 50 之间")
				@ExcelField(title="登录IP地址", align=2, sort=3)
	public String getIpaddr() {
		return ipaddr;
	}

	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}


	@Length(min=0, max=255, message="登录地点长度必须介于 0 和 255 之间")
				@ExcelField(title="登录地点", align=2, sort=4)
	public String getLoginLocation() {
		return loginLocation;
	}

	public void setLoginLocation(String loginLocation) {
		this.loginLocation = loginLocation;
	}


	@Length(min=0, max=50, message="浏览器类型长度必须介于 0 和 50 之间")
				@ExcelField(title="浏览器类型", align=2, sort=5)
	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}


	@Length(min=0, max=50, message="操作系统长度必须介于 0 和 50 之间")
				@ExcelField(title="操作系统", align=2, sort=6)
	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}


	@Length(min=0, max=10, message="在线状态on_line在线off_line离线长度必须介于 0 和 10 之间")
				@ExcelField(title="在线状态on_line在线off_line离线", dictType="on_line_status", align=2, sort=7)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public String getStatusLabel() {
		return DictUtils.getDictLabel(status,"on_line_status","");
	}
	public String getStatusPicture() {
		return DictUtils.getDictPicture(status,"on_line_status","");
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
				@ExcelField(title="session创建时间", align=2, sort=8)
	public Date getStartTimestsamp() {
		return startTimestsamp;
	}

	public void setStartTimestsamp(Date startTimestsamp) {
		this.startTimestsamp = startTimestsamp;
	}


	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
				@ExcelField(title="session最后访问时间", align=2, sort=9)
	public Date getLastAccessTime() {
		return lastAccessTime;
	}

	public void setLastAccessTime(Date lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}


				@ExcelField(title="超时时间，单位为分钟", align=2, sort=10)
	public Integer getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Integer expireTime) {
		this.expireTime = expireTime;
	}


	public Date getBeginStartTimestsamp() {
		return beginStartTimestsamp;
	}

	public void setBeginStartTimestsamp(Date beginStartTimestsamp) {
		this.beginStartTimestsamp = beginStartTimestsamp;
	}

	public Date getEndStartTimestsamp() {
		return endStartTimestsamp;
	}

	public void setEndStartTimestsamp(Date endStartTimestsamp) {
		this.endStartTimestsamp = endStartTimestsamp;
	}

	public Date getBeginLastAccessTime() {
		return beginLastAccessTime;
	}

	public void setBeginLastAccessTime(Date beginLastAccessTime) {
		this.beginLastAccessTime = beginLastAccessTime;
	}

	public Date getEndLastAccessTime() {
		return endLastAccessTime;
	}

	public void setEndLastAccessTime(Date endLastAccessTime) {
		this.endLastAccessTime = endLastAccessTime;
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


	public OnlineSession getSession() {
		return session;
	}

	public void setSession(OnlineSession session) {
		this.session = session;
	}

	/**
	 * 设置session对象
	 */
	public static final SysUserOnline fromOnlineSession(OnlineSession session)
	{
		SysUserOnline online = new SysUserOnline();
		online.setId(String.valueOf(session.getId()));
		online.setDeptName(session.getDeptName());
		online.setLoginName(session.getLoginName());
		online.setStartTimestsamp(session.getStartTimestamp());
		online.setLastAccessTime(session.getLastAccessTime());
		online.setExpireTime((int)session.getTimeout());
		online.setIpaddr(session.getHost());
		online.setLoginLocation(AddressUtils.getRealAddressByIP(session.getHost()));
		online.setBrowser(session.getBrowser());
		online.setOs(session.getOs());
		online.setStatus(session.getStatus().toString());
		online.setSession(session);
		return online;
	}
}