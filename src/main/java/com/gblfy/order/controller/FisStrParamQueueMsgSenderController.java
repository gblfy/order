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

@Controller
@Slf4j
public class FisStrParamQueueMsgSenderController {

    @Autowired
    private MQSendMsgUtils mQSendMsgUtils;
    //格式化时间
    public static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// 日期格式
    public static final DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");// 日期格式

    /**
     * 发送MQ STRING类型消息+额外参数
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/snedStrMQMsgAndParam", method = RequestMethod.GET)
    @ResponseBody
    public String snedStrMQMsgAndParam() {

        //type 路由routingKey
        String type = "user";

        //MQ STRING类型消息模拟
        String msg = "MQ message is 123456";

        //发送消息到MQ的交换机，通知其他系统
        mQSendMsgUtils.snedStrMQMsgAndParam(type, msg);

        return "snedStrMQMsgAndParam success !!!";
    }
    /**
     * 模拟发送MQ STRING类型消息+额外参数2
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/snedStrMQMsgAndParamT", method = RequestMethod.GET)
    @ResponseBody
    public String snedStrMQMsgAndParamT() throws Exception{

        //type 路由routingKey
        String type = "user";

        //MQ STRING类型消息模拟
        String msg = "MQ message is 123456";

        //发送消息到MQ的交换机，通知其他系统
        mQSendMsgUtils.snedStrMQMsgAndParamT(type, msg);

        return "snedStrMQMsgAndParamT success !!!";
    }

}
