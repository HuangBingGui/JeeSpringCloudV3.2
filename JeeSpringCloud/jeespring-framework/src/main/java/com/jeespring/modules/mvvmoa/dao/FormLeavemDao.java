/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.jeespring.modules.mvvmoa.dao;

import com.jeespring.common.persistence.InterfaceBaseDao;
import org.apache.ibatis.annotations.Mapper;
import com.jeespring.modules.mvvmoa.entity.FormLeavem;

/**
 * 员工请假DAO接口
 * @author liugf
 * @version 2017-07-17
 */
@Mapper
public interface FormLeavemDao extends InterfaceBaseDao<FormLeavem> {
	
}