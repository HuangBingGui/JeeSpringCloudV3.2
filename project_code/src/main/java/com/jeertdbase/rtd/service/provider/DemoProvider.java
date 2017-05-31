/*
 * Copyright 1999-2011 Alibaba Group.
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
package com.jeertdbase.rtd.service.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.dubbo.config.ProtocolConfig;

public class DemoProvider {

	private static ClassPathXmlApplicationContext context;

	public static void main(String[] args) {
        try {
			context = new ClassPathXmlApplicationContext(new String[]{"classpath:/spring-context.xml","classpath:dubbo-demo-provider.xml"});
        	//context = new ClassPathXmlApplicationContext("classpath*:/spring-context*.xml");
			context.start();
		
        	//context2 = new ClassPathXmlApplicationContext("classpath:dubbo-demo-provider.xml");
			//context2.start();
			
			//com.alibaba.dubbo.container.Main.main(args);
		} catch (Exception e) {
			//log.error("== DubboProvider context start error:",e);
		}
		synchronized (DemoProvider.class) {
			while (true) {
				try {
					DemoProvider.class.wait();
				} catch (InterruptedException e) {
					//log.error("== synchronized error:",e);
				}
			}
		}
    }
}