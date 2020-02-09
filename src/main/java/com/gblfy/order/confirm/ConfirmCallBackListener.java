package com.gblfy.order.confirm;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Service;

/**
 * Confirm 消息确认机制
 */
@Service("confirmCallBackListener")
public class ConfirmCallBackListener implements RabbitTemplate.ConfirmCallback {

    /**
     * 生产者消息发送成功与失败确认机制
     * <p>
     * 1. ack
     * true : 标志生产者将消息发出成功
     * false: 标志生产者将消息发出失败
     * 2. ack :true 意味着消息发送成功 有2种场景
     * 第一种：生产者将消息成功发送到指定队列中，等待消费者消费消息
     * 第两种：生产者将消息发送成功，但是，由于无法路由到指定的消息
     * 队列，这种场景的消息，会被return机制监听到，后续进行补偿机制，做消息补发处理
     * </p>
     *
     * @param correlationData 队列消息的唯一标识ID，消息做补偿机制会用到
     * @param ack             ack 消息是否发送成功的标识
     * @param cause           消息发送失败的原因
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.err.println("correlationData: " + correlationData);
        System.err.println("ack: " + ack);
        if (ack) {
            System.out.println("消息: " + correlationData + "，已经被ack成功");
        } else {
            System.out.println("消息: " + correlationData + "，nack，失败原因是：" + cause);
            System.err.println("异常处理....");
        }
    }
}

/**
 * ！ack 场景结果示例:
 * <p>
 * correlationData: CorrelationData [id=a37285dc-5dd6-4e22-8cc4-5c0fbf67b568]
 * ack: false
 * 异常处理....
 * 消息: CorrelationData [id=a37285dc-5dd6-4e22-8cc4-5c0fbf67b568]，
 * nack，失败原因是：channel error;
 * protocol method: #method<channel.close>(reply-code=404, reply-text=NOT_FOUND - no exchange 'FIS-TRACE-COMMON-EXCHANGE' in vhost '/admin',
 * class-id=60, method-id=40)
 */

