/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.jeespring.modules.sys.dao;

import com.jeespring.common.persistence.TreeDao;
import com.jeespring.common.persistence.annotation.MyBatisDao;
import org.apache.ibatis.annotations.Mapper;
import com.jeespring.modules.sys.entity.SysDictTree;

/**
 * 数据字典DAO接口
 * @author JeeSpring
 * @version 2018-08-22
 */
@Mapper
public interface SysDictTreeDao extends TreeDao<SysDictTree> {
	
}