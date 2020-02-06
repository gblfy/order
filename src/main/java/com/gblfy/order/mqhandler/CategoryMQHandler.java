package com.gblfy.order.mqhandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
            String type = jsonNode.get("type").asText();
            //通过 判断routingKey是否等于save相同即可
            log.info("商品分类消费者接收type:" + type);
            log.info("商品分类消费者接收cID:" + cId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 接收MQ消息，并做接收到的MQ消息做判断走不通的分支进行不同的处理
     *
     * @param msg
     */
//    public void execute(String msg) {
//        try {
//            JsonNode jsonNode = MAPPER.readTree(msg);
//            Long cId = jsonNode.get("cId").asLong();
//            String type = jsonNode.get("type").asText();
//            //相同逻辑处理通过 判断routingKey是否相同即可
//            if (StringUtils.equals(type, "insert") || StringUtils.equals(type, "save")) {
//                log.info("监听到insert和save处理的type:" + type);
//            } else if (StringUtils.equals(type, "update")) {
//                log.info("监听到update处理的type:" + type);
//            } else {
//                //其他逻辑统一在这里做处理
//                log.info("监听到其他统一处理的type:" + type);
//            }
//            System.out.println("商品分类消费者接收cID:" + cId);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
