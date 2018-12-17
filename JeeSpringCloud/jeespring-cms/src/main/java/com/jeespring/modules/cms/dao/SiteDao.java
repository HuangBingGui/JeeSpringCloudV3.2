/**
 * Copyright &copy; 2012-2016 <a href="https://gitee.com/JeeHuangBingGui/jeeSpringCloud">JeeSpring</a>All rights reserved.
 */
package com.jeespring.modules.cms.dao;

import com.jeespring.common.persistence.InterfaceBaseDao;
import com.jeespring.common.persistence.annotation.MyBatisDao;
import com.jeespring.modules.cms.entity.Site;
import org.apache.ibatis.annotations.Mapper;

/**
 * 站点DAO接口
 * @author JeeSpring
 * @version 2013-8-23
 */
@Mapper
public interface SiteDao extends InterfaceBaseDao<Site> {

}
