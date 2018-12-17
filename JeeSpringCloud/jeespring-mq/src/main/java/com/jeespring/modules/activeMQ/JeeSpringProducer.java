package com.jeespring.modules.activeMQ;

import com.jeespring.common.redis.RedisUtils;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;
import javax.jms.Destination;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息生产者.
 * @author 黄炳桂 516821420@qq.com
 * @version v.0.1
 * @date 2016年8月23日
 */

@Service
public class JeeSpringProducer {
    private static Logger logger = LoggerFactory.getLogger(RedisUtils.class);
    public static final  String ActiveMQQueueKeySendMailList="JeeSpring.ActiveMQ.queue.sendmaillist";
    public static final  String ActiveMQQueueKeySendMailObject="JeeSpring.ActiveMQ.queue.sendmailobject";
    public static final  String ActiveMQQueueKey="JeeSpring.ActiveMQ.queue";
    public static final  String ActiveMQQueueKeyA="JeeSpring.ActiveMQ.queueA";
    public static final  String ActiveMQQueueKeyB="JeeSpring.ActiveMQ.queueB";
    public static String RUN_MESSAGE="ActvieMQ连接异常，请开启ActvieMQ服务.";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    // 也可以注入JmsTemplate，JmsMessagingTemplate对JmsTemplate进行了封装
    @Autowired
    private JmsMessagingTemplate jmsTemplate;
    // 发送消息，destination是发送到的队列，message是待发送的消息

    public void sendMessage( Destination destination,List<String> list){
        try{
            jmsTemplate.convertAndSend(destination, list);
        }catch (Exception e){
            logger.error(dateFormat.format(new Date()) + " | " +"ActvieMQ:"+RUN_MESSAGE+e.getMessage(), RUN_MESSAGE+e.getMessage());
        }
    }

    public void sendMessage( Destination destination,Email email){
        try{
            jmsTemplate.convertAndSend(destination, email);
        }catch (Exception e){
            logger.error(dateFormat.format(new Date()) + " | " +"ActvieMQ:"+RUN_MESSAGE+e.getMessage(), RUN_MESSAGE+e.getMessage());
        }
    }

    public void sendMessage( Destination destination,String message){
        try{
            jmsTemplate.convertAndSend(destination, message);
        }catch (Exception e){
            logger.error(dateFormat.format(new Date()) + " | " +"ActvieMQ:"+RUN_MESSAGE+e.getMessage(), RUN_MESSAGE+e.getMessage());
        }
    }

    public void sendMessageA( final String message){
        try{
            Destination destinationA = new ActiveMQQueue(JeeSpringProducer.ActiveMQQueueKeyA);
            jmsTemplate.convertAndSend(destinationA, message);
        }catch (Exception e){
            logger.error(dateFormat.format(new Date()) + " | " +"ActvieMQ:"+RUN_MESSAGE+e.getMessage(), RUN_MESSAGE+e.getMessage());
        }
    }
    public void sendMessageB(final String message){
        try{
            Destination destinationB = new ActiveMQQueue(JeeSpringProducer.ActiveMQQueueKeyA);
            jmsTemplate.convertAndSend(destinationB, message);
        }catch (Exception e){
            logger.error(dateFormat.format(new Date()) + " | " +"ActvieMQ:"+RUN_MESSAGE+e.getMessage(), RUN_MESSAGE+e.getMessage());
        }
    }
    @JmsListener(destination="jeespring.out.queue")
    public void consumerMessage(String text){
        System.out.println(dateFormat.format(new Date()) + " | " +"ActvieMQ:从out.queue队列收到的回复报文为:"+text);
    }
}