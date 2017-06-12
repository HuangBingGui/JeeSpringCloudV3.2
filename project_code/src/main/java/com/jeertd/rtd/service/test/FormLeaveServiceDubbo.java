/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jeertd.rtd.service.test;

import java.util.List;
import com.jeertd.common.persistence.Page;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeertd.modules.test.entity.one.FormLeave;
/**
 * 员工请假Service
 * @author liugf
 * @version 2017-06-02
 */
public interface FormLeaveServiceDubbo {

	public FormLeave get(FormLeave formLeave,HttpServletRequest request,HttpServletResponse response);
	
	public List<FormLeave> list(FormLeave formLeave,HttpServletRequest request,HttpServletResponse response);
	
	public Page<FormLeave> findPage(FormLeave formLeave,HttpServletRequest request,HttpServletResponse response);
	
	public void save(FormLeave formLeave,HttpServletRequest request,HttpServletResponse response);
	
	public void delete(FormLeave formLeave,HttpServletRequest request,HttpServletResponse response);
	
}