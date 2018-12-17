/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/jeeSpringCloud">JeeSpringCloud</a> All rights reserved..
 */
package com.jeespring.modules.test.entity.onetomany;

import com.jeespring.modules.sys.entity.Area;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

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
public class TestDataChild3 extends AbstractBaseEntity<TestDataChild3> {
	
	private static final long serialVersionUID = 1L;
	private com.jeespring.modules.sys.entity.Area start;		// 出发地
	private com.jeespring.modules.sys.entity.Area end;		// 目的地
	private Double price;		// 代理价格
	private TestDataMain  testDataMain;		// 外键 父类testDataMain.id
	
	public TestDataChild3() {
		super();
	}

	public TestDataChild3(String id){
		super(id);
	}

	public TestDataChild3(TestDataMain testDataMain){
		this.testDataMain = testDataMain;
	}

	@NotNull(message="出发地不能为空")
				@ExcelField(title="出发地", fieldType=Area.class, value="start.name", align=2, sort=1)
	public Area getStart() {
		return start;
	}

	public void setStart(Area start) {
		this.start = start;
	}
	@NotNull(message="目的地不能为空")
				@ExcelField(title="目的地", fieldType=Area.class, value="end.name", align=2, sort=2)
	public Area getEnd() {
		return end;
	}

	public void setEnd(Area end) {
		this.end = end;
	}
				@ExcelField(title="代理价格", align=2, sort=3)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}


	@Length(min=1, max=64, message="外键长度必须介于 1 和 64 之间")
	@JsonIgnore
	@JSONField(serialize=false)
	public TestDataMain getTestDataMain() {
		return testDataMain;
	}

	public void setTestDataMain(TestDataMain testDataMain) {
		this.testDataMain = testDataMain;
	}
	
}