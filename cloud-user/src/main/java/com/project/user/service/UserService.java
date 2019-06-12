package com.project.user.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.project.service.base.BaseService;
import com.project.user.dao.UserDao;
import com.project.user.entity.RoleEntity;
import com.project.user.entity.UserEntity;
import com.project.user.pojo.UserRoleVO;
import com.project.util.BCryptUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 该类实现了XXXX相关操作接口的具体功能
 *
 * @ClassName: UserServiceImpl
 * @Author: WangQingYun
 * @Date: Created in 2019/5/21 11:28
 * @Version: 1.0
 */
@Service
public class UserService extends BaseService<UserEntity, UserDao> {
    //模拟存在一个admin用户
    public UserRoleVO getAccountName(String accountName){
        if("admin".equals(accountName)){
            UserRoleVO userEntity = new UserRoleVO();
            userEntity.setId("123");
            userEntity.setIdCard("123145454");
            userEntity.setNickName("谭咏麟");
            userEntity.setAccountName("admin");
            userEntity.setDeleteFlag(0);
            RoleEntity roleEntity = new RoleEntity();
            roleEntity.setEnName("SIMPLE_USER");
            userEntity.setRoleList(Lists.newArrayList(roleEntity));
            userEntity.setPassword(BCryptUtils.encode("123456"));
            return userEntity;
        }
        return null;
    }
}
