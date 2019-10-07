package com.project.util;

import com.project.constant.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * controller统一返回的实体
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

    public static ResponseResult parameterError(String msg){
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(ResultCode.PARAM_ERROR.getCode());
        responseResult.setMsg(msg);
        return responseResult;
    }

    public static ResponseResult error(){
        return new ResponseResult(ResultCode.SYSTEM_ERROR);
    }

    public static ResponseResult serviceDown(){
        return new ResponseResult(ResultCode.SERVICE_DOWNGRADE_ERROR);
    }

    public static ResponseResult success(Object data){
        return new ResponseResult(ResultCode.SUCCESS,data);
    }

    public static ResponseResult unauthorize(){
        return new ResponseResult(ResultCode.UNAUTHORIZED);
    }
}
