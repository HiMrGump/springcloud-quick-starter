package com.project.common.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {
    SUCCESS("200","请求成功"),
    PARAM_ERROR("400","参数错误"),
    UNAUTHORIZED("403","未授权"),
    SYSTEM_ERROR("500","系统异常"),
    SERVICE_DOWN_ERROR("501","服务降级"),
    ;
    //状态码
    private String code;
    //返回描述
    private String msg;
}
