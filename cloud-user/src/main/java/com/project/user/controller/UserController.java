package com.project.user.controller;

import com.project.common.controller.BaseController;
import com.project.common.service.BaseService;
import com.project.user.entity.UserEntity;
import com.project.user.feign.BaseClient;
import com.project.user.service.UserService;
import com.project.util.JwtUtils;
import com.project.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


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
public class UserController extends BaseController<UserEntity> {

    @Autowired
    private UserService userService;

    @Autowired
    private BaseClient baseClient;


    //被保护的接口
    @GetMapping("/getByAccountName/{accountName}")
    public ResponseResult getByAccountName(@PathVariable("accountName") String accountName){
        return ResponseResult.success(userService.getByAccountName(accountName));
    }

    //获取jwt,从中可以得到用户数据
    @RequestMapping("/currentLogin")
    public ResponseResult currentLogin(@RequestHeader("Authorization") String authentication){
        return ResponseResult.success(userService.getCurrentLoginUser().orElse(null));
    }

    //获取jwt,从中可以得到用户数据
    @RequestMapping("/getInfo")
    public ResponseResult getInfo(@RequestHeader("Authorization") String authentication){
      /*  Optional<JwtUtils.JwtDetail> jwtDetail = JwtUtils.parseJwt(authentication);
        System.out.println(jwtDetail);*/
        ResponseResult responseResult = baseClient.getByType("TEST_CODE");
        return responseResult;
    }

    @Override
    public BaseService getService() {
        return userService;
    }
}
