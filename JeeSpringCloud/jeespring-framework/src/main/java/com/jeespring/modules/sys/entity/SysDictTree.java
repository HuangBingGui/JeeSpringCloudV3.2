/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.jeespring.modules.sys.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.Date;

import com.jeespring.common.persistence.TreeEntity;

/**
 * 数据字典Entity
 * @author JeeSpring
 * @version 2018-08-22
 */
public class SysDictTree extends TreeEntity<SysDictTree> {
	
	private static final long serialVersionUID = 1L;
	private String value;		// 数据值
	private String label;		// 标签名
	private String type;		// 类型
	private String description;		// 描述
	//private Long sort;		// 排序（升序）
	private SysDictTree parent;		// 父级编号
	private String parentIds;		// 所有父级编号
	private String picture;		// 图片
	private Date beginCreateDate;		// 开始 创建时间
	private Date endCreateDate;		// 结束 创建时间
	private Date beginUpdateDate;		// 开始 更新时间
	private Date endUpdateDate;		// 结束 更新时间
	
	public SysDictTree() {
		super();
	}

	public SysDictTree(String id){
		super(id);
	}

	@Length(min=1, max=100, message="数据值长度必须介于 1 和 100 之间")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Length(min=1, max=100, message="标签名长度必须介于 1 和 100 之间")
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	@Length(min=1, max=100, message="类型长度必须介于 1 和 100 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=1, max=100, message="描述长度必须介于 1 和 100 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	//@NotNull(message="排序（升序）不能为空")
	//public Long getSort() {
		//return sort;
	//}

	//public void setSort(Long sort) {
		//this.sort = sort;
	//}
	
	@Override
    @JsonBackReference
	public SysDictTree getParent() {
		return parent;
	}

	@Override
    public void setParent(SysDictTree parent) {
		this.parent = parent;
	}
	
	@Override
    @Length(min=0, max=2000, message="所有父级编号长度必须介于 0 和 2000 之间")
	public String getParentIds() {
		return parentIds;
	}

	@Override
    public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	
	@Length(min=0, max=255, message="图片长度必须介于 0 和 255 之间")
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
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
		
	@Override
    public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
}