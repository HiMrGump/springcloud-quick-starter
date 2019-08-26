package com.project.util;

import java.util.Arrays;

/**
 * 定义了一些简单的工具
 * @ClassName: SchedulerUtil
 * @Author: WangQingYun
 * @Date: Created in 2019/4/22 16:45
 * @Version: 1.0
 */
public class SimpleUtil {

    /**
     * 判断一个Long值必须存在且大于0
     * @param num 要判断的数字
     * @return
     */
    public static boolean isThanZero(Long num){
        if(num != null && num > 0){
            return true;
        }
        return false;
    }

    /**
     * 判断一个Integer值必须存在且大于0
     * @param num 要判断的数字
     * @return
     */
    public static boolean isThanZero(Integer num){
        return num == null ? false : isThanZero(Long.valueOf(num));
    }

    /**
     * 若source在compareArr中出现，返回true
     * @param source 源
     * @param compareArr 要比较的数组
     * @return true-出现  false-没出现
     */
    public static boolean in(Integer source,Integer...compareArr){
        if(source == null || compareArr == null){ //二者为空,不作比较
            return false;
        }
        return Arrays.stream(compareArr).anyMatch(e -> e.equals(source));
    }

    /**
     * 若source在compareArr中出现，返回true
     * @param source 源
     * @param compareArr 要比较的数组
     * @return true-出现  false-没出现
     */
    public static boolean in(Long source,Long[] compareArr){
        if(source == null || compareArr == null){ //二者为空,不作比较
            return false;
        }
        return Arrays.stream(compareArr).anyMatch(e -> e.equals(source));
    }
}
