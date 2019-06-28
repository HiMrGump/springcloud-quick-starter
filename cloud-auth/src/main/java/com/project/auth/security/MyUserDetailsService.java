package com.project.auth.security;

import com.project.auth.dto.UserDTO;
import com.project.auth.thrift.ThriftUserServiceClient;
import com.project.thrift.entity.ThriftResponseResult;
import com.project.thrift.util.ThriftUtils;
import com.project.util.BeanUtils;
import org.apache.thrift.TException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * 用户信息获取
 *
 * @author TerryYu
 * @date 2019-04-10 17:01
 */
@Service
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
            System.out.println("账号密码登陆》》》》》》》》》》》》》》》》》》》》》》》》");
            ThriftResponseResult thriftResponseResult = userServiceClient.client().getByAccountName(username);
            Optional<UserDTO> userDTOOptional = ThriftUtils.parseObject(thriftResponseResult, UserDTO.class);
            if(!userDTOOptional.isPresent()){
                throw new UsernameNotFoundException("找不到用户,请重试");
            }
            System.out.println(userDTOOptional.get());
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
