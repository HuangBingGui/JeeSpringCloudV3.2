/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.jeespring.modules.sys.entity;

import com.jeespring.common.persistence.AbstractBaseEntity;
import com.jeespring.common.utils.excel.annotation.ExcelField;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 系统配置Entity
 * @author 黄炳桂 516821420@qq.com
 * @version 2017-11-17
 */
public class SysConfig extends AbstractBaseEntity<SysConfig> {
	
	private static final long serialVersionUID = 1L;
	private String type;		// 类型
	private String value;		// 数据值
	private String label;		// 标签名
	private String description;		// 描述
	private Long sort;		// 排序（升序）
	private String picture; //图片
	
	public SysConfig() {
		super();
	}

	public SysConfig(String id){
		super(id);
	}

	@Length(min=1, max=100, message="类型长度必须介于 1 和 100 之间")
	@ExcelField(title="类型", align=2, sort=1)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Length(min=1, max=100, message="数据值长度必须介于 1 和 100 之间")
	@ExcelField(title="数据值", align=2, sort=2)
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Length(min=1, max=100, message="标签名长度必须介于 1 和 100 之间")
	@ExcelField(title="标签名", align=2, sort=3)
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Length(min=1, max=4000, message="描述长度必须介于 1 和 2000 之间")
	@ExcelField(title="描述", align=2, sort=4)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@NotNull(message="排序（升序）不能为空")
	@ExcelField(title="排序（升序）", align=2, sort=5)
	public Long getSort() {
		return sort;
	}

	public String getPicture() {return picture;}

	public void setPicture(String picture) {this.picture = picture;}

	public void setSort(Long sort) {
		this.sort = sort;
	}

}