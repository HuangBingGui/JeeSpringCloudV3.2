/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeertd.org/">jeertd</a> All rights reserved.
 */
package com.jeertd.modules.iim.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeertd.common.persistence.Page;
import com.jeertd.common.service.CrudService;
import com.jeertd.common.utils.StringUtils;
import com.jeertd.modules.iim.dao.MailBoxDao;
import com.jeertd.modules.iim.dao.MailComposeDao;
import com.jeertd.modules.iim.dao.MailDao;
import com.jeertd.modules.iim.entity.Mail;
import com.jeertd.modules.iim.entity.MailBox;
import com.jeertd.modules.iim.entity.MailCompose;

/**
 * 发件箱Service
 * @author jeertd
 * @version 2015-11-15
 */
@Service
@Transactional(readOnly = true)
public class MailService extends CrudService<MailDao, Mail> {

	@Autowired
	private MailBoxDao mailBoxDao;
	@Autowired
	private MailComposeDao mailComposeDao;
	
	public Mail get(String id) {
		Mail mail = super.get(id);
		mail.setMailBoxList(mailBoxDao.findList(new MailBox(mail)));
		mail.setMailComposeList(mailComposeDao.findList(new MailCompose(mail)));
		return mail;
	}
	
	public List<Mail> findList(Mail mail) {
		return super.findList(mail);
	}
	
	public Page<Mail> findPage(Page<Mail> page, Mail mail) {
		return super.findPage(page, mail);
	}
	
	@Transactional(readOnly = false)
	public void save(Mail mail) {
		super.save(mail);
		for (MailBox mailBox : mail.getMailBoxList()){
			if (mailBox.getId() == null){
				continue;
			}
			if (MailBox.DEL_FLAG_NORMAL.equals(mailBox.getDelFlag())){
				if (StringUtils.isBlank(mailBox.getId())){
					mailBox.setMail(mail);
					mailBox.preInsert();
					mailBoxDao.insert(mailBox);
				}else{
					mailBox.preUpdate();
					mailBoxDao.update(mailBox);
				}
			}else{
				mailBoxDao.delete(mailBox);
			}
		}
		for (MailCompose mailCompose : mail.getMailComposeList()){
			if (mailCompose.getId() == null){
				continue;
			}
			if (MailCompose.DEL_FLAG_NORMAL.equals(mailCompose.getDelFlag())){
				if (StringUtils.isBlank(mailCompose.getId())){
					mailCompose.setMail(mail);
					mailCompose.preInsert();
					mailComposeDao.insert(mailCompose);
				}else{
					mailCompose.preUpdate();
					mailComposeDao.update(mailCompose);
				}
			}else{
				mailComposeDao.delete(mailCompose);
			}
		}
	}
	@Transactional(readOnly = false)
	public void saveOnlyMain(Mail mail) {
		super.save(mail);
		for (MailBox mailBox : mail.getMailBoxList()){
			if (mailBox.getId() == null){
				continue;
			}
			if (MailBox.DEL_FLAG_NORMAL.equals(mailBox.getDelFlag())){
				if (StringUtils.isBlank(mailBox.getId())){
					mailBox.setMail(mail);
					mailBox.preInsert();
					mailBoxDao.insert(mailBox);
				}else{
					mailBox.preUpdate();
					mailBoxDao.update(mailBox);
				}
			}else{
				mailBoxDao.delete(mailBox);
			}
		}
		for (MailCompose mailCompose : mail.getMailComposeList()){
			if (mailCompose.getId() == null){
				continue;
			}
			if (MailCompose.DEL_FLAG_NORMAL.equals(mailCompose.getDelFlag())){
				if (StringUtils.isBlank(mailCompose.getId())){
					mailCompose.setMail(mail);
					mailCompose.preInsert();
					mailComposeDao.insert(mailCompose);
				}else{
					mailCompose.preUpdate();
					mailComposeDao.update(mailCompose);
				}
			}else{
				mailComposeDao.delete(mailCompose);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(Mail mail) {
		super.delete(mail);
		mailBoxDao.delete(new MailBox(mail));
		mailComposeDao.delete(new MailCompose(mail));
	}
	
}