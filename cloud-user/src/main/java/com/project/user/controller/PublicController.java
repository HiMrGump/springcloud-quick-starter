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
import com.project.util.BCryptUtils;
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

    //列表查询
    @GetMapping("/list")
    public ResponseResult list(@RequestBody UserEntity entity){
        DBHelper dbHelper = DBHelper.build().addOperation(DBOperation.LIKE, "accountName", entity.getAccountName())
                .addOperation(DBOperation.EQ,"userEmail",entity.getUserEmail())
                .addOperation(DBOperation.GE,"enable",entity.getEnable());
        return ResponseResult.success(userService.list(dbHelper));
    }

    //分页查询
     @GetMapping("/page")
    public ResponseResult page(@RequestBody UserEntity entity){
         DBHelper dbHelper = DBHelper.build().addOperation(DBOperation.LIKE, "accountName", entity.getAccountName())
                 .addOperation(DBOperation.EQ,"userEmail",entity.getUserEmail())
                 .addOperation(DBOperation.GE,"enable",entity.getEnable());
        return ResponseResult.success(userService.listByPage(entity.buildPageHelper(),dbHelper));
    }

   //保存
    @PostMapping("/save")
    public ResponseResult save(@RequestBody UserEntity entity){
        entity.setPassword(BCryptUtils.encode(entity.getPassword()));
        entity.setEnable(1);
        userService.save(entity);
        return ResponseResult.success();
    }

    //字段不为null则修改,id必传
    @PostMapping("/update")
    public ResponseResult update(@RequestBody UserEntity entity){
        userService.updateBySelective(entity);
        return ResponseResult.success();
    }

    //删除
    @GetMapping("/delete/{id}")
    public ResponseResult delete(@PathVariable(name = "id") String id){
        userService.delete(id);
        return ResponseResult.success();
    }


}
