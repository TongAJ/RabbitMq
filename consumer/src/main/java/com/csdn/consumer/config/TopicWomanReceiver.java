package com.csdn.consumer.config;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 描述:
 * 主题消息监听器
 *
 * @author tongaijie-9697
 * 2020-10-26 15:22
 */

@Component
// 监听 manQueue 消息队列
@RabbitListener(queues = "topic.woman")
public class TopicWomanReceiver {

    @RabbitHandler
    public void process(Map testMessage){
        System.out.println("womanQueue 接受的消息：" + testMessage.toString());
    }
}
