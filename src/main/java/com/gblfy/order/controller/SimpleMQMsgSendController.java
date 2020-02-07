package com.gblfy.order.controller;

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

@Controller
@Slf4j
public class SimpleMQMsgSendController {

    @Autowired
    private MQSendMsgUtils mqSendMsgUtils;

    @RequestMapping(value = "/sendSimpleMQMsg", method = RequestMethod.GET)
    @ResponseBody
    public String snedSimpleMQMsg() {

        /**
         * 模拟发送数据
         * 1. serviceName 接口名称
         * 2. type        路由routingKey
         */
        String serviceName = "my name serviceName";
        String type = "menu";

        //发送消息到MQ的交换机，通知其他系统
        mqSendMsgUtils.sendMsg(serviceName, type);

        return "snedSimpleMQMsg success !!!";
    }

    @RequestMapping(value = "/sendSimpleMQMsg2", method = RequestMethod.GET)
    @ResponseBody
    public String snedSimpleMQMsg2() {

        /**
         * 模拟发送数据
         * 1. serviceName 接口名称
         * 2. type        路由routingKey
         */
        String serviceName = "my name serviceName2";
        String type = "category";

        //发送消息到MQ的交换机，通知其他系统
        mqSendMsgUtils.sendMsg(serviceName, type);

        return "snedSimpleMQMsg success !!!";
    }

    public static void main(String[] args) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(dateFormat.format(new Date()));
    }
}
