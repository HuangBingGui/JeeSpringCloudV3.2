/**
 * Copyright &copy; 2012-2016 <a href="https://gitee.com/JeeHuangBingGui/jeeSpringCloud">JeeSpring</a> All rights reserved.
 */
package com.jeespring.modules.act.service.ext;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.UserIdentityManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Activiti User Entity Service Factory
 * @author JeeSpring
 * @version 2013-11-03
 */
public class ActUserEntityServiceFactory implements SessionFactory {
	
	@Autowired
	private ActUserEntityService actUserEntityService;

	@Override
    public Class<?> getSessionType() {
		// 返回原始的UserIdentityManager类型
		return UserIdentityManager.class;
	}

	@Override
    public Session openSession() {
		// 返回自定义的UserEntityManager实例
		return actUserEntityService;
	}

}
