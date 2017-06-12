/**
 * Copyright &copy; 2012-2016 <a href="https://git.oschina.net/guanshijiehnan/JeeRTD">JeeSite</a> All rights reserved.
 */
package com.jeertd.rtd.api.test;

import java.util.List;
import com.jeertd.common.persistence.Page;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.jeertd.modules.test.entity.Test;
/**
 * 单表生成Service
 * @author ThinkGem
 * @version 2017-05-04
 */
public interface TestDataServiceDubbo {

	public Test get(Test testData,HttpServletRequest request,HttpServletResponse response);
	
	public List<Test> list(Test testData,HttpServletRequest request,HttpServletResponse response);
	
	public Page<Test> findPage(Test testData,HttpServletRequest request,HttpServletResponse response);
	
	public void save(Test testData,HttpServletRequest request,HttpServletResponse response);
	
	public void delete(Test testData,HttpServletRequest request,HttpServletResponse response);
	
}