package com.project.auth.security;

import com.google.common.collect.Lists;
import com.project.auth.dto.RoleDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 扩展UserDetails信息 满足业务需求
 *
 * @author TerryYu
 * @date 2019-04-10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyUserDetails implements UserDetails {
    //主键
    private String id;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> grantedAuthorityList = Lists.newArrayList();
        if(roleList != null && !roleList.isEmpty()){
            for(RoleDTO roleDTO : roleList){
                grantedAuthorityList.add(new SimpleGrantedAuthority(roleDTO.getCode()));
            }
        }
        return grantedAuthorityList;
    }

    @Override
    public String getUsername() {
        return accountName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return enable == 0;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enable == 0;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enable == 0;
    }
}
