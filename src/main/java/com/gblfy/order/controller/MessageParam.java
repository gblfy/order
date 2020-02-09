package com.gblfy.order.controller;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;

public class MessageParam implements MessagePostProcessor {
    @Override
    public Message postProcessMessage(Message message) throws AmqpException {
        System.err.println("------添加额外的设置---------");
        message.getMessageProperties().getHeaders().put("desc", "额外修改的信息描述");
        message.getMessageProperties().getHeaders().put("attr", "额外新加的属性");
        return message;
    }
}
