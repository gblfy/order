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

@Controller
@Slf4j
public class TopicMQMsgSendController {

    @Autowired
    private MQSendMsgUtils mqSendMsgUtils;
    //格式化时间
    public static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// 日期格式
    public static final DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");// 日期格式

    @RequestMapping(value = "/snedUserMQMsg", method = RequestMethod.GET)
    @ResponseBody
    public String snedUserMQMsg()throws Exception {

        /**
         * 模拟发送数据
         * 1. serviceName 接口名称
         * 2. type        路由routingKey
         */
        Date tStartDate = new Date();
        Date tEndDate = new Date();
        String serviceName = "my name serviceName";
        String type = "user";
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
        //发送消息到MQ的交换机，通知其他系统
        mqSendMsgUtils.sendMsg(type, mFisCallingTrace);

        return "snedUserMQMsg success !!!";
    }

//    @RequestMapping(value = "/sendMenuMQMsg", method = RequestMethod.GET)
//    @ResponseBody
//    public String sendMenuMQMsg() {
//
//        /**
//         * 模拟发送数据
//         * 1. serviceName 接口名称
//         * 2. type        路由routingKey
//         */
//        String serviceName = "my name serviceName";
//        String type = "menu";
//
//        //发送消息到MQ的交换机，通知其他系统
//        mqSendMsgUtils.sendMsg(serviceName, type);
//
//        return "sendMenuMQMsg success !!!";
//    }
//
//    @RequestMapping(value = "/snedCategoryMQMsg", method = RequestMethod.GET)
//    @ResponseBody
//    public String snedCategoryMQMsg() {
//
//        /**
//         * 模拟发送数据
//         * 1. serviceName 接口名称
//         * 2. type        路由routingKey
//         */
//        String serviceName = "my name serviceName2";
//        String type = "category.gblfy";
//
//        //发送消息到MQ的交换机，通知其他系统
//        mqSendMsgUtils.sendMsg(serviceName, type);
//
//        return "snedCategoryMQMsg success !!!";
//    }

    public static void main(String[] args) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(dateFormat.format(new Date()));
    }
}
