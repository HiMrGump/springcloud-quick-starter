package com.project.util;

import com.alibaba.fastjson.JSONObject;

/**
 * 该类实现了XXXX相关操作接口的具体功能
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
