package com.project.user.dao;

import com.project.dao.base.BaseDao;
import com.project.user.entity.RoleEntity;
import com.project.util.BCryptUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 该类实现了XXXX相关操作接口的具体功能
 *
 * @ClassName: RoleDao
 * @Author: WangQingYun
 * @Date: Created in 2019/6/14 11:30
 * @Version: 1.0
 */
@Mapper
public interface RoleDao extends BaseDao<RoleEntity> {

    @Select("SELECT r.* FROM t_user_role ur LEFT JOIN t_role r ON ur.role_id = r.id WHERE ur.user_id = #{userId}")
    List<RoleEntity> listByUserId(@Param("userId") String userId);

}
