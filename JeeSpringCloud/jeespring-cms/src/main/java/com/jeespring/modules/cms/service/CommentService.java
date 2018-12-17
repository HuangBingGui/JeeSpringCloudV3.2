/**
 * Copyright &copy; 2012-2016 <a href="https://gitee.com/JeeHuangBingGui/jeeSpringCloud">JeeSpring</a>All rights reserved.
 */
package com.jeespring.modules.cms.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeespring.common.persistence.Page;
import com.jeespring.common.service.AbstractBaseService;
import com.jeespring.modules.cms.dao.CommentDao;
import com.jeespring.modules.cms.entity.Comment;

/**
 * 评论Service
 * @author JeeSpring
 * @version 2013-01-15
 */
@Service
@Transactional(readOnly = true)
public class CommentService extends AbstractBaseService<CommentDao, Comment> {

	@Override
    public Page<Comment> findPage(Page<Comment> page, Comment comment) {
//		DetachedCriteria dc = commentDao.createDetachedCriteria();
//		if (StringUtils.isNotBlank(comment.getContentId())){
//			dc.add(Restrictions.eq("contentId", comment.getContentId()));
//		}
//		if (StringUtils.isNotEmpty(comment.getTitle())){
//			dc.add(Restrictions.like("title", "%"+comment.getTitle()+"%"));
//		}
//		dc.add(Restrictions.eq(Comment.FIELD_DEL_FLAG, comment.getDelFlag()));
//		dc.addOrder(Order.desc("id"));
//		return commentDao.find(page, dc);
		comment.getSqlMap().put("dsf", dataScopeFilter(comment.getCurrentUser(), "o", "u"));
		
		return super.findPage(page, comment);
	}
	
	@Transactional(readOnly = false)
	public void delete(Comment entity, Boolean isRe) {
		super.delete(entity);
	}
}
