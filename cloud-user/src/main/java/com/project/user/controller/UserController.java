package com.project.user.controller;

import com.project.common.controller.BaseController;
import com.project.common.service.BaseService;
import com.project.user.entity.UserEntity;
import com.project.user.service.UserService;
import com.project.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserController extends BaseController {

    @Autowired
    private UserService userService;


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

    @Override
    public BaseService getService() {
        return userService;
    }
}
