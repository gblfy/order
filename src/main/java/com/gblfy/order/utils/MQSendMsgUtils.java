package com.gblfy.order.utils;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gblfy.order.pojo.FisCallingTrace;
import com.gblfy.order.pojo.RequestInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.UUID;

/**
 * MQ发送消息公用工具类
 * <p>
 * MQ发送消息模式采用 通配符模式
 * order.* 区配一个词
 * order.# 区配一个或者多个词
 * <p>
 *
 * @author gblfy
 */
@Component
@Slf4j
public class MQSendMsgUtils {

    //格式化时间
    public static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// 日期格式
    //引入json工具类
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired//注入发送消息模板
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送MQ消息公用类
     *
     * @param mFisCallingTrace 轨迹保存的数据对象
     * @param type             路由routingKey 字符串拼接
     * @param reqXml           请求报文
     * @param resXml           响应报文
     * @param uuid             随机生成的uuid
     */
    public void sendMsg(FisCallingTrace mFisCallingTrace, String type, String reqXml, String resXml, String uuid) {

        try {
            RequestInfo requestInfo = new RequestInfo().builder()
                    .fisCallingTrace(mFisCallingTrace)
                    .mReqXml(reqXml)
                    .mResXml(resXml)
                    .mUUID(uuid)
                    .serviceName(mFisCallingTrace.getServicename())
                    .type(type)
                    .build();

            //发送消息到MQ的交换机，通知其他系统
            String jsonStr = JSON.toJSONString(requestInfo);

            rabbitTemplate.convertAndSend("order." + type, jsonStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送MQ消息公用类
     *
     * @param serviceName 接口名称
     * @param type        路由routingKey
     */
    public void sendMsg(String type, FisCallingTrace fisCallingTrace) {
        try {


            String uuid2 = UUID.randomUUID().toString();
            CorrelationData correlationId = new CorrelationData(uuid2);

            rabbitTemplate.convertAndSend("order." + type, fisCallingTrace, correlationId);
        } catch (Exception e) {
            e.printStackTrace();
        }
//    public void sendMsg(String type, Object message, Map<String, Object> properties) {
//        try {
//
//            MessageHeaders mhs = new MessageHeaders(properties);
//            Message msg = MessageBuilder.createMessage(message, mhs);
//
//            String uuid2 = UUID.randomUUID().toString();
//            CorrelationData correlationId = new CorrelationData(uuid2);
//
//            rabbitTemplate.convertAndSend("order." + type, msg, correlationId);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    public void sendMsg(String serviceName, String type) {
//        try {
//            //发送消息到MQ的交换机，通知其他系统
//            Map<String, Object> msg = new HashMap<String, Object>();
//            msg.put("serviceName", serviceName);
//            msg.put("routingKey", type);
//            msg.put("currentDate", dateFormat.format(new Date()));
//
//            String uuid2 = UUID.randomUUID().toString();
//            CorrelationData correlationId = new CorrelationData(uuid2);
//
//            rabbitTemplate.convertAndSend("order." + type, (Object) MAPPER.writeValueAsString(msg),correlationId);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

//    public static void main(String[] args) {
//        //使用fastjson 实体类对象转jsonStr
//        User ly = new User().builder()
//                .id(1)
//                .name("ly")
//                .build();
//        String jsonStr = JSON.toJSONString(ly);
//        log.info("转换后jsonStr的用户:" + jsonStr);
//
//        //使用fastjson 进行jsonObject转实体类对象
//        String userString = "{\"id\":1,\"name\":\"ly\"}";
//
//        JSONObject userJson = JSONObject.parseObject(userString);
//        User user = JSON.toJavaObject(userJson, User.class);
//
//        log.info("用户姓名:" + user.getName());
//        log.info("用户ID:" + user.getId());
//    }
}
