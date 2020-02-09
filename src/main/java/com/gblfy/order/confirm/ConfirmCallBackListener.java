package com.gblfy.order.confirm;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Service;

@Service("confirmCallBackListener")
public class ConfirmCallBackListener implements RabbitTemplate.ConfirmCallback{

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.err.println("correlationData: " + correlationData);
        System.err.println("ack: " + ack);
        if (ack) {
            System.out.println("消息: "+correlationData+"，已经被ack成功");
        }else {
            System.out.println("消息: "+correlationData+"，nack，失败原因是："+cause);
            System.err.println("异常处理....");
        }
    }
    /**
     * 参考链接:
     * rabbitmq-使用publisher confirm代替事务
     * https://blog.csdn.net/weixin_34336292/article/details/91726977
     */
}

