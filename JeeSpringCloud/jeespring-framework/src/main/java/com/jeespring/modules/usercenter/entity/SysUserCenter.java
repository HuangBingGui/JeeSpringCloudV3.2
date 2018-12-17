/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.jeespring.modules.usercenter.entity;

import com.jeespring.common.persistence.AbstractBaseEntity;
import com.jeespring.common.utils.excel.annotation.ExcelField;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 用户中心Entity
 * @author 黄炳桂
 * @version 2017-12-12
 */
public class SysUserCenter extends AbstractBaseEntity<SysUserCenter> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户编号
	private String userName;		// 用户名称
	private String userPhone;		// 用户手机号
	private String lat;		// 纬度
	private String lng;		// 经度
	private String city;		// 城市
	private String address;		// 地址
	private Date beginCreateDate;		// 开始 创建时间
	private Date endCreateDate;		// 结束 创建时间
	private Date beginUpdateDate;		// 开始 更新时间
	private Date endUpdateDate;		// 结束 更新时间
	private String ip;//ip地址

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public SysUserCenter() {
		super();
	}

	public SysUserCenter(String id){
		super(id);
	}

	@ExcelField(title="用户编号", align=2, sort=1)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Length(min=0, max=64, message="用户名称长度必须介于 0 和 64 之间")
	@ExcelField(title="用户名称", align=2, sort=2)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Length(min=0, max=64, message="用户手机号长度必须介于 0 和 64 之间")
	@ExcelField(title="用户手机号", align=2, sort=3)
	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	@Length(min=0, max=30, message="纬度长度必须介于 0 和 30 之间")
	@ExcelField(title="纬度", align=2, sort=4)
	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	@Length(min=0, max=30, message="经度长度必须介于 0 和 30 之间")
	@ExcelField(title="经度", align=2, sort=5)
	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}