/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.jeespring.modules.iim.dao;

import com.jeespring.common.persistence.InterfaceBaseDao;
import org.apache.ibatis.annotations.Mapper;
import com.jeespring.modules.iim.entity.Mail;

/**
 * 发件箱DAO接口
 * * * * @author 黄炳桂 516821420@qq.com
 * @version 2015-11-15
 */
@Mapper
public interface MailDao extends InterfaceBaseDao<Mail> {
	int getCount(MailDao entity);
}