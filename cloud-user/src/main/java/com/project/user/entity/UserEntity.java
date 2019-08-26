package com.project.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.project.common.entity.BaseEntity;
import com.project.common.entity.PageEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 该类实现了XXXX相关操作接口的具体功能
 *
 * @ClassName: UserEntity
 * @Author: WangQingYun
 * @Date: Created in 2019/5/21 10:50
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_user")
public class UserEntity extends PageEntity {
    //账户名
    private String accountName;
    //密码
    //@JSONField(serialize = false)
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
}
