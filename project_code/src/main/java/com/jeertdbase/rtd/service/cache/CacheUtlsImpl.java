/**
 * Copyright &copy; 2012-2016 <a href="https://git.oschina.net/guanshijiehnan/JeeRTD">JeeSite</a> All rights reserved.
 */
package com.jeertdbase.rtd.service.cache;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.jeertd.common.persistence.Page;
import com.jeertd.common.utils.CacheUtils;
import com.jeertd.common.utils.JedisUtils;

import org.springframework.beans.factory.annotation.Autowired;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;


/**
 * 单表生成Service
 * @author ThinkGem
 * @version 2017-05-04
 */

@Path("cache")
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
public class CacheUtlsImpl implements CacheUtls {
	
	@GET
	@POST
	@Path("get")
	public String get(@QueryParam("key") String key,@QueryParam("cacheName") String cacheName) {
		Object object=CacheUtils.get(cacheName,key);
	     return (String) object;
	}  

	@GET
	@POST
	@Path("set")
	public String set(@QueryParam("key") String key, @QueryParam("value") String value,@QueryParam("cacheName") String cacheName) {

		String result = null;
		try {
			CacheUtils.put(cacheName,key, value);
			result="true";
		} catch (Exception e) {
			System.out.print(e.getMessage());
			result=e.getMessage();
		}
	    return  result;
	}
	
	@GET
	@POST
	@Path("delete")
	public boolean delete(@QueryParam("key") String key, @QueryParam("cacheName") String cacheName) {
		boolean result;
		try {
			CacheUtils.remove(cacheName, key);
			result=true;
		} catch (Exception e) {
			System.out.print(e.getMessage());
			result=false;
		}
	    return  result;
	}
}