package com.project.base.exception;

import com.project.exception.ParameterErrorException;
import com.project.util.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;

/**
 * 该类实现了全局异常捕捉
 *
 * @ClassName: GlobalExceptionHandler
 * @Author: WangQingYun
 * @Date: Created in 2019/4/30 9:46
 * @Version: 1.0
 */
@ControllerAdvice(basePackages = {"com.project.base.controller"})
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 全局处理BindException异常,用于数据校验
     * @param e 异常信息
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = BindException.class)
    public ResponseResult handlerException(BindException e){
        BindingResult bindingResult = e.getBindingResult();
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining());
            return ResponseResult.parameterError(errorMessage);
        }
        return ResponseResult.parameterError();
    }

    /**
     * 全局处理MethodArgumentNotValidException异常,用于数据校验
     * @param e 异常信息
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseResult handlerMethodArgumentNotValidException(MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining());
            return ResponseResult.parameterError(errorMessage);
        }
        return ResponseResult.parameterError();
    }

    /**
     * 全局处理ErrorCodeException异常
     * @param e 异常信息
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = ParameterErrorException.class)
    public ResponseResult handlerErrorCodeException(ParameterErrorException e){
        return ResponseResult.parameterError(e.getMessage());
    }

    /**
     * 全局处理HttpMessageNotReadableException异常,当使用@RequestBody,接收参数为null时,处理此异常
     * @param e 异常信息
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseResult handlerHttpMessageNotReadableException(HttpMessageNotReadableException e){
        return ResponseResult.parameterError("该接口仅接收JSON参数");
    }


    /**
     * 全局处理Exception异常
     * @param e 异常信息
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseResult handlerException(Exception e){
        log.error(">>>>>>>>>>捕获未知异常",e);
        return ResponseResult.error();
    }
}
