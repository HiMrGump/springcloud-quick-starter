package com.project.util;

import com.alibaba.fastjson.JSONObject;

/**
 * bean深复制工具
 *
 * @ClassName: BeanUtils
 * @Author: WangQingYun
 * @Date: Created in 2019/6/13 10:40
 * @Version: 1.0
 */
public class BeanUtils {

    public static <T> T copyBean(Object source,Class<T> clazz){
        JSONObject jsonObject = (JSONObject)JSONObject.toJSON(source);
        return jsonObject.toJavaObject(clazz);
    }
}
