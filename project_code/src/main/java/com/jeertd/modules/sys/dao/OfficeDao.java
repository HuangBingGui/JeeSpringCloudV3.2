/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeertd.org/">jeertd</a> All rights reserved.
 */
package com.jeertd.modules.sys.dao;

import com.jeertd.common.persistence.TreeDao;
import com.jeertd.common.persistence.annotation.MyBatisDao;
import com.jeertd.modules.sys.entity.Office;

/**
 * 机构DAO接口
 * @author jeertd
 * @version 2014-05-16
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {
	
	public Office getByCode(String code);
}
