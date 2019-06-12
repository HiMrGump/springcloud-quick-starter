package com.project.util;

import com.project.entity.base.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 该类实现了XXXX相关操作接口的具体功能
 *
 * @ClassName: ResponseResult
 * @Author: WangQingYun
 * @Date: Created in 2019/5/21 11:52
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseResult {
    //返回码
    private String code;
    //错误描述
    private String msg;
    //返回数据
    private Object data;

    public ResponseResult(ResultCode resultCode){
       this(resultCode,null);
    }

    public ResponseResult(ResultCode resultCode,Object data){
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.data = data;
    }


    public static ResponseResult success(){
        return new ResponseResult(ResultCode.SUCCESS);
    }


    public static ResponseResult parameterError(){
        return new ResponseResult(ResultCode.PARAM_ERROR);
    }


    public static ResponseResult error(){
        return new ResponseResult(ResultCode.SYSTEM_ERROR);
    }

    public static ResponseResult serviceDown(){
        return new ResponseResult(ResultCode.SERVICE_DOWN_ERROR);
    }

    public static ResponseResult success(Object data){
        return new ResponseResult(ResultCode.SUCCESS,data);
    }

    public static ResponseResult unauthorize(){
        return new ResponseResult(ResultCode.UNAUTHORIZED);
    }
}
