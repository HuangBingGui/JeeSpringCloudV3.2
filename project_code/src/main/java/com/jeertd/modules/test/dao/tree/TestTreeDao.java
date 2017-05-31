/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeertd.org/">jeertd</a> All rights reserved.
 */
package com.jeertd.modules.test.dao.tree;

import com.jeertd.common.persistence.TreeDao;
import com.jeertd.common.persistence.annotation.MyBatisDao;
import com.jeertd.modules.test.entity.tree.TestTree;

/**
 * 组织机构DAO接口
 * @author liugf
 * @version 2016-01-15
 */
@MyBatisDao
public interface TestTreeDao extends TreeDao<TestTree> {
	
}