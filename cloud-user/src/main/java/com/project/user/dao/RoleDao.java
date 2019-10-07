package com.project.user.dao;

import com.project.common.dao.BaseDao;
import com.project.user.entity.RoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 该类定义了角色数据库操作基本接口
 *
 * @ClassName: RoleDao
 * @Author: WangQingYun
 * @Date: Created in 2019/6/14 11:30
 * @Version: 1.0
 */
@Mapper
public interface RoleDao extends BaseDao<RoleEntity> {
    /**
     *  根据用户ID查找该用户拥有的角色
     * @param userId 用户ID
     * @return
     */
    @Select("SELECT r.* FROM t_user_role ur LEFT JOIN t_role r ON ur.role_id = r.id WHERE ur.user_id = #{userId}")
    List<RoleEntity> listByUserId(@Param("userId") String userId);

}
