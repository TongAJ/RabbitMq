package com.csdn.producer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述:
 * 主题交换器配置
 *
 * @author tongaijie-9697
 * 2020-10-26 15:06
 */

@Configuration
public class TopicRabbitConfig {

    public static final String MAN = "topic.man";
    public static final String WOMAN = "topic.woman";
    public static final String ALL = "topic.#";

    @Bean
    public Queue manQueue(){
        return new Queue(TopicRabbitConfig.MAN);
    }

    @Bean
    public Queue womanQueue(){
        return new Queue(TopicRabbitConfig.WOMAN);
    }

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange("topicExchange");
    }

    @Bean
    public Binding topicManBinding(){
        return BindingBuilder.bind(manQueue()).to(topicExchange()).with(TopicRabbitConfig.MAN);
    }

    @Bean
    public Binding topicAllBinding(){
        return BindingBuilder.bind(womanQueue()).to(topicExchange()).with(TopicRabbitConfig.ALL);
    }
}
