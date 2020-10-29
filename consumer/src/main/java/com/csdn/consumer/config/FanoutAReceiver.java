package com.csdn.consumer.config;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 描述:
 * 主题A订阅
 *
 * @author tongaijie-9697
 * 2020-10-27 10:36
 */
@Component
@RabbitListener(queues = "fanout.A")
public class FanoutAReceiver {

    @RabbitHandler
    public void process(Map testMessage){
        System.out.println("fanout.A:" + testMessage.toString());
    }
}
