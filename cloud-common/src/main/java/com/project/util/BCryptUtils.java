package com.project.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * BCrypt工具类
 *
 * @ClassName: BCryptUtils
 * @Author: WangQingYun
 * @Date: Created in 2019/6/9 16:37
 * @Version: 1.0
 */
public class BCryptUtils {

    public static String encode(String password){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(password);
    }

    public static void main(String[] args) {
        System.out.println(encode("123456"));
    }
}
