package com.jeespring.modules.scheduling;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan("com.jeespring.modules.scheduling")
@EnableScheduling
public class JeeSpringTaskSchedulerConfig {

}