
package com.gblfy.order.mqhandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MenuMQHandler {

    private static final ObjectMapper MAPPER = new ObjectMapper();


    /**
     * 接收MQ消息
     *
     * @param msg
     */

    public void execute(String msg) {
        try {
            JsonNode jsonNode = MAPPER.readTree(msg);
            Long mId = jsonNode.get("mId").asLong();
            String type = jsonNode.get("type").asText();
            //通过 判断routingKey是否等于insert相同即可
            log.info("菜单消费者接收type:" + type);
            log.info("菜单消费者接收mId:" + mId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

