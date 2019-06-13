package com.project.user.controller;

import com.project.controller.base.BaseController;
import com.project.user.entity.UserEntity;
import com.project.user.service.UserService;
import com.project.util.ResponseResult;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.web.bind.annotation.*;

/**
 * 该类实现了XXXX相关操作接口的具体功能
 *
 * @ClassName: UserController
 * @Author: WangQingYun
 * @Date: Created in 2019/5/21 15:29
 * @Version: 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController<UserEntity,UserService> {

    //被保护的接口
    @GetMapping("/getByAccountName/{accountName}")
    public ResponseResult getByAccountName(@PathVariable("accountName") String accountName){
        return ResponseResult.success(service.getByAccountName(accountName));
    }
    //获取jwt,从中可以得到用户数据
    @RequestMapping("/currentLogin")
    public String test1(@RequestHeader("Authorization") String authentication){
        System.out.println("Authentication=" + authentication);
        String token = authentication.replace("Bearer ", ""); //去除
        Jwt jwt = JwtHelper.decode(token);
        String claims = jwt.getClaims();
        return claims;
    }

}
