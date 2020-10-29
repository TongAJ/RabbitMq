package com.csdn.consumer.config;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 描述:
 * 接受消息监听器
 *      如果只是接受消息，那可以不用配置RabbitConfig，只需要配置消息监听器即可
 *
 * @author tongaijie-9697
 * 2020-10-26 14:56
 */
@Component
// 监听 directQueue 队列的消息
@RabbitListener(queues = "directQueue")
public class DirectReceiver {

    @RabbitHandler
    public void process(Map testMessage){
        System.out.println("directMessage: " + testMessage.toString());
    }
}
