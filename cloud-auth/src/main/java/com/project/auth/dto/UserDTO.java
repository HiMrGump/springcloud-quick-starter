package com.project.auth.dto;

import com.project.common.entity.BaseEntity;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 用户DTO
 *
 * @ClassName: UserEntity
 * @Author: WangQingYun
 * @Date: Created in 2019/5/21 10:50
 * @Version: 1.0
 */
@Data
@ToString(callSuper = true)
public class UserDTO extends BaseEntity {
    //账户名
    private String accountName;
    //密码
    private String password;
    //用户别名
    private String userAlias;
    //身份证号
    private String idCard;
    //用户手机号
    private String userMobile;
    //用户邮箱
    private String userEmail;
    //用户头像信息
    private String userAvatar;
    //是否允许登录,0-允许登陆 1-不允许登陆
    private Integer enable;

    List<RoleDTO> roleList;
}
