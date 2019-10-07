package com.project.user.entity;

import com.project.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;

/**
 * 角色表
 * 
 * @ClassName: RoleEntity
 * @Author: WangQingYun
 * @Date: Created in 2019/6/6 13:51
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_role")
public class RoleEntity extends BaseEntity {
    //角色名
    private String name;
    //角色代码
    private String code;
}
