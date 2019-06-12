package com.project.user.entity;

import com.project.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
@ToString(callSuper = true)
public class UserEntity extends BaseEntity {
    //登陆账户名
    private String accountName;
    //密码
    private String password;
    //呢称,默认与登陆账户名同
    private String nickName;
    //身份证号
    private String idCard;
}
