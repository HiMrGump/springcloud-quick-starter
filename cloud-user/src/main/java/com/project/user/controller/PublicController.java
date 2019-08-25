package com.project.user.controller;

/**
 * 该类实现了XXXX相关操作接口的具体功能
 *
 * @ClassName: PublicController
 * @Author: WangQingYun
 * @Date: Created in 2019/6/10 11:24
 * @Version: 1.0
 */

import com.project.common.db.DBHelper;
import com.project.common.db.DBOperation;
import com.project.user.entity.UserEntity;
import com.project.user.service.UserService;
import com.project.util.PageHelper;
import com.project.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    //开放的接口
    @GetMapping("/list")
    public ResponseResult list(@RequestBody UserEntity entity){
        DBHelper dbHelper = DBHelper.build().addOperation(DBOperation.LIKE, "account_name", entity.getAccountName());
        return ResponseResult.success(userService.list(dbHelper));
    }

    //开放的接口
    @GetMapping("/page")
    public ResponseResult page(@RequestBody UserEntity entity){
        DBHelper dbHelper = DBHelper.build().addOperation(DBOperation.LIKE, "account_name", entity.getAccountName()).addOperation(DBOperation.EQ,"enable",entity.getEnable());
        return ResponseResult.success(userService.listByPage(entity.buildPageHelper(),dbHelper));
    }

}
