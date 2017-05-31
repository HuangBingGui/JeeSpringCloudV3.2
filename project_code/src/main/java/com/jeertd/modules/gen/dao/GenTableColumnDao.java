/**
 * Copyright &copy; 2012-2016 <a href="https://git.oschina.net/guanshijiehnan/JeeRTD">JeeRTD</a> All rights reserved.
 */
package com.jeertd.modules.gen.dao;

import com.jeertd.modules.gen.entity.GenTableColumn;
import com.jeertd.common.persistence.CrudDao;
import com.jeertd.common.persistence.annotation.MyBatisDao;

/**
 * 业务表字段DAO接口
 * @author ThinkGem
 * @version 2013-10-15
 */
@MyBatisDao
public interface GenTableColumnDao extends CrudDao<GenTableColumn> {
	
	public void deleteByGenTableId(String genTableId);
}
