package com.project.auth.security;

import com.project.auth.dto.UserDTO;
import com.project.auth.thrift.ThriftUserServiceClient;
import com.project.thrift.entity.ThriftResponseResult;
import com.project.thrift.util.ThriftUtils;
import com.project.util.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 *  spring security账号密码获取用户信息的方式
 *
 * @ClassName: MyUserDetailsService
 * @Author: WangQingYun
 * @Date: Created in 2019/5/21 10:50
 * @Version: 1.0
 */
@Service
@Slf4j
public class MyUserDetailsService implements UserDetailsService {

   /* @Resource
    UserServiceClient userServiceClient;*/

   @Resource
   ThriftUserServiceClient userServiceClient;

    /**
     * 通过 Username 加载用户详情
     *
     * @param username 用户名
     * @return UserDetails
     * @throws UsernameNotFoundException 用户没找到
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            log.debug("Account password login,find params{username:{}}",username);
            ThriftResponseResult thriftResponseResult = userServiceClient.client().getByAccountName(username);
            Optional<UserDTO> userDTOOptional = ThriftUtils.parseObject(thriftResponseResult, UserDTO.class);
            if(!userDTOOptional.isPresent()){
                throw new UsernameNotFoundException("找不到用户,请重试");
            }
            return build(userDTOOptional.get());
        } catch (TException e) {
            e.printStackTrace();
        }
        throw new UsernameNotFoundException("找不到用户,请重试");
    }

    private MyUserDetails build( UserDTO userDTO ){
        return BeanUtils.copyBean(userDTO,MyUserDetails.class);
    }

}
