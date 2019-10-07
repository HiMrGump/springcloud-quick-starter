package com.project.user.dao;

import com.project.common.dao.BaseDao;
import com.project.user.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 该类定义了用户数据库操作基本接口
 *
 * @ClassName: UserDao
 * @Author: WangQingYun
 * @Date: Created in 2019/6/14 11:30
 * @Version: 1.0
 */
@Mapper
public interface UserDao extends BaseDao<UserEntity> {

}
