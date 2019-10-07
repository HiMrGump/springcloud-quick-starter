package com.project.user.service;

import com.project.common.dao.BaseDao;
import com.project.common.service.BaseService;
import com.project.common.service.MyBatisServiceImpl;
import com.project.user.dao.RoleDao;
import com.project.user.entity.RoleEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 该类实现了角色相关操作接口的具体功能
 *
 * @ClassName: RoleService
 * @Author: WangQingYun
 * @Date: Created in 2019/6/14 11:31
 * @Version: 1.0
 */
@Service
public class RoleService extends MyBatisServiceImpl<RoleEntity> implements BaseService<RoleEntity> {

    @Resource
    RoleDao roleDao;

    @Override
    public BaseDao getDao() {
        return roleDao;
    }
}
