package com.company.project.modules.activeMQ;

import com.jeespring.common.utils.SendMailUtil;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 消息消费者.
 * @author 黄炳桂 516821420@qq.com
 * @version v.0.1
 * @date 2016年8月23日
 */

@Component
public class CompanyConsumer {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // 使用JmsListener配置消费者监听的队列，其中text是接收到的消息
    @JmsListener(destination = CompanyProducer.ActiveMQQueueKey)
    public void receiveQueue(String text) {
        System.out.println(dateFormat.format(new Date()) + " | " +"ActiveMQ Consumer :"+ CompanyProducer.ActiveMQQueueKey+":收到的报文为:"+text);
    }

    // 使用JmsListener配置消费者监听的队列，其中text是接收到的消息
    @JmsListener(destination = CompanyProducer.ActiveMQQueueKeyA)
    public void receiveQueueA(String text) {
        System.out.println(dateFormat.format(new Date()) + " | " +"ActiveMQ Consumer :"+ CompanyProducer.ActiveMQQueueKeyA+":收到的报文为:"+text);
    }

    // 使用JmsListener配置消费者监听的队列，其中text是接收到的消息
    @JmsListener(destination = CompanyProducer.ActiveMQQueueKeyB)
    @SendTo("company.out.queue")
    public String receiveQueueB(String text) {
        System.out.println(dateFormat.format(new Date()) + " | " +"ActiveMQ Consumer :"+ CompanyProducer.ActiveMQQueueKeyA+"收到的报文为:"+text);
        return "return message:"+text;
    }
}