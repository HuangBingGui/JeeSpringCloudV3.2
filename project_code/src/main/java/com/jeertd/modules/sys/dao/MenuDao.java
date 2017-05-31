/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeertd.org/">jeertd</a> All rights reserved.
 */
package com.jeertd.modules.sys.dao;

import java.util.List;

import com.jeertd.common.persistence.CrudDao;
import com.jeertd.common.persistence.annotation.MyBatisDao;
import com.jeertd.modules.sys.entity.Menu;

/**
 * 菜单DAO接口
 * @author jeertd
 * @version 2014-05-16
 */
@MyBatisDao
public interface MenuDao extends CrudDao<Menu> {

	public List<Menu> findByParentIdsLike(Menu menu);

	public List<Menu> findByUserId(Menu menu);
	
	public int updateParentIds(Menu menu);
	
	public int updateSort(Menu menu);
	
}
