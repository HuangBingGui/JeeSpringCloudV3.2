/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.jeespring.modules.server.dao;

import com.jeespring.common.persistence.InterfaceBaseDao;
import org.apache.ibatis.annotations.Mapper;
import com.jeespring.modules.server.entity.SysServer;

/**
 * 服务器监控DAO接口
 * @author JeeSpring
 * @version 2018-08-20
 */
@Mapper
public interface SysServerDao extends InterfaceBaseDao<SysServer> {
	
}