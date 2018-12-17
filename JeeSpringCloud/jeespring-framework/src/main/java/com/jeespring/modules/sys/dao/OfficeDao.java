/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.jeespring.modules.sys.dao;

import com.jeespring.common.persistence.TreeDao;
import com.jeespring.modules.sys.entity.Office;
import org.apache.ibatis.annotations.Mapper;

/**
 * 机构DAO接口
 * @author 黄炳桂 516821420@qq.com
 * @version 2014-05-16
 */
@Mapper
public interface OfficeDao extends TreeDao<Office> {
	
	Office getByCode(String code);
}
