
package com.gblfy.order.mqhandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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
            System.out.println("菜单消费者接收mID:" + mId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

