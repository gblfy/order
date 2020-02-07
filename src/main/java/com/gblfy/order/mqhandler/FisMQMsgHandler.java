package com.gblfy.order.mqhandler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gblfy.order.pojo.FisCallingTrace;
import com.gblfy.order.pojo.RequestInfo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FisMQMsgHandler {

    /**
     * 接收MQ消息,保存轨迹
     *
     * @param msg
     */
    public void execute(String msg) {
        try {
            //通过 判断路由routingKey是否等于trace相同即可
            //fastjson解析MQ接收的json字符串 转换成RequestInfo对象
            JSONObject jsonObject = JSON.parseObject(msg);
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

        } catch (Exception e) {
            log.info("如果对象中没有，指定的元素,一般会导致空指针异常！！！");
            e.printStackTrace();
        }
    }
}

