/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.jeespring.modules.job.dao;

import com.jeespring.common.persistence.InterfaceBaseDao;
import org.apache.ibatis.annotations.Mapper;
import com.jeespring.modules.job.entity.SysJob;

/**
 * 定时任务调度DAO接口
 * @author JeeSpring
 * @version 2018-08-16
 */
@Mapper
public interface SysJobDao extends InterfaceBaseDao<SysJob> {
	
}