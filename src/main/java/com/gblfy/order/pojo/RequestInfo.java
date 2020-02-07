package com.gblfy.order.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 轨迹保存数据对象封装类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestInfo {
    private FisCallingTrace fisCallingTrace;
    private String mReqXml; // 请求报文
    private String mResXml; // 响应报文
    private String mUUID;  // 请求ID
    private String serviceName;//服务名称
    private String type;//路由
}
