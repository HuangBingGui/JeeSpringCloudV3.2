/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeespring.org/">JeeSpring</a> All rights reserved.
 */
package com.jeespring.modules.monitor.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeespring.common.persistence.Page;
import com.jeespring.common.service.AbstractBaseService;
import com.jeespring.modules.monitor.entity.Monitor;
import com.jeespring.modules.monitor.dao.MonitorDao;

/**
 * 系统监控Service
 * @author liugf
 * @version 2016-02-07
 */
@Service
@Transactional(readOnly = true)
public class MonitorService extends AbstractBaseService<MonitorDao, Monitor> {

	@Override
    public Monitor get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<Monitor> findList(Monitor monitor) {
		return super.findList(monitor);
	}
	
	@Override
    public Page<Monitor> findPage(Page<Monitor> page, Monitor monitor) {
		return super.findPage(page, monitor);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(Monitor monitor) {
		super.save(monitor);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(Monitor monitor) {
		super.delete(monitor);
	}
	
}