package com.gblfy.order.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MQSendUtils {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 发送MQ消息公用类
     *
     * @param mId
     * @param type
     */
    public void sendMsg(int mId, String type) {
        try {
            //发送消息到MQ的交换机，通知其他系统
            Map<String, Object> msg = new HashMap<String, Object>();
            msg.put("mId", mId);
            msg.put("type", type);
            msg.put("date", System.currentTimeMillis());
            rabbitTemplate.convertAndSend("order." + type, MAPPER.writeValueAsString(msg));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 发送MQ消息公用类
     *
     * @param cId
     * @param type
     */
    public void sendMsg2(int cId, String type) {
        try {
            //发送消息到MQ的交换机，通知其他系统
            Map<String, Object> msg = new HashMap<String, Object>();
            msg.put("cId", cId);
            msg.put("type", type);
            msg.put("date", System.currentTimeMillis());
            rabbitTemplate.convertAndSend("order." + type, MAPPER.writeValueAsString(msg));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
