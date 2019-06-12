package com.project.auth.security;

import com.project.auth.dto.UserDTO;
import com.project.auth.feign.UserServiceClient;
import com.project.auth.util.FeignUtils;
import com.project.util.ResponseResult;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户信息获取
 *
 * @author TerryYu
 * @date 2019-04-10 17:01
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Resource
    BCryptPasswordEncoder passwordEncoder;

    @Resource
    UserServiceClient userServiceClient;

    /**
     * 通过 Username 加载用户详情
     *
     * @param username 用户名
     * @return UserDetails
     * @throws UsernameNotFoundException 用户没找到
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("登陆：" + username);
        ResponseResult responseResult = userServiceClient.findUserByUsername(username);
        System.out.println(responseResult);
        if(FeignUtils.isRequestFail(responseResult)){
            throw new UsernameNotFoundException("找不到用户,请重试");
        }
        UserDTO userDTO = FeignUtils.packageUserVO(responseResult);
        if(userDTO == null){
            throw new UsernameNotFoundException("找不到用户,请重试");
        }
        return build(userDTO);
    }

    private MyUserDetails build( UserDTO userDTO ){
        return new MyUserDetails(userDTO.getId(),userDTO.getAccountName(),userDTO.getPassword(),userDTO.getNickName(),userDTO.getRoleList());
    }

}
