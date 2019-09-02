package com.project.gateway.exception;

import com.google.common.collect.Maps;
import com.project.constant.ResultCode;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 重写gateway的返回错误码
 *
 * @ClassName: JsonExceptionHandler
 * @Author: WangQingYun
 * @Date: Created in 2019/9/2 16:46
 * @Version: 1.0
 */
public class JsonExceptionHandler extends DefaultErrorWebExceptionHandler {

    public JsonExceptionHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties, ErrorProperties errorProperties, ApplicationContext applicationContext) {
        super(errorAttributes, resourceProperties, errorProperties, applicationContext);
    }

    /**
              * 指定响应处理方法为JSON处理的方法
             * @param errorAttributes
               */
    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    /**
     * 根据code获取对应的HttpStatus
     * @param errorAttributes
     */
    @Override
    protected HttpStatus getHttpStatus(Map<String, Object> errorAttributes) {
        return HttpStatus.valueOf(Integer.valueOf((String)errorAttributes.get("code")));
    }

    @Override
    protected Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
        Throwable error = super.getError(request);
        error.printStackTrace();
        HashMap<String, Object> errorMap = Maps.newHashMap();
        if (error instanceof org.springframework.cloud.gateway.support.NotFoundException) {
            errorMap.put("code", ResultCode.SERVICE_OFF_ERROR.getCode());
            errorMap.put("msg",ResultCode.SERVICE_OFF_ERROR.getMsg());
        }else{
            errorMap.put("code", ResultCode.NOT_FOUND.getCode());
            errorMap.put("msg",ResultCode.NOT_FOUND.getMsg());
        }
        return errorMap;
    }
}
