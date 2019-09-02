package com.project.base.controller;

import com.project.base.entity.DictEntity;
import com.project.base.service.DictService;
import com.project.common.controller.BaseController;
import com.project.common.service.BaseService;
import com.project.util.JwtUtils;
import com.project.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * 该类实现了XXXX相关操作接口的具体功能
 *
 * @ClassName: DictController
 * @Author: WangQingYun
 * @Date: Created in 2019/8/31 16:57
 * @Version: 1.0
 */
@RestController
@RequestMapping("/dict")
public class DictController extends BaseController<DictEntity> {

    @Autowired
    private DictService dictService;

    @Override
    public BaseService getService() {
        return dictService;
    }

    /**
     * 根据type获取字典
     * @param type
     * @return
     */
    @GetMapping("/getByType/{type}")
    public ResponseResult getByType(@PathVariable("type") String type){
        DictEntity dictEntity = new DictEntity();
        dictEntity.setType(type);
        return ResponseResult.success(dictService.list(dictEntity));
    }



}
