package com.project.gateway.controller;

import com.project.util.ResponseResult;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.stream.Collectors;


/**
 * 该类实现了XXXX相关操作接口的具体功能
 *
 * @ClassName: FallbackController
 * @Author: WangQingYun
 * @Date: Created in 2019/6/18 9:35
 * @Version: 1.0
 */
@RestController
public class FallbackController {

    @RequestMapping("/defaultfallback")
    public ResponseResult defaultfallback(){
        return ResponseResult.serviceDown();
    }

}
