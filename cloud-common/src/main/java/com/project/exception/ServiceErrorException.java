package com.project.exception;

import com.project.entity.base.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 该类实现了XXXX相关操作接口的具体功能
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
