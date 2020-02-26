package com.gblfy.order.mqhandler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gblfy.order.pojo.FisCallingTrace;
import com.gblfy.order.pojo.RequestInfo;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FisTraceObjParamMsgHandler implements ChannelAwareMessageListener {

    /**
     * 接收MQ ObjPOJO类型消息+额外参数
     *
     * @param message    消息实体
     * @param channel    消息通道
     * @throws Exception 抛出异常
     */
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {

        //接收MQ消息 轨迹对象数据
        String jsonMsg = new String(message.getBody(),"UTF-8");

        // 消息的标识，false只确认当前一个消息收到，true确认所有consumer获得的消息
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

        //通过 判断路由routingKey是否等于trace相同即可
        //fastjson解析MQ接收的json字符串 转换成RequestInfo对象
        JSONObject jsonObject = JSON.parseObject(jsonMsg);
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

        //把MQ接收消息的数据进行 保存轨迹数据库操作 todo
        //注入mqpper 插入数据库 todo
    }
}


/**
 * 4种消息确认方式:
 * <p>
 * 1. 消息的标识，false只确认当前一个消息收到，true确认所有consumer获得的消息
 * channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
 * 2. ack返回false，并重新回到队列，api里面解释得很清楚
 * channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
 * 3. 丢弃这条消息
 * channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
 * 4. 拒绝消息
 * channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
 */
