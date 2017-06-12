/**
 * Copyright &copy; 2012-2016 <a href="https://git.oschina.net/guanshijiehnan/JeeRTD">JeeSite</a> All rights reserved.
 */
package com.jeertd.rtd.api.test;

import java.util.List;
import com.jeertd.common.persistence.Page;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 单表生成Service
 * @author ThinkGem
 * @version 2017-04-28
 */
public interface ServiceDubbo<T> {

	public T get(T Test,HttpServletRequest request,HttpServletResponse response);
	
	public List<T> list(T Test,HttpServletRequest request,HttpServletResponse response);
	
	public Page<T> findPage(T Test,HttpServletRequest request,HttpServletResponse response);
	
	public void save(T Test,HttpServletRequest request,HttpServletResponse response);
	
	public void delete(T Test,HttpServletRequest request,HttpServletResponse response);
	
}