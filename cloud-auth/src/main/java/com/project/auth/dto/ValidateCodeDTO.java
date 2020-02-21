package com.project.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 验证码认证和错误实体
 *
 * @Author Gump
 * @Date 2020/2/2114:56
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidateCodeDTO {

    //错误次数
    private int error;
    //验证码
    private String vc;
    //有效期，秒
    private int expire;
}
