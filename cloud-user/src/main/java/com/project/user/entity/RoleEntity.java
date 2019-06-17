package com.project.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.project.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 该类实现了XXXX相关操作接口的具体功能
 *
 * @ClassName: RoleEntity
 * @Author: WangQingYun
 * @Date: Created in 2019/6/6 13:51
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_role")
public class RoleEntity extends BaseEntity {
    //角色名
    private String name;
    //角色代码
    private String code;
}
