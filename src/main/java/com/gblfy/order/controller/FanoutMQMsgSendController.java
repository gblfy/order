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
public class FanoutMQMsgSendController {

    @Autowired
    private MQSendMsgUtils mqSendMsgUtils;

    @RequestMapping(value = "/snedUserMQMsg", method = RequestMethod.GET)
    @ResponseBody
    public String snedUserMQMsg() {

        /**
         * 模拟发送数据
         * 1. serviceName 接口名称
         * 2. type        路由routingKey
         */
        String serviceName = "my name serviceName";
        String type = "user";

        //发送消息到MQ的交换机，通知其他系统
        mqSendMsgUtils.sendMsg(serviceName, type);

        return "snedUserMQMsg success !!!";
    }
    @RequestMapping(value = "/sendMenuMQMsg", method = RequestMethod.GET)
    @ResponseBody
    public String sendMenuMQMsg() {

        /**
         * 模拟发送数据
         * 1. serviceName 接口名称
         * 2. type        路由routingKey
         */
        String serviceName = "my name serviceName";
        String type = "menu";

        //发送消息到MQ的交换机，通知其他系统
        mqSendMsgUtils.sendMsg(serviceName, type);

        return "sendMenuMQMsg success !!!";
    }

    @RequestMapping(value = "/snedCategoryMQMsg", method = RequestMethod.GET)
    @ResponseBody
    public String snedCategoryMQMsg() {

        /**
         * 模拟发送数据
         * 1. serviceName 接口名称
         * 2. type        路由routingKey
         */
        String serviceName = "my name serviceName2";
        String type = "category.gblfy";

        //发送消息到MQ的交换机，通知其他系统
        mqSendMsgUtils.sendMsg(serviceName, type);

        return "snedCategoryMQMsg success !!!";
    }

    public static void main(String[] args) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(dateFormat.format(new Date()));
    }
}
