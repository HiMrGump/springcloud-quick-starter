package com.project.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * 该类定义了controller统一返回的的状态码和描述
 *
 * @ClassName: RedisPrefixConstant
 * @Author: WangQingYun
 * @Date: Created in 2019/6/21 14:43
 * @Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum ResultCode {
    LOGIN_NOTFOUND("100","找不到用户"),
    LOGIN_DONTSHOW_VALIDATE("101","密码错误"),
    LOGIN_NOTCOUNT_SHOW_VALIDATE("102","找不到用户,显示验证码"),
    LOGIN_PASSWORDERROR_SHOW_VALIDATE("103","密码超出最大错误次数,显示验证码"),
    LOGIN_VALIDATE_ERROR("104","验证码错误"),

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
