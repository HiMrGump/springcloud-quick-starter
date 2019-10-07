package com.project.user.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import tk.mybatis.mapper.annotation.Version;

import javax.persistence.Table;

/**
 * 用户表
 *
 * @ClassName: UserEntity
 * @Author: WangQingYun
 * @Date: Created in 2019/5/21 10:50
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@Table(name = "t_user")
public class UserEntity extends BaseEntity {
    //账户名
    private String accountName;
    //密码
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
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
    //版本号,并有乐观锁问题
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Version
    private Integer version;
}
