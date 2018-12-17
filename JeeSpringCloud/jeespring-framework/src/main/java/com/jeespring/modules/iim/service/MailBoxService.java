/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.jeespring.modules.iim.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeespring.common.persistence.Page;
import com.jeespring.common.service.AbstractBaseService;
import com.jeespring.modules.iim.dao.MailBoxDao;
import com.jeespring.modules.iim.entity.MailBox;
import com.jeespring.modules.iim.entity.MailPage;

/**
 * 收件箱Service
 * @author 黄炳桂 516821420@qq.com
 * @version 2015-11-13
 */
@Service
@Transactional(readOnly = true)
public class MailBoxService extends AbstractBaseService<MailBoxDao, MailBox> {

	@Autowired
	private MailBoxDao mailBoxDao;
	@Override
    public MailBox get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<MailBox> findList(MailBox mailBox) {
		return super.findList(mailBox);
	}
	
	public Page<MailBox> findPage(MailPage<MailBox> page, MailBox mailBox) {
		return super.findPage(page, mailBox);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(MailBox mailBox) {
		super.save(mailBox);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(MailBox mailBox) {
		super.delete(mailBox);
	}
	
	public int getCount(MailBox mailBox) {
		return mailBoxDao.getCount(mailBox);
	}
	
}