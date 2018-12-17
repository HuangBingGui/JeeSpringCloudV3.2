/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeespring.modules.iim.dao;

import com.jeespring.common.persistence.InterfaceBaseDao;
import com.jeespring.modules.iim.entity.MyCalendar;
import org.apache.ibatis.annotations.Mapper;


/**
 * 日历DAO接口
 * @author JeeSpring
 * @version 2016-04-19
 */
@Mapper
public interface MyCalendarDao extends InterfaceBaseDao<MyCalendar> {
	
}