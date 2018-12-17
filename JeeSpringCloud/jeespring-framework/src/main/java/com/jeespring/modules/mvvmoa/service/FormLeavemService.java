/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.jeespring.modules.mvvmoa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeespring.common.persistence.Page;
import com.jeespring.common.service.AbstractBaseService;
import com.jeespring.modules.mvvmoa.entity.FormLeavem;
import com.jeespring.modules.mvvmoa.dao.FormLeavemDao;

/**
 * 员工请假Service
 * @author liugf
 * @version 2017-07-17
 */
@Service
@Transactional(readOnly = true)
public class FormLeavemService extends AbstractBaseService<FormLeavemDao, FormLeavem> {

	@Override
    public FormLeavem get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<FormLeavem> findList(FormLeavem formLeavem) {
		return super.findList(formLeavem);
	}
	
	@Override
    public Page<FormLeavem> findPage(Page<FormLeavem> page, FormLeavem formLeavem) {
		return super.findPage(page, formLeavem);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(FormLeavem formLeavem) {
		super.save(formLeavem);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(FormLeavem formLeavem) {
		super.delete(formLeavem);
	}
	
}