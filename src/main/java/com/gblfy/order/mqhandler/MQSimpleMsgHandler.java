package com.gblfy.order.mqhandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MQSimpleMsgHandler {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 接收MQ消息
     *
     * @param msg
     */
    public void execute(String msg) {
        try {
            JsonNode jsonNode = MAPPER.readTree(msg);
            String serviceName = jsonNode.get("serviceName").asText();
            String routingKey = jsonNode.get("routingKey").asText();
            String currentDate = jsonNode.get("currentDate").asText();

            log.info("接口名称:" + serviceName);
            log.info("路由routingKey:" + routingKey);
            log.info("当前时间:" + currentDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

