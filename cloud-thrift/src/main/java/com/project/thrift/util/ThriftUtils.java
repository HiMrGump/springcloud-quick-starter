package com.project.thrift.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.project.common.entity.ResultCode;
import com.project.thrift.entity.ThriftResponseResult;
import org.apache.commons.lang.StringUtils;

import java.util.Optional;

/**
 * 该类实现了XXXX相关操作接口的具体功能
 *
 * @ClassName: ThriftUtils
 * @Author: WangQingYun
 * @Date: Created in 2019/6/15 16:17
 * @Version: 1.0
 */
public class ThriftUtils {

    public static ThriftResponseResult success(Object data){
        ThriftResponseResult thriftResponseResult = new ThriftResponseResult();
        thriftResponseResult.setCode(ResultCode.SUCCESS.getCode());
        thriftResponseResult.setMsg(ResultCode.SUCCESS.getMsg());
        if(data != null){
            thriftResponseResult.setData(JSON.toJSONString(data));
        }
        return thriftResponseResult;
    }

    public static <T> Optional<T> parseObject(ThriftResponseResult thriftResponseResult,Class<T> clazz){
        System.out.println("接受到数据1：" + thriftResponseResult);
        if(thriftResponseResult == null){
            return Optional.empty();
        }
        String data = thriftResponseResult.getData();

        System.out.println("接受到数据2：" + data);
        if(StringUtils.isBlank(data)){
            return Optional.empty();
        }

        return Optional.ofNullable(JSONObject.parseObject(data,clazz));
    }
}
