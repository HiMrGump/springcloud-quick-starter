package com.project.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {
    SUCCESS("200","请求成功"),
    PARAM_ERROR("400","参数错误"),
    UNAUTHORIZED("403","未授权"),
    NOT_FOUND("404","未找到接口"),
    SYSTEM_ERROR("500","系统异常"),
    SERVICE_DOWNGRADE_ERROR("501","服务降级"),
    SERVICE_OFF_ERROR("504","服务下架"),
    ;
    //状态码
    private String code;
    //返回描述
    private String msg;
}
