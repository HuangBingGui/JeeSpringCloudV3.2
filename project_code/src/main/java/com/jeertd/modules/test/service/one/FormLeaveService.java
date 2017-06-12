/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeertd.org/">jeertd</a> All rights reserved.
 */
package com.jeertd.modules.test.service.one;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeertd.common.persistence.Page;
import com.jeertd.common.service.CrudService;
import com.jeertd.modules.test.dao.one.FormLeaveDao;
import com.jeertd.modules.test.entity.one.FormLeave;

/**
 * 员工请假Service
 * @author liugf
 * @version 2016-01-15
 */
@Service
@Transactional(readOnly = true)
public class FormLeaveService extends CrudService<FormLeaveDao, FormLeave> {

	public FormLeave get(String id) {
		return super.get(id);
	}
	
	public List<FormLeave> findList(FormLeave formLeave) {
		return super.findList(formLeave);
	}
	
	public Page<FormLeave> findPage(Page<FormLeave> page, FormLeave formLeave) {
		return super.findPage(page, formLeave);
	}
	
	@Transactional(readOnly = false)
	public void save(FormLeave formLeave) {
		super.save(formLeave);
	}
	
	@Transactional(readOnly = false)
	public void delete(FormLeave formLeave) {
		super.delete(formLeave);
	}
	
}