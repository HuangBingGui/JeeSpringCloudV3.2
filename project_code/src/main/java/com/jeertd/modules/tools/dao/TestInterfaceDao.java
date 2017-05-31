/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeertd.org/">jeertd</a> All rights reserved.
 */
package com.jeertd.modules.tools.dao;

import com.jeertd.common.persistence.CrudDao;
import com.jeertd.common.persistence.annotation.MyBatisDao;
import com.jeertd.modules.tools.entity.TestInterface;

/**
 * 接口DAO接口
 * @author lgf
 * @version 2016-01-07
 */
@MyBatisDao
public interface TestInterfaceDao extends CrudDao<TestInterface> {
	
}