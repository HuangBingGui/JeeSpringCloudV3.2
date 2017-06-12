/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jeertd.rtd.service.test;

import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.jeertd.common.persistence.Page;

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

import com.jeertd.modules.sys.entity.User;
import com.jeertd.modules.test.entity.one.FormLeave;
import com.jeertd.modules.test.service.one.FormLeaveService;
import com.jeertd.rtd.api.extension.JSONObjectX;

/**
 * 员工请假Service
 * @author liugf
 * @version 2017-06-02
 */
@Service(protocol = {"rest", "dubbo"}, group = "annotationConfig", validation = "true")
@Path("formLeave")
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML,MediaType.APPLICATION_FORM_URLENCODED})
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
public class FormLeaveServiceDubboImpl implements FormLeaveServiceDubbo {

	@Autowired
	private FormLeaveService formLeaveService;

	@GET
	@POST
	@Path("get")
	public FormLeave get(@BeanParam  FormLeave formLeave,@Context HttpServletRequest request,@Context HttpServletResponse response) {
		//return formLeaveService.get(formLeave);
		return formLeaveService.get(formLeave.getId());
	}

	@GET
	@POST
	@Path("list")
	public List<FormLeave> list(@BeanParam  FormLeave formLeave,@Context HttpServletRequest request,@Context HttpServletResponse response) {
		formLeave=JSONObjectX.parseObject(request,formLeave);
		return formLeaveService.findList(formLeave);
	}

	@GET
	@POST
	@Path("findPage")
	public Page<FormLeave> findPage(@BeanParam  FormLeave formLeave,@Context HttpServletRequest request,@Context HttpServletResponse response) {
		return formLeaveService.findPage(new Page<FormLeave>(request, response), formLeave);
	}

	@GET
	@POST
	@Path("save")
	public void save(@BeanParam  FormLeave formLeave,@Context HttpServletRequest request,@Context HttpServletResponse response) {
		formLeaveService.save(formLeave);
	}

	@GET
	@POST
	@Path("delete")
	public void delete(@BeanParam  FormLeave formLeave,@Context HttpServletRequest request,@Context HttpServletResponse response) {
		formLeaveService.delete(formLeave);
	}
	
}