/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jeertd.rtd.service.oanotity;

import java.util.List;
import com.jeertd.common.persistence.Page;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeertd.modules.oa.entity.OaNotify;
/**
 * teacher kwanService
 * @author teacher kwan
 * @version 2017-06-02
 */
public interface OaNotifyServiceDubbo {

	public OaNotify get(OaNotify oaNotify,HttpServletRequest request,HttpServletResponse response);
	
	public List<OaNotify> list(OaNotify oaNotify,HttpServletRequest request,HttpServletResponse response);
	
	public Page<OaNotify> findPage(OaNotify oaNotify,HttpServletRequest request,HttpServletResponse response);
	
	public void save(OaNotify oaNotify,HttpServletRequest request,HttpServletResponse response);
	
	public void delete(OaNotify oaNotify,HttpServletRequest request,HttpServletResponse response);
	
}