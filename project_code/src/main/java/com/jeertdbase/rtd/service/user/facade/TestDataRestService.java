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
package com.jeertdbase.rtd.service.user.facade;

import java.util.List;





import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Min;
import javax.ws.rs.BeanParam;
import javax.ws.rs.core.Context;

import com.jeertd.common.persistence.Page;
import com.jeertd.modules.test.entity.onetomany.TestDataMain;

/**
 * This interface acts as some kind of service broker for the original UserService

 * Here we want to simulate the twitter/weibo rest api, e.g.
 *
 * http://localhost:8888/user/1.json
 * http://localhost:8888/user/1.xml
 *
 * @author lishen
 */
public interface TestDataRestService {

    /**
     * the request object is just used to test jax-rs injection.
     */
	TestDataMain getUser(Long id/*, HttpServletRequest request*/);
	
    TestDataMain get(TestDataMain testDataMain,HttpServletRequest request,HttpServletResponse response) ;
   
    List<TestDataMain> list(TestDataMain testDataMain,HttpServletRequest request,HttpServletResponse response) ;
  
    TestDataMain save(TestDataMain testDataMain,HttpServletRequest request,HttpServletResponse response) ;
    
    Page<TestDataMain> findPage(TestDataMain testDataMain,HttpServletRequest request,HttpServletResponse response); 
    
    TestDataMain getUser3() ;
    
    String Test();
	
}
