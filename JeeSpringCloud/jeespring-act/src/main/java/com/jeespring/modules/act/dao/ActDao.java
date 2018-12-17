/**
 * Copyright &copy; 2012-2016 <a href="https://gitee.com/JeeHuangBingGui/jeeSpringCloud">JeeSpring</a> All rights reserved.
 */
package com.jeespring.modules.act.dao;

import com.jeespring.common.persistence.InterfaceBaseDao;
import com.jeespring.common.persistence.annotation.MyBatisDao;
import com.jeespring.modules.act.entity.Act;
import org.apache.ibatis.annotations.Mapper;

/**
 * 审批DAO接口
 * @author thinkgem
 * @version 2014-05-16
 */
@Mapper
public interface ActDao extends InterfaceBaseDao<Act> {

	public int updateProcInsIdByBusinessId(Act act);
	
}
