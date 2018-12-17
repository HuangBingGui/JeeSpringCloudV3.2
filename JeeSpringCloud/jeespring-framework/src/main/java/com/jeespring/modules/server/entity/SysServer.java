/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.jeespring.modules.server.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;

import com.jeespring.common.persistence.AbstractBaseEntity;
import com.jeespring.common.utils.excel.annotation.ExcelField;
import com.jeespring.modules.sys.utils.DictUtils;

/**
 * 服务器监控Entity
 * @author JeeSpring
 * @version 2018-08-20
 */
public class SysServer extends AbstractBaseEntity<SysServer> {
	
	private static final long serialVersionUID = 1L;
	private String serverNumber;		// 服务器编号
	private String serverAddress;		// 服务器监控地址
	private String name;		// 名称
	private String label;		// 标签名
	private String picture;		// 图片
	private String type;		// 类型
	private String typeLabel;		// 类型Label
	private String typePicture;		// 类型Picture
	private Long sort;		// 排序（升序）
	private String description;		// 描述
	private String html;		// 备注信息
	private String status;		// 在线状态on_line在线off_line离线
	private String statusLabel;		// 在线状态on_line在线off_line离线Label
	private String statusPicture;		// 在线状态on_line在线off_line离线Picture
	private java.util.Date beginCreateDate;		// 开始 创建时间
	private java.util.Date endCreateDate;		// 结束 创建时间
	private java.util.Date beginUpdateDate;		// 开始 更新时间
	private java.util.Date endUpdateDate;		// 结束 更新时间
	
	public SysServer() {
		super();
	}

	public SysServer(String id){
		super(id);
	}

	@Length(min=0, max=255, message="服务器编号长度必须介于 0 和 255 之间")
				@ExcelField(title="服务器编号", align=2, sort=1)
	public String getServerNumber() {
		return serverNumber;
	}

	public void setServerNumber(String serverNumber) {
		this.serverNumber = serverNumber;
	}


	@Length(min=0, max=100, message="服务器监控地址长度必须介于 0 和 100 之间")
				@ExcelField(title="服务器监控地址", align=2, sort=2)
	public String getServerAddress() {
		return serverAddress;
	}

	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}


	@Length(min=0, max=255, message="名称长度必须介于 0 和 255 之间")
				@ExcelField(title="名称", align=2, sort=3)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Length(min=0, max=50, message="标签名长度必须介于 0 和 50 之间")
				@ExcelField(title="标签名", align=2, sort=4)
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}


	@Length(min=0, max=100, message="图片长度必须介于 0 和 100 之间")
				@ExcelField(title="图片", align=2, sort=5)
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}


	@Length(min=0, max=50, message="类型长度必须介于 0 和 50 之间")
				@ExcelField(title="类型", dictType="server_type", align=2, sort=6)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public String getTypeLabel() {
		return DictUtils.getDictLabel(type,"server_type","");
	}
	public String getTypePicture() {
		return DictUtils.getDictPicture(type,"server_type","");
	}
	@NotNull(message="排序（升序）不能为空")
				@ExcelField(title="排序（升序）", align=2, sort=7)
	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}


	@Length(min=0, max=100, message="描述长度必须介于 0 和 100 之间")
				@ExcelField(title="描述", align=2, sort=8)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


				@ExcelField(title="备注信息", align=2, sort=10)
	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}


	@Length(min=0, max=10, message="在线状态on_line在线off_line离线长度必须介于 0 和 10 之间")
				@ExcelField(title="在线状态on_line在线off_line离线", dictType="on_line_status", align=2, sort=11)
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