package com.project.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 该类实现了XXXX相关操作接口的具体功能
 *
 * @ClassName: CodeErrorException
 * @Author: WangQingYun
 * @Date: Created in 2019/5/21 11:42
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
public class ParameterErrorException extends RuntimeException{

    public ParameterErrorException(String message) {
        super(message);
    }
}


