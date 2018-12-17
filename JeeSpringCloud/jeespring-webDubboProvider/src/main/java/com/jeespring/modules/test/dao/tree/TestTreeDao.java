/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/jeeSpringCloud">JeeSpringCloud</a> All rights reserved..
 */
package com.jeespring.modules.test.dao.tree;

import com.jeespring.common.persistence.TreeDao;
import com.jeespring.common.persistence.annotation.MyBatisDao;
import org.apache.ibatis.annotations.Mapper;
import com.jeespring.modules.test.entity.tree.TestTree;

/**
 * 树DAO接口
 * @author JeeSpring
 * @version 2018-10-11
 */
@Mapper
public interface TestTreeDao extends TreeDao<TestTree> {
	
}