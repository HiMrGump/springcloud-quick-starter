package com.project.common.controller;

import com.project.common.entity.BaseEntity;
import com.project.common.service.BaseService;
import com.project.util.ResponseResult;
import org.springframework.web.bind.annotation.*;

/**
 * 该类实现了XXXX相关操作接口的具体功能
 *
 * @ClassName: BaseController
 * @Author: WangQingYun
 * @Date: Created in 2019/6/6 10:24
 * @Version: 1.0
 */
public abstract class BaseController{

    public abstract BaseService getService();

    /**
     * 根据ID获取一条数据
     * @param id 主键
     * @return
     */
    @GetMapping("/get/{id}")
    public ResponseResult get(@PathVariable("id") String id){
        return ResponseResult.success(getService().get(id));
    }

    /**
     * 保存一条数据
     * @param entity 实体
     * @return
     */
    @PostMapping("/save")
    public ResponseResult save(@RequestBody BaseEntity entity){
        getService().save(entity);
        return ResponseResult.success();
    }

    /**
     * 保存一条数据
     * @param entity 实体
     * @return
     */
    @PutMapping("/update")
    public ResponseResult update(@RequestBody BaseEntity entity){
        getService().update(entity);
        return ResponseResult.success();
    }


    /**
     * 删除一条数据
     * @param id 主键
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ResponseResult delete(@PathVariable("id") String id){
        getService().delete(id);
        return ResponseResult.success();
    }

}
