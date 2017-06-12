/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jeertd.rtd.service.oanotity;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeertd.common.persistence.Page;

import org.nutz.json.Json;
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

import com.jeertd.modules.oa.entity.OaNotify;
import com.jeertd.modules.oa.service.OaNotifyService;
import com.jeertd.rtd.api.extension.JSONObjectX;

/**
 * teacher kwanService
 * @author teacher kwan
 * @version 2017-06-02
 */
@Service(protocol = {"rest", "dubbo"}, group = "annotationConfig", validation = "true")
@Path("oaNotify")
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML,MediaType.APPLICATION_FORM_URLENCODED})
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8}) 
public class OaNotifyServiceDubboImpl implements OaNotifyServiceDubbo {

	@Autowired
	private OaNotifyService oaNotifyService;

	@GET
	@POST
	@Path("get")
	public OaNotify get(@BeanParam  OaNotify oaNotify,@Context HttpServletRequest request,@Context HttpServletResponse response) {
		//return oaNotifyService.get(oaNotify);
		return oaNotifyService.get(oaNotify.getId());
	}

	@GET
	@POST
	@Path("list")
	public List<OaNotify> list(@BeanParam  OaNotify oaNotify,@Context HttpServletRequest request,@Context HttpServletResponse response) {
		return oaNotifyService.findList(oaNotify);
	}

	@GET
	@POST
	@Path("findPage")
	public Page<OaNotify> findPage(@BeanParam  OaNotify oaNotify,@Context HttpServletRequest request,@Context HttpServletResponse response) {
		
		//String rqst=JSONObjectX.getRequestPayload(request);
		
		if(request.getMethod().equals("POST")){
		   oaNotify=JSONObjectX.parseObject(request, oaNotify);
	    }
			
		System.out.print("getTitle:"+oaNotify.getTitle());
		return oaNotifyService.findPage(new Page<OaNotify>(request, response), oaNotify);
	}

	@GET
	@POST
	@Path("save")
	public void save(@BeanParam  OaNotify oaNotify,@Context HttpServletRequest request,@Context HttpServletResponse response) {
		if(request.getParameter("oaNotify")!=null) {
			 System.out.print(request.getParameter("oaNotify"));
			 oaNotify=(OaNotify)JSONObject.parseObject(request.getParameter("oaNotify"),oaNotify.getClass());
		}
		oaNotifyService.save(oaNotify);
	}

	@GET
	@POST
	@Path("delete")
	public void delete(@BeanParam  OaNotify oaNotify,@Context HttpServletRequest request,@Context HttpServletResponse response) {
		if(request.getParameter("oaNotify")!=null) {
			 System.out.print(request.getParameter("oaNotify"));
			 oaNotify=(OaNotify)JSONObject.parseObject(request.getParameter("oaNotify"),oaNotify.getClass());
		}
		oaNotifyService.delete(oaNotify);
	}
	
}