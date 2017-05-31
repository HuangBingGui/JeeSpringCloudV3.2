package com.jeertdbase.rtd.service.user.facade;

import java.util.List;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.jeertd.common.persistence.Page;
import com.jeertd.modules.test.entity.onetomany.TestDataMain;
import com.jeertd.modules.test.service.onetomany.TestDataMainService;
import com.jeertdbase.rtd.service.user.User;
import com.jeertdbase.rtd.service.user.UserService;

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
 * @version 2015-04-06
 */

@Service(protocol = {"rest", "dubbo"}, group = "annotationConfig", validation = "true")
@Path("testdatamain")
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
public class TestDataRestServiceImpl implements TestDataRestService {
	 @Autowired
		private TestDataMainService testDataMainService;
	 
	    @GET
	    @Path("{id : \\d+}")
	    public TestDataMain getUser(@PathParam("id") Long id/*, @Context HttpServletRequest request*/) {
	        return testDataMainService.get(id.toString());
	    }
	
	    @GET
	    @Path("g3")
	    public TestDataMain getUser3() {
	    	TestDataMain testDataMain=new TestDataMain();
	    	testDataMain.setName("11111");
	        return testDataMainService.findList(testDataMain).get(0);
	    }
	    
	    @GET
	    @POST
	    @Path("get")
	    public TestDataMain get(@BeanParam TestDataMain testDataMain,@Context HttpServletRequest request,@Context HttpServletResponse response) {
	    	 return testDataMainService.get(testDataMain.getId());
	    }
	    
	    @GET
	    @POST
	    @Path("list")
	    public List<TestDataMain> list(@BeanParam TestDataMain testDataMain,@Context HttpServletRequest request,@Context HttpServletResponse response) {
	    	 return testDataMainService.findList(testDataMain);
	    }
	  
	    @GET
	    @POST
	    @Path("findPage")
	    public Page<TestDataMain> findPage(@BeanParam TestDataMain testDataMain,@Context HttpServletRequest request,@Context HttpServletResponse response) {
	    	 return testDataMainService.findPage(new Page<TestDataMain>(request, response),testDataMain);
	    }

	    @GET
	    @POST
	    @Path("save")
	    public TestDataMain save(@BeanParam TestDataMain testDataMain,@Context HttpServletRequest request,@Context HttpServletResponse response) {
	    	TestDataMain testDataMainDb=testDataMainService.get(testDataMain.getId());
	    	testDataMainDb.setName(testDataMain.getName());
	    	testDataMainService.save(testDataMainDb);
	    	 return testDataMainDb;
	    }

	    @GET
	    @POST
	    @Path("test")
		public String Test() {
			// TODO Auto-generated method stub
			return "Test";
		}
	    
	    
}