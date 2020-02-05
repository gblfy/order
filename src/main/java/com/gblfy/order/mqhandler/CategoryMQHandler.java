package com.gblfy.order.mqhandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CategoryMQHandler {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 接收MQ消息
     *
     * @param msg
     */
    public void execute(String msg) {
        try {
            JsonNode jsonNode = MAPPER.readTree(msg);
            Long cId = jsonNode.get("cId").asLong();
            System.out.println("商品分类消费者接收cID:" + cId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
