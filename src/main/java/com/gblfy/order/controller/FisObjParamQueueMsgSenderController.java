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
public class FisObjParamQueueMsgSenderController {

    @Autowired
    private MQSendMsgUtils mQSendMsgUtils;

    //格式化时间
    public static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// 日期格式
    public static final DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");// 日期格式

    /**
     * 发送MQ ObjPOJO类型消息+额外参数2
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/snedObjPOJOMQMsgAndParam", method = RequestMethod.GET)
    @ResponseBody
    public String snedObjPOJOMQMsgAndParam() throws Exception {

        //模拟请求和响应报文
        String reqXml = "my name is reqXml";
        String resXml = "my name is resXml";
        //生成uuid作为轨迹ID 高并发建议使用雪花算法 分布式ID
        String uuid = UUID.randomUUID().toString();

        //模拟 封装轨迹储存数据进行发送
        FisCallingTrace mFisCallingTrace = encapsulatePOJOData();

        //定义路由routingKey
        String routingKey = "trace";

        //调用MQ松松消息公共方法
        mQSendMsgUtils.snedObjPOJOMQMsgAndParam(mFisCallingTrace, routingKey, reqXml, resXml, uuid);


        return "snedObjPOJOMQMsgAndParam success !!!";
    }

    /**
     * 发送MQ ObjPOJO类型消息+额外参数2
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/snedObjPOJOMQMsgAndParamT", method = RequestMethod.GET)
    @ResponseBody
    public String snedObjPOJOMQMsgAndParamT() throws Exception {

        //模拟请求和响应报文
        String reqXml = "my name is reqXml";
        String resXml = "my name is resXml";
        //生成uuid作为轨迹ID 高并发建议使用雪花算法 分布式ID
        String uuid = UUID.randomUUID().toString();

        //模拟 封装轨迹储存数据进行发送
        FisCallingTrace mFisCallingTrace = encapsulatePOJOData();

        //定义路由routingKey
        String routingKey = "trace";

        //调用MQ松松消息公共方法
        mQSendMsgUtils.snedObjPOJOMQMsgAndParamT(mFisCallingTrace, routingKey, reqXml, resXml, uuid);


        return "snedObjPOJOMQMsgAndParamT success !!!";
    }

    /**
     * 模拟组装POJO对象数据
     *
     * @throws Exception
     */
    public FisCallingTrace encapsulatePOJOData() throws Exception {
        Date tStartDate = new Date();// 记录转发结束时间
        Date tEndDate = new Date();// 记录转发结束时间
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
        return mFisCallingTrace;
    }
}
