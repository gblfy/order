package com.gblfy.order.mqhandler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gblfy.order.pojo.FisCallingTrace;
import com.gblfy.order.pojo.RequestInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.MessageProperties;

import java.io.IOException;
import java.util.List;

@Slf4j
public class FisMQMonitorMsgHandler implements MessageListener {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 接收MQ消息,保存轨迹 可以获取一些额外的参数
     * <p>
     * 此类需要实现MessageListener接口重写onMessage方法
     * <p>
     * 栗子：FisMQHandler implements MessageListener
     *
     * @param message
     */
    @Override
    public void onMessage(Message message) {
        try {
            // msg就是rabbitmq传来的消息
            // 使用jackson解析
            MessageProperties messageProperties = message.getMessageProperties();

            log.info("编码类型:" + messageProperties.getContentType());
            log.info("编码:" + messageProperties.getContentEncoding());
            log.info("交换机:" + messageProperties.getReceivedExchange());
            log.info("路由:" + messageProperties.getReceivedRoutingKey());
            log.info("额外自定义的属性:" + messageProperties.getHeaders());

            JsonNode jsonData = MAPPER.readTree(message.getBody());
            List<JsonNode> findKeys = jsonData.findParents("fisCallingTrace");
            for (JsonNode result : findKeys) {
                JSONObject jsonObject = JSON.parseObject(result.toString());
                //fastjson解析MQ接收的json字符串 转换成RequestInfo对象
                RequestInfo requestInfo = JSON.toJavaObject(jsonObject, RequestInfo.class);

                log.info("请求报文 mReqXml:" + requestInfo.getMReqXml());
                log.info("响应报文 mResXml:" + requestInfo.getMResXml());
                log.info("接口名称 serviceName:" + requestInfo.getServiceName());
                log.info("路由routingKey:" + requestInfo.getType());
                log.info("生成的 mUUID:" + requestInfo.getMUUID());

                /**
                 * 1.从requestInfo对象中，获取fisCallingTrace轨迹对象
                 * 2.请求报文和响应报文需要添加进去 fisCallingTrace对象中的请求报文和响应报文默认是空字符串
                 * 3.将fisCallingTrace 轨技数据保存数据库
                 */
                FisCallingTrace fisCallingTrace = requestInfo.getFisCallingTrace();
                fisCallingTrace.setTraceId(requestInfo.getMUUID());
                fisCallingTrace.setReqxml(requestInfo.getMReqXml());
                fisCallingTrace.setResxml(requestInfo.getMResXml());

                log.info("从MQ接收消息并封装完成！！！");
                log.info("开始进行插入数据库操作！！！");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

