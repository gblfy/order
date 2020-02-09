package com.gblfy.order.controller;

import com.gblfy.order.utils.MQSendMsgUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class FisStrQueueMsgSenderController {

    @Autowired
    private MQSendMsgUtils MQSendMsgUtils;

    /**
     * 模拟发送MQ STRING类型消息
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/snedStrMQMsg", method = RequestMethod.GET)
    @ResponseBody
    public String snedStrMQMsg() {

        //type 路由routingKey
        String type = "user";

        //MQ STRING类型消息模拟
        String msg = "MQ message is 123456";

        //发送消息到MQ的交换机，通知其他系统
        MQSendMsgUtils.snedStrMQMsg(type, msg);

        return "snedStrMQMsg success !!!";
    }

}
