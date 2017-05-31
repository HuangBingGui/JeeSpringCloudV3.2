/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeertd.org/">jeertd</a> All rights reserved.
 */
package com.jeertd.modules.oa.dao;

import com.jeertd.common.persistence.CrudDao;
import com.jeertd.common.persistence.annotation.MyBatisDao;
import com.jeertd.modules.oa.entity.OaNotify;

/**
 * 通知通告DAO接口
 * @author jeertd
 * @version 2014-05-16
 */
@MyBatisDao
public interface OaNotifyDao extends CrudDao<OaNotify> {
	
	/**
	 * 获取通知数目
	 * @param oaNotify
	 * @return
	 */
	public Long findCount(OaNotify oaNotify);
	
}