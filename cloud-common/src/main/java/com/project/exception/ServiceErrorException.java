package com.project.exception;

import com.project.constant.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 业务异常定义
 *
 * @ClassName: ServiceErrorException
 * @Author: WangQingYun
 * @Date: Created in 2019/5/21 13:53
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
public class ServiceErrorException extends RuntimeException{

    ResultCode resultCode;
}
