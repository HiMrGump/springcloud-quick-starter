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

    private String id;

    //登陆账户名
    private String accountName;
    //密码
    private String password;
    //呢称,默认与登陆账户名同
    private String nickName;

    private List<RoleDTO> roleList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> grantedAuthorityList = Lists.newArrayList();
        if(roleList != null && !roleList.isEmpty()){
            for(RoleDTO roleDTO : roleList){
                grantedAuthorityList.add(new SimpleGrantedAuthority(roleDTO.getEnName()));
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
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
