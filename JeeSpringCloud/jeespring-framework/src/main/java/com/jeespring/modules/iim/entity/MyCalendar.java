/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeespring.modules.iim.entity;

import org.hibernate.validator.constraints.Length;

import com.jeespring.common.persistence.AbstractBaseEntity;
import com.jeespring.common.utils.excel.annotation.ExcelField;
import com.jeespring.modules.sys.entity.User;

/**
 * 日历Entity
 * @author liugf
 * @version 2016-04-19
 */
public class MyCalendar extends AbstractBaseEntity<MyCalendar> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 事件标题
	private String start;		// 事件开始时间
	private String end;		// 事件结束时间
	private String adllDay;		// 是否为全天时间
	private String color;		// 时间的背景色
	private User user;		// 所属用户
	
	public MyCalendar() {
		super();
	}

	public MyCalendar(String id){
		super(id);
	}

	@Length(min=0, max=64, message="事件标题长度必须介于 0 和 64 之间")
	@ExcelField(title="事件标题", align=2, sort=1)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=64, message="事件开始时间长度必须介于 0 和 64 之间")
	@ExcelField(title="事件开始时间", align=2, sort=2)
	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}
	
	@Length(min=0, max=64, message="事件结束时间长度必须介于 0 和 64 之间")
	@ExcelField(title="事件结束时间", align=2, sort=3)
	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}
	
	@Length(min=0, max=64, message="是否为全天时间长度必须介于 0 和 64 之间")
	@ExcelField(title="是否为全天时间", align=2, sort=4)
	public String getAdllDay() {
		return adllDay;
	}

	public void setAdllDay(String adllDay) {
		this.adllDay = adllDay;
	}
	
	@Length(min=0, max=64, message="时间的背景色长度必须介于 0 和 64 之间")
	@ExcelField(title="时间的背景色", align=2, sort=5)
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}
	
}