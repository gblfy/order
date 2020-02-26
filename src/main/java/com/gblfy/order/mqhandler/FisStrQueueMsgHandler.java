package com.gblfy.order.mqhandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FisStrQueueMsgHandler implements ChannelAwareMessageListener {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 接收MQ STRING类型消息
     *
     * @param message
     * @param channel
     * @throws Exception
     */
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {

        String msg = new String(message.getBody(),"UTF-8");
        log.info("接收MQ消息:" + msg);

        // 消息的标识，false只确认当前一个消息收到，true确认所有consumer获得的消息
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}

/**
 * 4种消息确认方式:
 * <p>
 * 1. 消息的标识，false只确认当前一个消息收到，true确认所有consumer获得的消息
 * channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
 * 2. ack返回false，并重新回到队列，api里面解释得很清楚
 * channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
 * 3. 丢弃这条消息
 * channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
 * 4. 拒绝消息
 * channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
 */