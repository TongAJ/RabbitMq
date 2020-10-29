package com.csdn.consumer.config;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Description: 消息手动确认模式
 *
 * @author tongaijie
 * <p>
 * Create: 2020-10-27 17:07
 **/
@Component
public class MyAckReceiver implements ChannelAwareMessageListener {

    /**
     * Description:
     * Param: [message, channel]
     * return: void
     * Author: tongaijie
     * Date: 2020/10/27
     */
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            //因为传递消息的时候用的map传递,所以将Map从Message内取出需要做些处理
            String msg = message.toString();
            //可以点进Message里面看源码,单引号直接的数据就是我们的map消息数据
            String[] msgArray = msg.split("'");
            Map<String, String> msgMap = mapStringToMap(msgArray[1].trim());
            String messageId = msgMap.get("messageId");
            String messageData = msgMap.get("messageData");
            String createTime = msgMap.get("createTime");
            System.out.println("  MyAckReceiver  messageId:" + messageId + "  messageData:" + messageData + "  createTime:" + createTime);
            System.out.println("消费的主题消息来自：" + message.getMessageProperties().getConsumerQueue());

            // 直连交换机时，做的处理
            if (message.getMessageProperties().getConsumerQueue().equals("directQueue")) {
                // todo
            } else if (message.getMessageProperties().getConsumerQueue().equals("fanout.A")) {
                // 当时订阅A时，做的处理
                // todo
            }

            //第二个参数，手动确认可以被批处理，当该参数为 true 时，则可以一次性确认 delivery_tag 小于等于传入值的所有消息
            channel.basicAck(deliveryTag, true);
            //第二个参数，true会重新放回队列，所以需要自己根据业务逻辑判断什么时候使用拒绝
            //channel.basicReject(deliveryTag, true);
        } catch (Exception e) {
            channel.basicReject(deliveryTag, false);
            e.printStackTrace();

        }
    }

    /**
     * Description:
     * Param: [string]
     * return: java.util.Map<java.lang.String,java.lang.String>
     * Author: tongaijie
     * Date: 2020/10/28
     */
    private Map<String, String> mapStringToMap(String string) {
        string = string.substring(1, string.length() - 1);
        String[] strings = string.split(",", 3);
        Map<String, String> map = new HashMap<>(16);
        for (String element : strings) {
            String key = element.split("=")[0].trim();
            String value = element.split("=")[1];
            map.put(key, value);
        }
        return map;
    }

}
