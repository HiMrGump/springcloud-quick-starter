package com.project.auth.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.web.bind.annotation.RequestHeader;
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
public class TestController {

    @RequestMapping("/test1")
    @PreAuthorize("hasAuthority('SIMPLE_USER')")
    public String test1(@RequestHeader("Authorization") String authentication){
        String token = authentication.replace("bearer ", ""); //去除
        Jwt jwt = JwtHelper.decode(token);
        String claims = jwt.getClaims();
        System.out.println(claims);
        //spring security下获取用户信息
     /*   MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        System.out.println("当前登录用户拥有的权限：");
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        authorities.stream().forEach(System.out::println);*/
        return "test1";
    }

    @RequestMapping("/test2")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public String test2(){
        return "test2";
    }

    @RequestMapping("/test3")
    public String test3(){
        return "test3";
    }
}
