/**
 * Copyright &copy; 2012-2016 <a href="https://git.oschina.net/guanshijiehnan/JeeRTD">JeeSite</a> All rights reserved.
 */
package com.jeertdbase.rtd.service.redis;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.jeertd.common.persistence.Page;
import com.jeertd.common.utils.JedisUtils;

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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * 单表生成Service
 * @author ThinkGem
 * @version 2017-05-04
 */
@Path("redis")
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
public class RedisDubboImpl implements RedisDubbo {

	@GET
	@POST
	@Path("get")
	public String get(@QueryParam("key") String key) {
		return JedisUtils.get(key);
	}

	@GET
	@POST
	@Path("set")
	public String set(@QueryParam("key") String key, @QueryParam("value") String value,@QueryParam("cacheSeconds") int cacheSeconds) {
		return JedisUtils.set(key, value, cacheSeconds);
	}
	
	@GET
	@POST
	@Path("delete")
	public long delete(@QueryParam("key") String key) {
		return JedisUtils.del(key);
	}
}