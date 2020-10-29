package com.csdn.producer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述:
 * 发布订阅配置
 *
 * @author tongaijie-9697
 * 2020-10-26 15:37
 */

@Configuration
public class FanoutRabbitConfig {

    public static final String A = "fanout.A";
    public static final String B = "fanout.B";
    public static final String C = "fanout.C";

    @Bean
    public Queue fanoutAQueue(){
        return new Queue(FanoutRabbitConfig.A);
    }

    @Bean
    public Queue fanoutBQueue(){
        return new Queue(FanoutRabbitConfig.B);
    }

    @Bean
    public Queue fanoutCQueue(){
        return new Queue(FanoutRabbitConfig.C);
    }

    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("fanoutExchange");
    }

    @Bean
    public Binding fanoutABinding(){
        return BindingBuilder.bind(fanoutAQueue()).to(fanoutExchange());
    }
    @Bean
    public Binding fanoutBBinding(){
        return BindingBuilder.bind(fanoutBQueue()).to(fanoutExchange());
    }
    @Bean
    public Binding fanoutCBinding(){
        return BindingBuilder.bind(fanoutCQueue()).to(fanoutExchange());
    }
}
