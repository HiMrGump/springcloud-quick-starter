package com.project.util;


import org.apache.commons.lang3.StringUtils;

/**
 * 该类实现了手机号正则验证
 *
 * @ClassName: PhoneUtil
 * @Author: WangQingYun
 * @Date: Created in 2019/5/16 9:32
 * @Version: 1.0
 */
public class PhoneUtil {

    static String MOBILE_REGEX = "^1\\d{10}$"; //手机号正则
    static String TEL_REGEX = "^\\d{3}-\\d{8}|\\d{4}-\\d{7}$"; //座机正则
    //正则判断是否是手机号
    public static boolean isPhone(String phone){
        if(StringUtils.isBlank(phone)){
            return false;
        }
        return phone.matches(MOBILE_REGEX) || phone.matches(TEL_REGEX);
    }

    //正则判断是否是手机号
    public static boolean isMobile(String mobile){
        if(StringUtils.isBlank(mobile)){
            return false;
        }
        return mobile.matches(MOBILE_REGEX);
    }
}
