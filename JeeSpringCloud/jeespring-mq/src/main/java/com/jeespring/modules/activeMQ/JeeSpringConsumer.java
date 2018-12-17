package com.jeespring.modules.activeMQ;

import com.jeespring.common.utils.SendMailUtil;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 消息消费者.
 * @author 黄炳桂 516821420@qq.com
 * @version v.0.1
 * @date 2016年8月23日
 */

@Component
public class JeeSpringConsumer {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    // 使用JmsListener配置消费者监听的队列，其中text是接收到的消息
    @JmsListener(destination = JeeSpringProducer.ActiveMQQueueKeySendMailList)
    public void receiveQueue(List<String> list) {
        String toMailAddr=list.get(0);
        String subject=list.get(1);
        String message=list.get(2);
        SendMailUtil.sendCommonMail(toMailAddr,subject,message);
    }

    @JmsListener(destination = JeeSpringProducer.ActiveMQQueueKeySendMailObject)
    public void receiveQueue(Email email) {
        SendMailUtil.sendCommonMail(email.getToMailAddr(),email.getSubject(),email.getMessage());
    }

    // 使用JmsListener配置消费者监听的队列，其中text是接收到的消息
    @JmsListener(destination = JeeSpringProducer.ActiveMQQueueKey)
    public void receiveQueue(String text) {
        System.out.println(dateFormat.format(new Date()) + " | " +"ActiveMQ Consumer :"+JeeSpringProducer.ActiveMQQueueKey+":收到的报文为:"+text);
    }

    // 使用JmsListener配置消费者监听的队列，其中text是接收到的消息
    @JmsListener(destination = JeeSpringProducer.ActiveMQQueueKeyA)
    public void receiveQueueA(String text) {
        System.out.println(dateFormat.format(new Date()) + " | " +"ActiveMQ Consumer :"+JeeSpringProducer.ActiveMQQueueKeyA+":收到的报文为:"+text);
    }

    // 使用JmsListener配置消费者监听的队列，其中text是接收到的消息
    @JmsListener(destination = JeeSpringProducer.ActiveMQQueueKeyB)
    @SendTo("jeespring.out.queue")
    public String receiveQueueB(String text) {
        System.out.println(dateFormat.format(new Date()) + " | " +"ActiveMQ Consumer :"+JeeSpringProducer.ActiveMQQueueKeyA+"收到的报文为:"+text);
        return "return message:"+text;
    }
}