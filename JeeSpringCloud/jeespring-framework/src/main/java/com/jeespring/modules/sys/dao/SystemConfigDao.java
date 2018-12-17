/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeespring.org/">JeeSpring</a> All rights reserved.
 */
package com.jeespring.modules.sys.dao;

import com.jeespring.common.persistence.InterfaceBaseDao;
import com.jeespring.modules.sys.entity.SystemConfig;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统配置DAO接口
 * @author liugf
 * @version 2016-02-07
 */
@Mapper
public interface SystemConfigDao extends InterfaceBaseDao<SystemConfig> {
	
}