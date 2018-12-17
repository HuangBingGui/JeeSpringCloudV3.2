/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeespring.org/">JeeSpring</a> All rights reserved.
 */
package com.jeespring.modules.monitor.dao;

import com.jeespring.common.persistence.InterfaceBaseDao;
import com.jeespring.modules.monitor.entity.Monitor;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统监控DAO接口
 * @author JeeSpring
 * @version 2016-02-07
 */
@Mapper
public interface MonitorDao extends InterfaceBaseDao<Monitor> {
	
}