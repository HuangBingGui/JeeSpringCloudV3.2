/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.jeespring.modules.sys.dao;

import java.util.List;

import com.jeespring.common.persistence.InterfaceBaseDao;
import com.jeespring.modules.sys.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜单DAO接口
 * @author 黄炳桂 516821420@qq.com
 * @version 2014-05-16
 */
@Mapper
public interface MenuDao extends InterfaceBaseDao<Menu> {

	List<Menu> findByParentIdsLike(Menu menu);

	List<Menu> findByUserId(Menu menu);
	
	int updateParentIds(Menu menu);
	
	int updateSort(Menu menu);
	
}
