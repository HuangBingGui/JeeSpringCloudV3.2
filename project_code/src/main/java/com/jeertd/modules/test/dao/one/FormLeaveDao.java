/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeertd.org/">jeertd</a> All rights reserved.
 */
package com.jeertd.modules.test.dao.one;

import com.jeertd.common.persistence.CrudDao;
import com.jeertd.common.persistence.annotation.MyBatisDao;
import com.jeertd.modules.test.entity.one.FormLeave;

/**
 * 员工请假DAO接口
 * @author liugf
 * @version 2016-01-15
 */
@MyBatisDao
public interface FormLeaveDao extends CrudDao<FormLeave> {
	
}