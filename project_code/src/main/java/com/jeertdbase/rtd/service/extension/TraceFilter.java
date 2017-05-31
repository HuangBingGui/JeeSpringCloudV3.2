/**
 * Copyright 1999-2014 dangdang.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jeertdbase.rtd.service.extension;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MediaType;

import java.io.IOException;

/**
 * @author lishen
 */
@Priority(Priorities.USER)
public class TraceFilter implements ContainerRequestFilter, ContainerResponseFilter {

    public void filter(ContainerRequestContext requestContext) throws IOException {
        System.out.println("Request filter invoked");
        
        /*HttpServletRequest req = (HttpServletRequest)requestContext.getRequest();
        req.setCharacterEncoding("UTF-8");*/
        
       /*String headerString = requestContext.getHeaderString("content-type");
        if (headerString != null) {
            //如果content-type以"application/x-www-form-urlencoded"开头，则处理
           if (headerString.startsWith(MediaType.APPLICATION_FORM_URLENCODED))
        	   requestContext.getHeaders().putSingle("content-type", MediaType.APPLICATION_FORM_URLENCODED);
        }*/
        
    }

    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        System.out.println("Response filter invoked");
        /*if (containerRequestContext.getMethod().equals("OPTIONS")) { 
        	containerResponseContext.getHeaders().add("Access-Control-Allow-Origin", "*"); 
        	containerResponseContext.getHeaders().add("Access-Control-Allow-Headers", "Content-Type,x-requested-with,Authorization,Access-Control-Allow-Origin"); 
        	containerResponseContext.getHeaders().add("Access-Control-Allow-Methods", "POST, GET, OPTIONS"); 
        	containerResponseContext.getHeaders().add("Access-Control-Max-Age" ,"360"); }*/
    }
}