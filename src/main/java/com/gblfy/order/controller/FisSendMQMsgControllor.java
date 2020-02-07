package com.gblfy.order.controller;

import com.gblfy.order.pojo.FisCallingTrace;
import com.gblfy.order.utils.MQSendMsgUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
@Slf4j
public class FisSendMQMsgControllor {

    public static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// 日期格式
    public static final DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");// 日期格式

    @Autowired
    private MQSendMsgUtils mqSendMsgUtils;

    /**
     * 发送轨迹数据 MQ异步存储轨迹
     *
     * @return
     */
    @RequestMapping(value = "/sendMQObjMsg2", method = RequestMethod.GET)
    @ResponseBody
    public String sendObj() throws Exception {

        Date tStartDate = new Date();// 记录转发结束时间
        Date tEndDate = new Date();// 记录转发结束时间

        //模拟请求和响应报文
        String reqXml = "my name is reqXml";
        String resXml = "my name is resXml";
        String uuid = UUID.randomUUID().toString();

        //模拟 轨迹储存数据
        FisCallingTrace mFisCallingTrace = new FisCallingTrace().builder()
                .servicename("myServiceNme is A")
                .servicetype("2")
                .interfacetype("2")
                .resstatus("1")
                .resremark("1")
                .reqdate(dateFormat.parse(dateFormat.format(tStartDate)))
                .reqtime(timeFormat.format(tStartDate))
                .resdate(dateFormat.parse(dateFormat.format(tEndDate)))
                .restime(timeFormat.format(tEndDate))
                .reqxml("")
                .resxml("")
                .build();

        //定义路由routingKey
        String routingKey = "trace2";

        //调用MQ松松消息公共方法
        mqSendMsgUtils.sendMsg(mFisCallingTrace, routingKey, reqXml, resXml, uuid);

        return "send sendMQObjMsg success !!!";
    }
}
