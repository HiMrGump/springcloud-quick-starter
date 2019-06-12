package com.project.auth.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtils {

    /**
     * 校验字符串是否存在中文
     */
    public static boolean isHasChineseCharacter(String str) {
        String regEx = "[\\u4e00-\\u9fa5]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 校验字符串是否是指定时间格式
     */
    public static boolean isTimeFormat(String str) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleDateFormat4 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            if (str.contains("-") && str.length() == 10) {
                simpleDateFormat.parse(str);
                return true;
            } else if (str.contains("/") && str.length() == 10) {
                simpleDateFormat2.parse(str);
                return true;
            } else if (str.contains("-") && str.contains(":") && str.length() == 19 && str.charAt(10) == ' ') {
                simpleDateFormat3.parse(str);
                return true;
            } else if (str.contains("/") && str.contains(":") && str.length() == 19 && str.charAt(10) == ' ') {
                simpleDateFormat4.parse(str);
                return true;
            }

        } catch (ParseException e) {
//			e.printStackTrace();
        }
        return false;

    }

    /**
     * 校验字符串是否是金钱
     */
    public static boolean isMoney(String str) {
        Pattern p = null;// 正则表达式
        Matcher m = null;// 操作符表达式
        boolean b = false;
        p = Pattern.compile("^([1-9]{1}[0-9]{0,}(\\.[0-9]{0,2})?|0(\\.[0-9]{0,2})?|\\.[0-9]{1,2})$");
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    /**
     * 校验是否是车牌号
     */
    public static boolean isCarNum(String str) {
        Pattern p = null;// 正则表达式
        Matcher m = null;// 操作符表达式
        boolean b = false;
        p = Pattern.compile("^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$");
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    /**
     * 校验是否邮箱
     */
    public static boolean isEmail(String str) {
        String patternStr = "[\\w]+(@){1}[a-z0-9]+((\\.){1}[a-z]+){1,2}";
        Pattern p = Pattern.compile(patternStr);// 正则表达式
        Matcher m = p.matcher(str);// 操作符表达式
        boolean b = m.matches();
        return b;
    }

    /**
     * 校验smtp服务器格式
     */
    public static boolean isSmtp(String str) {
        String patternStr = "(smtp\\.){1}[a-z0-9]+((\\.){1}[a-z]+){1,2}";
        Pattern p = Pattern.compile(patternStr); // 正则表达式
        Matcher m = p.matcher(str); // 操作符表达式
        boolean b = m.matches();
        return b;
    }

    public static boolean isPhone(String str) {
        String patternStr = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        Pattern p = Pattern.compile(patternStr); // 正则表达式
        Matcher m = p.matcher(str); // 操作符表达式
        boolean b = m.matches();
        if (!b) {
            b = isHKPhone(str);
        }
        return b;
    }

    public static boolean isHKPhone(String str) {
        String patternStr = "^(5|6|8|9)\\d{7}$";
        Pattern p = Pattern.compile(patternStr);// 正则表达式
        Matcher m = p.matcher(str);// 操作符表达式
        boolean b = m.matches();
        return b;
    }

}
