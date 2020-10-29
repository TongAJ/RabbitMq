package com.csdn.producer.controller;

import com.csdn.producer.config.TopicRabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 描述:
 * 发送信息Controller
 *
 * @author tongaijie-9697
 * 2020-10-23 14:43
 */

@RestController
@RequestMapping("/send")
public class SendMessageController {

    private RabbitTemplate rabbitTemplate;

    private Map<String,Object> map = new HashMap<>();

    {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "test rabbitMq,hello!";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        map.put("messageId",messageId);
        map.put("messageData",messageData);
        map.put("createTime",createTime);
    }

    @GetMapping("direct")
    public String sendDirectMessage(){
        rabbitTemplate.convertAndSend("directExchange","directRouting",map);
        return "SUCCESS";
    }

    @GetMapping("topicMan")
    public String sendTopicManMessage(){
        rabbitTemplate.convertAndSend("topicExchange", TopicRabbitConfig.MAN,map);
        return "SUCCESS";
    }

    @GetMapping("topicWoMan")
    public String sendTopicWoManMessage(){
        rabbitTemplate.convertAndSend("topicExchange", TopicRabbitConfig.ALL,map);
        return "SUCCESS";
    }

    @GetMapping("fanout")
    public String fanoutMessage(){
        rabbitTemplate.convertAndSend("fanoutExchange",null,map);
        return "SUCCESS";
    }

    /**
     * 没找到exchange交换机
     *
     *      ConfirmCallback:     相关数据：null
     *      ConfirmCallback:     确认情况：false
     *      ConfirmCallback:
     *      原因：channel error; protocol method: #method<channel.close>(reply-code=404, reply-text=NOT_FOUND
     *      - no exchange 'non.exchange' in vhost '/', class-id=60, method-id=40)
     * @return SUCCESS
     */
    @GetMapping("testConfirmBack")
    public String confirmBack(){
        rabbitTemplate.convertAndSend("non.exchange","directRouting",map);
        return "SUCCESS";
    }

    /**
     * 找到exchange交换机，但是没找到binding的routingKey
     *      ConfirmCallback:     相关数据：null
     *      ConfirmCallback:     确认情况：true
     *      ConfirmCallback:     原因：null
     *
     *      ReturnCallback:     消息：(Body:'{createTime=2020-10-27, messageId=4ad1d1d7-502d-4b00-9a26-2d63a990e183, messageData=test rabbitMq,hello!}' MessageProperties [headers={}, contentType=application/x-java-serialized-object, contentLength=0, receivedDeliveryMode=PERSISTENT, priority=0, deliveryTag=0])
     *      ReturnCallback:     回应码：312
     *      ReturnCallback:     回应信息：NO_ROUTE
     *      ReturnCallback:     交换机：lonelyDirectExchange
     *      ReturnCallback:     路由键：directRouting
     * @return SUCCESS
     */
    @GetMapping("lonelyDirectExchange")
    public String lonelyDirectExchange(){
        rabbitTemplate.convertAndSend("lonelyDirectExchange","directRouting",map);
        return "SUCCESS";
    }

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
}
