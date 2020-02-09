package com.gblfy.order.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gblfy.order.pojo.FisCallingTrace;
import com.gblfy.order.pojo.RequestInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * MQ发送 不同类型消息 公用工具类
 * <p>
 * MQ发送消息模式采用 订阅模式(topic)中的通配符模式
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
//    public static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// 日期格式
    //引入json工具类
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired//注入发送消息模板
    private RabbitTemplate rabbitTemplate;


    /**
     * 发送MQ STRING类型消息 第1种
     *
     * @param type 路由routingKey
     * @param msg  MQ STRING类型消息
     */
    public void snedStrMQMsg(String type, String msg) {
        try {
            /**
             * CorrelationData 说明：
             * 1. correlationId 作为生产端和消息绑定消息队列全局唯一标识
             * 2. 当生产端发送的消息无法路由到指定的消息队列时，此种场
             *    景的消息会被生产端会return确认机制监听到，对消息做补
             *    偿机制处理
             */
            String uuidStr = UUID.randomUUID().toString();
            CorrelationData correlationId = new CorrelationData(uuidStr);

            //发送消息到MQ的交换机，通知其他系统
            rabbitTemplate.convertAndSend("fis-str." + type, msg.getBytes(), correlationId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送MQ STRING类型消息+额外属性 第2种
     *
     * @param type 路由routingKey
     * @param msg  MQ STRING类型消息
     */
    public void snedStrMQMsgAndParam(String type, String msg) {
        try {
            /**
             * CorrelationData 说明：
             * 1. correlationId 作为生产端和消息绑定消息队列全局唯一标识
             * 2. 当生产端发送的消息无法路由到指定的消息队列时，此种场
             *    景的消息会被生产端会return确认机制监听到，对消息做补
             *    偿机制处理
             */
            String uuidStr = UUID.randomUUID().toString();
            CorrelationData correlationId = new CorrelationData(uuidStr);


            /**
             * 添加额外参数设置
             */
            MessageProperties messageProperties = new MessageProperties();
            //这里注意一定要修改contentType为 text/plain
            messageProperties.setContentType("text/plain");
            messageProperties.getHeaders().put("desc", "信息描述:测试发送【字符串消息】加上额外参数在设置");
            messageProperties.getHeaders().put("type", "自定义消息类型..");
            Message message = new Message(msg.getBytes(), messageProperties);

            //发送消息到MQ的交换机，通知其他系统
            rabbitTemplate.convertAndSend("fis-str-param." + type, message, correlationId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送MQ STRING类型消息+额外属性+自定义属性 第3种
     *
     * @param type 路由routingKey
     * @param msg  MQ STRING类型消息
     */
    public void snedStrMQMsgAndParamT(String type, String msg) throws Exception {
        try {
            /**
             * CorrelationData 说明：
             * 1. correlationId 作为生产端和消息绑定消息队列全局唯一标识
             * 2. 当生产端发送的消息无法路由到指定的消息队列时，此种场
             *    景的消息会被生产端会return确认机制监听到，对消息做补
             *    偿机制处理
             */
            String uuidStr = UUID.randomUUID().toString();
            CorrelationData correlationId = new CorrelationData(uuidStr);

            /**
             * 添加额外参数设置
             */
            MessageProperties messageProperties = new MessageProperties();
            //这里注意一定要修改contentType为 text/plain
            messageProperties.setContentType("text/plain");
            messageProperties.getHeaders().put("desc", "信息描述:测试发送【字符串消息】加上额外参数在设置");
            messageProperties.getHeaders().put("type", "自定义消息类型..");
            Message message = new Message(msg.getBytes(), messageProperties);

            /**
             * 发送消息到MQ的交换机，通知其他系统
             * API
             * convertAndSend(String routingKey, Object message, MessagePostProcessor messagePostProcessor, CorrelationData correlationData)
             */
            rabbitTemplate.convertAndSend("fis-str-param." + type, message, new MessagePostProcessor() {
                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    System.err.println("------添加额外的设置---------");
                    message.getMessageProperties().getHeaders().put("desc", "额外修改的信息描述");
                    message.getMessageProperties().getHeaders().put("attr", "额外新加的属性");
                    return message;
                }
            }, correlationId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送MQ POJO对象消息公用类
     * <p>
     * 1. POJO 对象实现序列化 implements Serializable
     * 2. 发送消息前，对消息进行 jsonStr 类型 转换处理
     * </p>
     *
     * @param mFisCallingTrace 轨迹保存的数据对象
     * @param type             路由routingKey 字符串拼接
     * @param reqXml           请求报文
     * @param resXml           响应报文
     * @param uuid             随机生成的uuid
     */
    public void snedObjPOJOMQMsg(FisCallingTrace mFisCallingTrace, String type, String reqXml, String resXml, String uuid) {

        try {
            RequestInfo requestInfo = new RequestInfo().builder()
                    .fisCallingTrace(mFisCallingTrace)
                    .mReqXml(reqXml)
                    .mResXml(resXml)
                    .mUUID(uuid)
                    .serviceName(mFisCallingTrace.getServicename())
                    .type(type)
                    .build();

            //把对象转换成 jsonStr 类型便与解析
            String jsonStrObj = MAPPER.writeValueAsString(requestInfo);

            /**
             * CorrelationData 说明：
             * 1. correlationId 作为生产端和消息绑定消息队列全局唯一标识
             * 2. 当生产端发送的消息无法路由到指定的消息队列时，此种场
             *    景的消息会被生产端会return确认机制监听到，对消息做补
             *    偿机制处理
             */
            String uuidStr = UUID.randomUUID().toString();
            CorrelationData correlationId = new CorrelationData(uuidStr);

            //发送消息到MQ的交换机，通知其他系统
            rabbitTemplate.convertAndSend("fis-trace-obj." + type, jsonStrObj.getBytes(), correlationId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送MQ POJO对象消息+自定义参数 公用类
     * <p>
     * 1. POJO 对象实现序列化 implements Serializable
     * 2. 发送消息前，对消息进行 jsonStr 类型 转换处理
     * </p>
     *
     * @param mFisCallingTrace 轨迹保存的数据对象
     * @param type             路由routingKey 字符串拼接
     * @param reqXml           请求报文
     * @param resXml           响应报文
     * @param uuid             随机生成的uuid
     */
    public void snedObjPOJOMQMsgAndParam(FisCallingTrace mFisCallingTrace, String type, String reqXml, String resXml, String uuid) {

        try {
            RequestInfo requestInfo = new RequestInfo().builder()
                    .fisCallingTrace(mFisCallingTrace)
                    .mReqXml(reqXml)
                    .mResXml(resXml)
                    .mUUID(uuid)
                    .serviceName(mFisCallingTrace.getServicename())
                    .type(type)
                    .build();

            //把对象转换成 jsonStr 类型便与解析
            String jsonStrObj = MAPPER.writeValueAsString(requestInfo);

            /**
             * CorrelationData 说明：
             * 1. correlationId 作为生产端和消息绑定消息队列全局唯一标识
             * 2. 当生产端发送的消息无法路由到指定的消息队列时，此种场
             *    景的消息会被生产端会return确认机制监听到，对消息做补
             *    偿机制处理
             */
            String uuidStr = UUID.randomUUID().toString();
            CorrelationData correlationId = new CorrelationData(uuidStr);

            /**
             * 添加额外参数设置
             */
            MessageProperties messageProperties = new MessageProperties();
            //这里注意一定要修改contentType为 application/json
            messageProperties.setContentType("application/json");
            messageProperties.getHeaders().put("desc", "信息描述:测试发送对象加上额外参数在设置");
            messageProperties.getHeaders().put("type", "自定义消息类型..");
            Message message = new Message(jsonStrObj.getBytes(), messageProperties);

            //发送消息到MQ的交换机，通知其他系统
            rabbitTemplate.convertAndSend("fis-trace-obj-param." + type, message, correlationId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送MQ POJO对象消息+自定义参数 公用类3
     * <p>
     * 1. POJO 对象实现序列化 implements Serializable
     * 2. 发送消息前，对消息进行 jsonStr 类型 转换处理
     * </p>
     *
     * @param mFisCallingTrace 轨迹保存的数据对象
     * @param type             路由routingKey 字符串拼接
     * @param reqXml           请求报文
     * @param resXml           响应报文
     * @param uuid             随机生成的uuid
     */
    public void snedObjPOJOMQMsgAndParamT(FisCallingTrace mFisCallingTrace, String type, String reqXml, String resXml, String uuid) {

        try {
            RequestInfo requestInfo = new RequestInfo().builder()
                    .fisCallingTrace(mFisCallingTrace)
                    .mReqXml(reqXml)
                    .mResXml(resXml)
                    .mUUID(uuid)
                    .serviceName(mFisCallingTrace.getServicename())
                    .type(type)
                    .build();

            //把对象转换成 jsonStr 类型便与解析
            String jsonStrObj = MAPPER.writeValueAsString(requestInfo);

            /**
             * CorrelationData 说明：
             * 1. correlationId 作为生产端和消息绑定消息队列全局唯一标识
             * 2. 当生产端发送的消息无法路由到指定的消息队列时，此种场
             *    景的消息会被生产端会return确认机制监听到，对消息做补
             *    偿机制处理
             */
            String uuidStr = UUID.randomUUID().toString();
            CorrelationData correlationId = new CorrelationData(uuidStr);

            /**
             * 添加额外参数设置
             */
            MessageProperties messageProperties = new MessageProperties();
            //这里注意一定要修改contentType为 application/json
            messageProperties.setContentType("application/json");
            messageProperties.getHeaders().put("desc", "信息描述:测试发送对象加上额外参数在设置");
            messageProperties.getHeaders().put("type", "自定义消息类型..");
            Message message = new Message(jsonStrObj.getBytes(), messageProperties);

            //发送消息到MQ的交换机，通知其他系统
            rabbitTemplate.convertAndSend("fis-trace-obj-param." + type, message, new MessagePostProcessor() {
                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    System.err.println("------添加额外的设置---------");
                    message.getMessageProperties().getHeaders().put("desc", "额外修改的信息描述");
                    message.getMessageProperties().getHeaders().put("attr", "额外新加的属性");
                    return message;
                }
            }, correlationId);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
