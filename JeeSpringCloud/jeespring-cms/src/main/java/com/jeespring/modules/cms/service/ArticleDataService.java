/**
 * Copyright &copy; 2012-2016 <a href="https://gitee.com/JeeHuangBingGui/jeeSpringCloud">JeeSpring</a>All rights reserved.
 */
package com.jeespring.modules.cms.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeespring.common.service.AbstractBaseService;
import com.jeespring.modules.cms.dao.ArticleDataDao;
import com.jeespring.modules.cms.entity.ArticleData;

/**
 * 站点Service
 * @author JeeSpring
 * @version 2013-01-15
 */
@Service
@Transactional(readOnly = true)
public class ArticleDataService extends AbstractBaseService<ArticleDataDao, ArticleData> {

}
