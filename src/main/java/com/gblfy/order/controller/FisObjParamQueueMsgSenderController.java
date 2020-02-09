//package com.gblfy.order.controller;
//
//import com.gblfy.order.pojo.FisCallingTrace;
//import com.gblfy.order.utils.MQSendMsgUtils;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.UUID;
//
//@Controller
//@Slf4j
//public class FisObjParamQueueMsgSenderController {
//
//    @Autowired
//    private MQSendMsgUtils mqSendMsgUtils;
//    //格式化时间
//    public static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// 日期格式
//    public static final DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");// 日期格式
//
//    @RequestMapping(value = "/snedStrMQMsg", method = RequestMethod.GET)
//    @ResponseBody
//    public String snedStrMQMsg() throws Exception {
//
//        /**
//         * 模拟发送数据
//         * 1. serviceName 接口名称
//         * 2. type        路由routingKey
//         */
//        String type = "user";
//        String msg = "MQ message is 123456";
//
//        //发送消息到MQ的交换机，通知其他系统
//        mqSendMsgUtils.snedStrMQMsg(type, msg);
//
//        return "snedUserMQMsg success !!!";
//    }
//
//    @RequestMapping(value = "/snedTraceMQMsg", method = RequestMethod.GET)
//    @ResponseBody
//    public String snedTraceMQMsg() throws Exception {
//
//        Date tStartDate = new Date();// 记录转发结束时间
//        Date tEndDate = new Date();// 记录转发结束时间
//
//        //模拟请求和响应报文
//        String reqXml = "my name is reqXml";
//        String resXml = "my name is resXml";
//        String uuid = UUID.randomUUID().toString();
//
//        //模拟 轨迹储存数据
//        FisCallingTrace mFisCallingTrace = new FisCallingTrace().builder()
//                .servicename("myServiceNme is A")
//                .servicetype("2")
//                .interfacetype("2")
//                .resstatus("1")
//                .resremark("1")
//                .reqdate(dateFormat.parse(dateFormat.format(tStartDate)))
//                .reqtime(timeFormat.format(tStartDate))
//                .resdate(dateFormat.parse(dateFormat.format(tEndDate)))
//                .restime(timeFormat.format(tEndDate))
//                .reqxml("")
//                .resxml("")
//                .build();
//
//        //定义路由routingKey
//        String routingKey = "trace";
//
//        //调用MQ松松消息公共方法
//        mqSendMsgUtils.sendObjMQMsg(mFisCallingTrace, routingKey, reqXml, resXml, uuid);
//
//
//        return "snedUserMQMsg success !!!";
//    }
//
////    @RequestMapping(value = "/sendMenuMQMsg", method = RequestMethod.GET)
////    @ResponseBody
////    public String sendMenuMQMsg() {
////
////        /**
////         * 模拟发送数据
////         * 1. serviceName 接口名称
////         * 2. type        路由routingKey
////         */
////        String serviceName = "my name serviceName";
////        String type = "menu";
////
////        //发送消息到MQ的交换机，通知其他系统
////        mqSendMsgUtils.sendMsg(serviceName, type);
////
////        return "sendMenuMQMsg success !!!";
////    }
////
////    @RequestMapping(value = "/snedCategoryMQMsg", method = RequestMethod.GET)
////    @ResponseBody
////    public String snedCategoryMQMsg() {
////
////        /**
////         * 模拟发送数据
////         * 1. serviceName 接口名称
////         * 2. type        路由routingKey
////         */
////        String serviceName = "my name serviceName2";
////        String type = "category.gblfy";
////
////        //发送消息到MQ的交换机，通知其他系统
////        mqSendMsgUtils.sendMsg(serviceName, type);
////
////        return "snedCategoryMQMsg success !!!";
////    }
//
//    public static void main(String[] args) {
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        System.out.println(dateFormat.format(new Date()));
//    }
//}
