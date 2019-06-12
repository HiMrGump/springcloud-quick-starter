package com.project.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 该类实现了XXXX相关操作接口的具体功能
 *
 * @ClassName: TestController
 * @Author: WangQingYun
 * @Date: Created in 2019/5/30 10:41
 * @Version: 1.0
 */
@RestController
@RequestMapping("/public")
public class PublicController {

    @RequestMapping("/1")
    public String test1(){
        return "public1";
    }
}
