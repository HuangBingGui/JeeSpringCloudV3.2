/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.jeespring.modules.sys.dao;

import org.apache.ibatis.annotations.Mapper;

import com.jeespring.common.persistence.InterfaceBaseDao;
import com.jeespring.modules.sys.entity.Role;

/**
 * 角色DAO接口
 * @author 黄炳桂 516821420@qq.com
 * @version 2013-12-05
 */
@Mapper
public interface RoleDao extends InterfaceBaseDao<Role> {

	Role getByName(Role role);
	
	Role getByEnname(Role role);

	/**
	 * 维护角色与菜单权限关系
	 * @param role
	 * @return
	 */
    int deleteRoleMenu(Role role);

	int insertRoleMenu(Role role);
	
	/**
	 * 维护角色与公司部门关系
	 * @param role
	 * @return
	 */
    int deleteRoleOffice(Role role);

	int insertRoleOffice(Role role);

}
