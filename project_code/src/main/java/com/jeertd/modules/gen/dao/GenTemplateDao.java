/**
 * Copyright &copy; 2012-2016 <a href="https://git.oschina.net/guanshijiehnan/JeeRTD">JeeRTD</a> All rights reserved.
 */
package com.jeertd.modules.gen.dao;

import com.jeertd.modules.gen.entity.GenTemplate;
import com.jeertd.common.persistence.CrudDao;
import com.jeertd.common.persistence.annotation.MyBatisDao;

/**
 * 代码模板DAO接口
 * @author ThinkGem
 * @version 2013-10-15
 */
@MyBatisDao
public interface GenTemplateDao extends CrudDao<GenTemplate> {
	
}
