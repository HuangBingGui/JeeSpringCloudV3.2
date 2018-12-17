/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.jeespring.modules.usercenter.dao;

import com.jeespring.common.persistence.InterfaceBaseDao;
import org.apache.ibatis.annotations.Mapper;
import com.jeespring.modules.usercenter.entity.SysUserCenter;

/**
 * 用户中心DAO接口
 * @author 黄炳桂
 * @version 2017-12-12
 */
@Mapper
public interface SysUserCenterDao extends InterfaceBaseDao<SysUserCenter> {
	
}