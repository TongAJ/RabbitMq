package com.csdn.producer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述:
 * 直连配置
 *
 * @author tongaijie-9697
 * 2020-10-23 14:26
 */
@Configuration
public class DirectRabbitConfig {

    /**
     *      public Queue(String name, boolean durable) {
     *          this(name, durable, false, false, (Map)null);
     *      }
     *
     * @return 直连队列
     */
    @Bean
    public Queue directQueue(){
        return new Queue("directQueue",true);
    }

    /**
     *      public DirectExchange(String name, boolean durable, boolean autoDelete) {
     *         super(name, durable, autoDelete);
     *     }
     *
     * @return 直连交换器
     */
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("directExchange",true,false);
    }

    /**
     * 绑定  将队列和交换机绑定, 并设置用于匹配键：directRouting
     */
    @Bean
    public Binding bindingDirect(){
        return BindingBuilder.bind(directQueue()).to(directExchange()).with("directRouting");
    }

    @Bean
    DirectExchange lonelyDirectExchange() {
        return new DirectExchange("lonelyDirectExchange");
    }

}
