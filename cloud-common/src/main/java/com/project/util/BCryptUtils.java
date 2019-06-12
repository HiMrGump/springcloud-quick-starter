package com.project.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 该类实现了XXXX相关操作接口的具体功能
 *
 * @ClassName: BCryptUtil
 * @Author: WangQingYun
 * @Date: Created in 2019/6/9 16:37
 * @Version: 1.0
 */
public class BCryptUtils {

    public static String encode(String password){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(password);
    }
}
