package com.project.user.dao;

import com.project.dao.base.BaseDao;
import com.project.user.entity.UserEntity;
import com.project.user.pojo.UserRoleVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 该类实现了XXXX相关操作接口的具体功能
 *
 * @ClassName: UserMapper
 * @Author: WangQingYun
 * @Date: Created in 2019/5/21 14:23
 * @Version: 1.0
 */
@Mapper
public interface UserDao extends BaseDao<UserEntity> {

}
