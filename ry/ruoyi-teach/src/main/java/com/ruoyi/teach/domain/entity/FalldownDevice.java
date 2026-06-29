package com.ruoyi.teach.domain.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;

@Data
public class FalldownDevice {
    @JsonProperty("Id")
    private Long id;

    @JsonProperty("DeviceCode")
    @Excel(name = "设备编号")
    private String deviceCode;

    @JsonProperty("Status")
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    @JsonProperty("Model")
    @Excel(name = "设备型号")
    private String model;

    @JsonProperty("ContactPhones")
    @Excel(name = "家属电话")
    private String contactPhones;

    @JsonProperty("Phone")
    @Excel(name = "设备电话")
    private String phone;

    @JsonProperty("Flag")
    private String flag;

    @JsonProperty("CreateBy")
    private String createBy;

    @JsonProperty("UpdateBy")
    private String updateBy;

    @JsonProperty("Remark")
    @Excel(name = "备注")
    private String remark;

    @JsonProperty("CreateTime")
    private Date createTime;

    @JsonProperty("UpdateTime")
    private Date updateTime;

    @JsonProperty("DeleteTime")
    private Date deleteTime;
}
