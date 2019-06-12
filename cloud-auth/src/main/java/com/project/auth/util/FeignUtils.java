package com.project.auth.util;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.project.auth.dto.RoleDTO;
import com.project.auth.dto.UserDTO;
import com.project.entity.base.ResultCode;
import com.project.util.ResponseResult;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 该类实现了XXXX相关操作接口的具体功能
 *
 * @ClassName: FeignUtils
 * @Author: WangQingYun
 * @Date: Created in 2019/6/6 15:06
 * @Version: 1.0
 */
public class FeignUtils {
    /**
     * 根据状态码，判断是否降级
     *
     * @param responseResult 返回的Response封装的数据
     * @return true-服务降级,false-请求成功
     */
    public static boolean isRequestFail(ResponseResult responseResult) {
        if (responseResult == null) {
            return true;
        }
        return responseResult.getCode().equals(ResultCode.SERVICE_DOWN_ERROR.getCode());
    }

    public static UserDTO packageUserVO(ResponseResult responseResult){
        LinkedHashMap<String,Object> dataMap = (LinkedHashMap) responseResult.getData();
        if (dataMap == null) {
            return null;
        }
        JSONObject jsonObject = (JSONObject)JSONObject.toJSON(dataMap);
        UserDTO userDTO = jsonObject.toJavaObject(UserDTO.class);
        return userDTO;
    }
}
