package com.csdn.producer.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述:
 * RabbitTemplate配置
 *
 * @author tongaijie-9697
 * 2020-10-27 10:50
 */
@Configuration
public class RabbitConfig {

    /**
    * Description: 配置个性化RabbitTemplate，添加了ConfirmCallBack和ReturnCallBack
    * Param: [connectionFactory]
    * return: org.springframework.amqp.rabbit.core.RabbitTemplate
    * Author: tongaijie
    * Date: 2020/10/27
    */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        // true，触发回调函数，无论消息发送成功或失败都会调用
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
                System.out.println("ConfirmCallback:     "+"相关数据："+ correlationData);
                System.out.println("ConfirmCallback:     "+"确认情况："+ ack);
                System.out.println("ConfirmCallback:     "+"原因："+ cause);
        });

        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            System.out.println("ReturnCallback:     "+"消息："+message);
            System.out.println("ReturnCallback:     "+"回应码："+replyCode);
            System.out.println("ReturnCallback:     "+"回应信息："+replyText);
            System.out.println("ReturnCallback:     "+"交换机："+exchange);
            System.out.println("ReturnCallback:     "+"路由键："+routingKey);
        });

        return rabbitTemplate;
    }

}
