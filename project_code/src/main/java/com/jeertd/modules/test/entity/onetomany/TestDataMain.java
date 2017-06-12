/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeertd.org/">jeertd</a> All rights reserved.
 */
package com.jeertd.modules.test.entity.onetomany;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.List;

import com.google.common.collect.Lists;
import com.jeertd.common.persistence.DataEntity;
import com.jeertd.common.utils.excel.annotation.ExcelField;

/**
 * 票务代理Entity
 * @author liugf
 * @version 2016-01-15
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TestDataMain extends DataEntity<TestDataMain> {
	
	private static final long serialVersionUID = 1L;
	private String user;		// 归属用户
	private String office;		// 归属部门
	private String area;		// 归属区域
	private String name;		// 名称
	private String sex;		// 性别
	private Date inDate;		// 加入日期
	private Date beginInDate;		// 开始 加入日期
	private Date endInDate;		// 结束 加入日期
	private List<TestDataChild> testDataChildList = Lists.newArrayList();		// 子表列表
	private List<TestDataChild2> testDataChild2List = Lists.newArrayList();		// 子表列表
	private List<TestDataChild3> testDataChild3List = Lists.newArrayList();		// 子表列表
	
	public TestDataMain() {
		super();
	}

	public TestDataMain(String id){
		super(id);
	}

	@Length(min=1, max=64, message="归属用户长度必须介于 1 和 64 之间")
	@ExcelField(title="归属用户", fieldType=String.class, value="user.name", align=2, sort=1)
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	@Length(min=1, max=64, message="归属部门长度必须介于 1 和 64 之间")
	@ExcelField(title="归属部门", fieldType=String.class, value="office.name", align=2, sort=2)
	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}
	
	@Length(min=1, max=64, message="归属区域长度必须介于 1 和 64 之间")
	@ExcelField(title="归属区域", fieldType=String.class, value="area.name", align=2, sort=3)
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	@Length(min=1, max=100, message="名称长度必须介于 1 和 100 之间")
	@ExcelField(title="名称", align=2, sort=4)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=1, message="性别长度必须介于 1 和 1 之间")
	@ExcelField(title="性别", dictType="sex", align=2, sort=5)
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="加入日期不能为空")
	@ExcelField(title="加入日期", align=2, sort=6)
	public Date getInDate() {
		return inDate;
	}

	public void setInDate(Date inDate) {
		this.inDate = inDate;
	}
	
	public Date getBeginInDate() {
		return beginInDate;
	}

	public void setBeginInDate(Date beginInDate) {
		this.beginInDate = beginInDate;
	}
	
	public Date getEndInDate() {
		return endInDate;
	}

	public void setEndInDate(Date endInDate) {
		this.endInDate = endInDate;
	}
		
	public List<TestDataChild> getTestDataChildList() {
		return testDataChildList;
	}

	public void setTestDataChildList(List<TestDataChild> testDataChildList) {
		this.testDataChildList = testDataChildList;
	}
	public List<TestDataChild2> getTestDataChild2List() {
		return testDataChild2List;
	}

	public void setTestDataChild2List(List<TestDataChild2> testDataChild2List) {
		this.testDataChild2List = testDataChild2List;
	}
	public List<TestDataChild3> getTestDataChild3List() {
		return testDataChild3List;
	}

	public void setTestDataChild3List(List<TestDataChild3> testDataChild3List) {
		this.testDataChild3List = testDataChild3List;
	}
}