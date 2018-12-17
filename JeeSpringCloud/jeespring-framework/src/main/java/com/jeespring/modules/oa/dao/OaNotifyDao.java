/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.jeespring.modules.oa.dao;

import com.jeespring.common.persistence.InterfaceBaseDao;
import org.apache.ibatis.annotations.Mapper;
import com.jeespring.modules.oa.entity.OaNotify;

/**
 * 通知通告DAO接口
 * @author 黄炳桂 516821420@qq.com
 * @version 2014-05-16
 */
@Mapper
public interface OaNotifyDao extends InterfaceBaseDao<OaNotify> {
	
	/**
	 * 获取通知数目
	 * @param oaNotify
	 * @return
	 */
    Long findCount(OaNotify oaNotify);
	
}