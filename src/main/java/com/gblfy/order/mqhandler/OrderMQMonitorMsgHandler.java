package com.gblfy.order.mqhandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderMQMonitorMsgHandler implements ChannelAwareMessageListener {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {

        // msg就是rabbitmq传来的消息
        // 使用jackson解析
        MessageProperties messageProperties = message.getMessageProperties();

        log.info("编码类型:" + messageProperties.getContentType());
        log.info("编码:" + messageProperties.getContentEncoding());
        log.info("交换机:" + messageProperties.getReceivedExchange());
        log.info("路由:" + messageProperties.getReceivedRoutingKey());
        log.info("额外自定义的属性:" + messageProperties.getHeaders());
        String msg = new String(message.getBody());
        System.err.println("--------------------------------------" );


        System.err.println("--------------------------------------");
        System.err.println("消费端Payload: " + message.getBody());
//        byte[] body = message.getBody();


/**
 * 4中消息确认方式
 * //消息的标识，false只确认当前一个消息收到，true确认所有consumer获得的消息
 *         channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
 *
 *         //ack返回false，并重新回到队列，api里面解释得很清楚
 *         channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
 *
 *         //丢弃这条消息
 *         channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
 *
 *         //拒绝消息
 *         channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
 */


        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        // 消息的标识，false只确认当前一个消息收到，true确认所有consumer获得的消息
        channel.basicAck(deliveryTag, false);
//        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

//    public void onMessage(org.springframework.amqp.core.Message message, AMQP.Channel channel) {
//        System.err.println("--------------------------------------");
//        System.err.println("消费端Payload: " + message.getBody());
//        MessageProperties messageProperties = message.getMessageProperties();
//        Long deliveryTag = messageProperties.getDeliveryTag();
////        Long deliveryTag = (Long)message.getMessageProperties()getHeaders().get(AmqpHeaders.DELIVERY_TAG);
//        //手工ACK
//        channel.basicAck(deliveryTag, false);
//    }

    //    /**
//     * 接收MQ消息
//     *
//     * @param msg
//     */
    public void execute(Message message, Channel channel) {
        try {
//            System.err.println("--------------------------------------");
//            System.err.println("消费端Payload: " + message.getPayload());
//            Long deliveryTag = (Long)message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
//            //手工ACK
//            channel.basicAck(deliveryTag, false);


//            JsonNode jsonNode = MAPPER.readTree(msg);
//            String serviceName = jsonNode.get("serviceName").asText();
//            String routingKey = jsonNode.get("routingKey").asText();
//            String currentDate = jsonNode.get("currentDate").asText();
//
//            log.info("接口名称:" + serviceName);
//            log.info("路由routingKey:" + routingKey);
//            log.info("当前时间:" + currentDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    public void execute(String msg,Channel channel) {
//        try {
//            JsonNode jsonNode = MAPPER.readTree(msg);
//            String serviceName = jsonNode.get("serviceName").asText();
//            String routingKey = jsonNode.get("routingKey").asText();
//            String currentDate = jsonNode.get("currentDate").asText();
//
//            log.info("接口名称:" + serviceName);
//            log.info("路由routingKey:" + routingKey);
//            log.info("当前时间:" + currentDate);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}

