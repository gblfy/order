package com.gblfy.order.re;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.stereotype.Service;

/**
 * 消息 return机制
 */
@Service("returnCallBackListener")
public class ReturnCallBackListener implements ReturnCallback {

    /**
     * 消息无法路由 触发消息 return机制
     * <p></p>
     * 1. 消费者在消息没有被路由到合适队列情况下会被return监听，而不会自动删除
     * 2. 会监听到生产者发送消息的关键信息
     * 3. 根据关键信息，后续进行补偿机制，做消息补发处理
     * </p>
     *
     * @param message    消息实体
     * @param replyCode  应答码312
     * @param replyText  NO_ROUTE
     * @param exchange   交换机
     * @param routingKey 路由routingKey
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        System.err.println("return exchange: " + exchange + ", routingKey: "
                + routingKey + ", replyCode: " + replyCode + ", replyText: " + replyText);
    }

/**
 * 场景结果示例：
 * return exchange: FIS-TRACE-COMMON-EXCHANGE, routingKey: fis-str.user, replyCode: 312, replyText: NO_ROUTE
 * correlationData: CorrelationData [id=30d924db-77b4-41df-bbe6-9a8f0eb3fe7a]
 * ack: true
 * 消息: CorrelationData [id=30d924db-77b4-41df-bbe6-9a8f0eb3fe7a]，已经被ack成功
 */

}
