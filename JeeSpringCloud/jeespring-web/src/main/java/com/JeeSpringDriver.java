/**
 * Copyright &copy; 2012-2016 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved.
 */
package com;

import com.jeespring.common.websocket.WebSockertFilter;
import com.jeespring.modules.sys.service.SystemService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * jeespring
 * springboot的启动类
 * * * @author 黄炳桂 516821420@qq.com
 * Created on 2017/1/8 16:20
 *
 * @EnableAutoConfiguration(exclude = {
 * org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class,
 * org.activiti.spring.boot.SecurityAutoConfiguration.class,
 * })
 * 使用lazyInit缩短Spring Boot启动时间//, lazyInit = true
 */
@EnableCaching
@SpringBootApplication
@ServletComponentScan(basePackages = {"com.jeespring", "com.company","cn.xxx"})
@ComponentScan(basePackages = {"com","com.jeespring", "com.company","cn.xxx"})
@MapperScan(basePackages={"com.jeespring.modules.**.dao","com.company.project.modules.*.dao","cn.xxx.xxx.modules.*.dao"})
@EnableScheduling
@EnableAutoConfiguration
@Configuration
//部署开发在线文档：https://gitee.com/JeeHuangBingGui/jeeSpringCloud/wikis
//部署开发异常处理在线文档：https://gitee.com/JeeHuangBingGui/jeeSpringCloud/wikis
//开发文档：https://gitee.com/JeeHuangBingGui/jeeSpringCloud/attach_files
//单项目版本：https://gitee.com/JeeHuangBingGui/JeeSpringCloudV3.0
public class JeeSpringDriver extends SpringBootServletInitializer
{

    //部署开发在线文档：https://gitee.com/JeeHuangBingGui/jeeSpringCloud/wikis
    //部署开发异常处理在线文档：https://gitee.com/JeeHuangBingGui/jeeSpringCloud/wikis
    //开发文档：https://gitee.com/JeeHuangBingGui/jeeSpringCloud/attach_files
    //单项目版本：https://gitee.com/JeeHuangBingGui/JeeSpringCloudV3.0
    public static void main(String[] args) {
        SpringApplication.run(JeeSpringDriver.class, args);
        SystemService.printKeyLoadMessage();
        //IM WebSocker
        WebSockertFilter w = new WebSockertFilter();
        w.startWebsocketChatServer();
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        //this.setRegisterErrorPageFilter(false); // 错误页面有容器来处理，而不是SpringBoot
        return builder.sources(JeeSpringDriver.class);
    }
}
