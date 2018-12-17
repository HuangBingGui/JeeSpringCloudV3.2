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
import com.jeespring.modules.iim.dao.MailComposeDao;
import com.jeespring.modules.iim.entity.MailCompose;
import com.jeespring.modules.iim.entity.MailPage;

/**
 * 发件箱Service
 * @author 黄炳桂 516821420@qq.com
 * @version 2015-11-13
 */
@Service
@Transactional(readOnly = true)
public class MailComposeService extends AbstractBaseService<MailComposeDao, MailCompose> {
	@Autowired
	private MailComposeDao mailComposeDao;
	@Override
    public MailCompose get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<MailCompose> findList(MailCompose mailCompose) {
		return super.findList(mailCompose);
	}
	
	public Page<MailCompose> findPage(MailPage<MailCompose> page, MailCompose mailCompose) {
		return super.findPage(page, mailCompose);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(MailCompose mailCompose) {
		super.save(mailCompose);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(MailCompose mailCompose) {
		super.delete(mailCompose);
	}

	public int getCount(MailCompose mailCompose) {
		return mailComposeDao.getCount(mailCompose);
	}
	
}