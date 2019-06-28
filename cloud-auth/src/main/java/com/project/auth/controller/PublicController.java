package com.project.auth.controller;

import com.project.constant.RedisPrefixConstant;
import com.project.util.ResponseResult;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * 该类实现了XXXX相关操作接口的具体功能
 *
 * @ClassName: TestController
 * @Author: WangQingYun
 * @Date: Created in 2019/5/30 10:41
 * @Version: 1.0
 */
@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @RequestMapping("/sendSMS/{phone}")
    public ResponseResult sendSMS(@PathVariable("phone") String phone){
        String code = RandomStringUtils.randomNumeric(6);
        redisTemplate.opsForValue().set(RedisPrefixConstant.SEND_SMS + phone,code,60, TimeUnit.SECONDS);
        return ResponseResult.success(code);
    }

}
