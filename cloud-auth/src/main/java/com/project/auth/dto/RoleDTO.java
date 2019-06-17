package com.project.auth.dto;

import com.project.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 该类实现了XXXX相关操作接口的具体功能
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
