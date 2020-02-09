package com.gblfy.order.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 轨迹保存数据对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FisCallingTrace implements Serializable {

    private String traceId;
    private String servicename;
    private String servicetype;
    private String interfacetype;
    private String resstatus;
    private String resremark;
    private Date reqdate;
    private String reqtime;
    private Date resdate;
    private String restime;
    private String reqxml;
    private String resxml;
}
