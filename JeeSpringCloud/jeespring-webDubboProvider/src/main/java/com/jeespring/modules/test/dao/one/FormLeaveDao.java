/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/jeeSpringCloud">JeeSpringCloud</a> All rights reserved..
 */
package com.jeespring.modules.test.dao.one;

import com.jeespring.common.persistence.InterfaceBaseDao;
import org.apache.ibatis.annotations.Mapper;
import com.jeespring.modules.test.entity.one.FormLeave;

/**
 * 请假DAO接口
 * @author JeeSpring
 * @version 2018-10-12
 */
@Mapper
public interface FormLeaveDao extends InterfaceBaseDao<FormLeave> {
	
}