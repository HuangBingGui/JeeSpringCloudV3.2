/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.jeespring.modules.sys.dao;

import com.jeespring.common.persistence.InterfaceBaseDao;
import org.apache.ibatis.annotations.Mapper;
import com.jeespring.modules.sys.entity.SysConfig;

/**
 * 系统配置DAO接口
 * @author 黄炳桂 516821420@qq.com
 * @version 2017-11-17
 */
@Mapper
public interface SysConfigDao extends InterfaceBaseDao<SysConfig> {
	
}