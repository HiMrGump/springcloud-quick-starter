package com.project.user.controller;

/**
 * 该类实现了XXXX相关操作接口的具体功能
 *
 * @ClassName: PublicController
 * @Author: WangQingYun
 * @Date: Created in 2019/6/10 11:24
 * @Version: 1.0
 */

import com.project.user.service.UserService;
import com.project.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    UserService userService;

    //开放的接口
    @GetMapping("/getUserByAccountName/{accountName}")
    public ResponseResult getByAccountName(@PathVariable("accountName") String accountName){
        return ResponseResult.success(userService.getByAccountName(accountName));
    }
}
