/**
 * Copyright &copy; 2012-2016 <a href="https://git.oschina.net/guanshijiehnan/JeeRTD">JeeSite</a> All rights reserved.
 */
package com.jeertdbase.rtd.service.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.jeertd.common.persistence.Page;
import com.jeertd.modules.test.service.TestService;
import com.jeertdbase.rtd.service.extension.JSONObjectX;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import com.jeertd.modules.test.entity.Test;;

/**
 * 单表生成Service
 * @author ThinkGem
 * @version 2017-05-04
 */
@Service(protocol = {"rest", "dubbo"}, group = "annotationConfig", validation = "true")
@Path("test")
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN,MediaType.APPLICATION_FORM_URLENCODED})
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8,ContentType.TEXT_PLAIN_UTF_8})
public class TestDataServiceDubboImpl implements TestDataServiceDubbo {

	@Autowired
	private TestService testDataService;

	@GET
	@POST
	@Path("get")
	public Test get(@BeanParam Test testData,@Context HttpServletRequest request,@Context HttpServletResponse response) {
		//return testDataService.get(testData);
		return testDataService.get(testData.getId());
	}

	@GET
	@POST
	@Path("list")
	public List<Test> list(@BeanParam  Test testData,@Context HttpServletRequest request,@Context HttpServletResponse response) {
		if (RpcContext.getContext().getResponse(HttpServletResponse.class) != null) { 
    		RpcContext.getContext().getResponse(HttpServletResponse.class).addHeader("Access-Control-Allow-Origin","*"); 
    		}
		return testDataService.findList(testData);
	}
	
	@GET
	@POST
	@Path("findPage")
	public Page<Test> findPage(@BeanParam  Test testData,@Context HttpServletRequest request,@Context HttpServletResponse response) {
		if (RpcContext.getContext().getResponse(HttpServletResponse.class) != null) { 
    		RpcContext.getContext().getResponse(HttpServletResponse.class).addHeader("Access-Control-Allow-Origin","*"); 
    		}
	
		//if(request.getMethod().equals("POST"))
			//testData=JSONObject.parseObject(form2JSON(request),TestData.class);
		if(request.getMethod().equals("POST")) 
		{
			//fastjson
			testData=JSONObjectX.parseObject(request, testData);
		}
		//String string=getRequestPayload(request);
	    
		/*Enumeration params=request.getParameterNames();
		while (params.hasMoreElements()) {
			 String paramName = (String) params.nextElement();  
		      String[] paramValues = request.getParameterValues(paramName);  
		      if (paramValues.length == 1) {  
		        String paramValue = paramValues[0];  
		        if (paramValue.length() != 0) {     
		         System.out.println("dubbo参数：" + paramName + "=" + paramValue);  
		        }  
		  }
		}*/
		
		return testDataService.findPage(new Page<Test>(request, response), testData);
	}

    @GET
	@POST
	@Path("save")
	public void save(@BeanParam  Test testData,@Context HttpServletRequest request,@Context HttpServletResponse response) {
		testDataService.save(testData);
	}

	@GET
	@POST
	@Path("delete")
	public void delete(@BeanParam  Test testData,@Context HttpServletRequest request,@Context HttpServletResponse response) {
		testDataService.delete(testData);
	}
	
}