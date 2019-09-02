package com.project.base.service;

import com.project.base.dao.DictDao;
import com.project.base.entity.DictEntity;
import com.project.common.dao.BaseDao;
import com.project.common.service.BaseService;
import com.project.common.service.MyBatisServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 该类实现了XXXX相关操作接口的具体功能
 *
 * @ClassName: DictService
 * @Author: WangQingYun
 * @Date: Created in 2019/8/31 16:53
 * @Version: 1.0
 */
@Service
public class DictService extends MyBatisServiceImpl<DictEntity> implements BaseService<DictEntity> {

    @Resource
    private DictDao dictDao;

    @Override
    public BaseDao getDao() {
        return dictDao;
    }
}
