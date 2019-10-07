package com.project.user.pojo;

import com.project.user.entity.RoleEntity;
import com.project.user.entity.UserEntity;
import lombok.Data;

import java.util.List;

/**
 * 该类实现了用户角色的VO定义
 *
 * @ClassName: UserRoleVO
 * @Author: WangQingYun
 * @Date: Created in 2019/6/6 13:57
 * @Version: 1.0
 */
@Data
public class UserRoleVO extends UserEntity {

    List<RoleEntity> roleList;

}
