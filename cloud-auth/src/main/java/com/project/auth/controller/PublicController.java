package com.project.auth.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.protobuf.ServiceException;
import com.project.auth.security.MyWebResponseExceptionTranslator;
import com.project.auth.util.ValidateCodeUtils;
import com.project.util.ResponseResult;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 公开接口
 *
 * @ClassName: PublicController
 * @Author: WangQingYun
 * @Date: Created in 2019/5/30 10:41
 * @Version: 1.0
 */
@RestController
@RequestMapping("/public")
public class PublicController {

  /*  @Autowired
    private StringRedisTemplate redisTemplate;*/

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Resource
    MyWebResponseExceptionTranslator webResponseExceptionTranslator;

    /**
     * 发送短信
     * @param phone 手机号码
     * @return
     */
    @RequestMapping("/sendSMS/{phone}")
    public ResponseResult sendSMS(@PathVariable("phone") String phone){
        String code = RandomStringUtils.randomNumeric(6);
       // redisTemplate.opsForValue().set(RedisPrefixConstant.SEND_SMS + phone,code,60, TimeUnit.SECONDS);
        return ResponseResult.success(code);
    }

    /**
     * 发送验证码
     * @param username
     * @param response
     * @throws IOException
     */
    @RequestMapping("/kaptcha")
    public void kaptcha(@RequestParam(value = "username",required = true) String username,@RequestParam(value = "vc",required = false) String vc, HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        if(StringUtils.isBlank(vc)){
            vc = ValidateCodeUtils.createText();
            webResponseExceptionTranslator.flushCode(username,vc);
        }
        try(ServletOutputStream output = response.getOutputStream()) {
            ValidateCodeUtils.writeImage(output,vc);
        }
    }
}
