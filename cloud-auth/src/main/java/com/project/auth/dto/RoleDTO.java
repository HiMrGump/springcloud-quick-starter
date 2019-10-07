package com.project.auth.dto;

import com.project.common.entity.BaseEntity;
import lombok.Data;

/**
 * 角色DTO
 *
 * @ClassName: RoleDTO
 * @Author: WangQingYun
 * @Date: Created in 2019/6/6 15:28
 * @Version: 1.0
 */
@Data
public class RoleDTO extends BaseEntity {
    //角色名
    private String name;
    //角色代码
    private String code;
}
