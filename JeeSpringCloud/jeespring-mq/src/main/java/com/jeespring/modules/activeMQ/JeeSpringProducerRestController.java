package com.jeespring.modules.activeMQ;

import com.jeespring.common.utils.SendMailUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Destination;
import java.util.ArrayList;
import java.util.List;

/**
 * ActiveMQController
 * @author 黄炳桂 516821420@qq.com
 * @version 2014-05-16
 */
@RestController
@RequestMapping(value = "rest/mq/producer")
@Api(value="ActiveMQ队列任务云接口", description="ActiveMQ队列任务云接口")
public class JeeSpringProducerRestController {
    @Autowired
    private JeeSpringProducer jeeSpringProducer;

    @RequestMapping(value = {"sendMessage"},method ={RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value="ActiveMQ队列云发送信息(Content-Type为text/html)", notes="ActiveMQ队列云发送信息(Content-Type为text/html)")
    @ApiImplicitParam(name = "message", value = "信息", required = false, dataType = "String",paramType="query")
    public void sendMessage(@RequestParam(required=false) String message){
        Destination destination = new ActiveMQQueue(JeeSpringProducer.ActiveMQQueueKey);
        jeeSpringProducer.sendMessage(destination,message);
    }

    @RequestMapping(value = {"sendMail"},method ={RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value="ActiveMQ队列云发送邮件(Content-Type为text/html)", notes="ActiveMQ队列云发送邮件(Content-Type为text/html)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "toMailAddr", value = "接收邮件", required = false, dataType = "String",paramType="query"),
            @ApiImplicitParam(name = "subject", value = "邮件主题", required = false, dataType = "String",paramType="query"),
            @ApiImplicitParam(name = "message", value = "邮件内容", required = false, dataType = "String",paramType="query")
    })
    public void sendMail(@RequestParam(required=false) String toMailAddr, @RequestParam(required=false) String subject, @RequestParam(required=false) String message) {
        List<String> list=new ArrayList();
        list.add(toMailAddr);
        list.add(subject);
        list.add(message);
        Destination destination = new ActiveMQQueue(JeeSpringProducer.ActiveMQQueueKeySendMailList);
        jeeSpringProducer.sendMessage(destination,list);
    }

    @RequestMapping(value = {"sendMailObject"},method ={RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value="ActiveMQ队列云发送邮件(Content-Type为text/html)", notes="ActiveMQ队列云发送邮件(Content-Type为text/html)")
    @ApiImplicitParam(name = "email", value = "email信息{toMailAddr,subject,message}", required = false, dataType = "Email",paramType="query")
    public void sendMailObject(Email email) {
        Destination destination = new ActiveMQQueue(JeeSpringProducer.ActiveMQQueueKeySendMailObject);
        jeeSpringProducer.sendMessage(destination,email);
    }
}
