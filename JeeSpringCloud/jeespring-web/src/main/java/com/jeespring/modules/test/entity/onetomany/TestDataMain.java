/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/jeeSpringCloud">JeeSpringCloud</a> All rights reserved..
 */
package com.jeespring.modules.test.entity.onetomany;

import com.jeespring.modules.sys.entity.User;
import javax.validation.constraints.NotNull;
import com.jeespring.modules.sys.entity.Office;
import com.jeespring.modules.sys.entity.Area;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.List;
import com.google.common.collect.Lists;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jeespring.common.persistence.AbstractBaseEntity;
import com.jeespring.common.utils.excel.annotation.ExcelField;
import com.jeespring.modules.sys.utils.DictUtils;

/**
 * 订票Entity
 * @author JeeSpring
 * @version 2018-10-12
 */
public class TestDataMain extends AbstractBaseEntity<TestDataMain> {
	
	private static final long serialVersionUID = 1L;
	private com.jeespring.modules.sys.entity.User user;		// 归属用户
	private com.jeespring.modules.sys.entity.Office office;		// 归属部门
	private com.jeespring.modules.sys.entity.Area area;		// 归属区域
	private String name;		// 名称
	private String sex;		// 性别
	private String sexLabel;		// 性别Label
	private String sexPicture;		// 性别Picture
	private java.util.Date inDate;		// 加入日期
	private java.util.Date beginInDate;		// 开始 加入日期
	private java.util.Date endInDate;		// 结束 加入日期
	private List<TestDataChild> testDataChildList = Lists.newArrayList();		// 子表列表
	private List<TestDataChild2> testDataChild2List = Lists.newArrayList();		// 子表列表
	private List<TestDataChild3> testDataChild3List = Lists.newArrayList();		// 子表列表
	
	public TestDataMain() {
		super();
	}

	public TestDataMain(String id){
		super(id);
	}

	@NotNull(message="归属用户不能为空")
				@ExcelField(title="归属用户", fieldType=User.class, value="user.name", align=2, sort=1)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	@NotNull(message="归属部门不能为空")
				@ExcelField(title="归属部门", fieldType=Office.class, value="office.name", align=2, sort=2)
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	@NotNull(message="归属区域不能为空")
				@ExcelField(title="归属区域", fieldType=Area.class, value="area.name", align=2, sort=3)
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
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


	public String getSexLabel() {
		return DictUtils.getDictLabel(sex,"sex","");
	}
	public String getSexPicture() {
		return DictUtils.getDictPicture(sex,"sex","");
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