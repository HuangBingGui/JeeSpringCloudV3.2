package com.jeespring.modules.act;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(locations= {"classpath:spring-context-activiti.xml"})
public class ActConfig{
}

