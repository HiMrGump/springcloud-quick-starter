package com.project.auth.security;

import com.project.auth.dto.UserDTO;
import com.project.auth.dto.ValidateCodeDTO;
import com.project.auth.thrift.ThriftUserServiceClient;
import com.project.thrift.entity.ThriftResponseResult;
import com.project.thrift.util.ThriftUtils;
import com.project.util.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.http.AccessTokenRequiredException;
import org.springframework.security.oauth2.common.exceptions.UserDeniedAuthorizationException;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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


   @Resource
   MyWebResponseExceptionTranslator webResponseExceptionTranslator;

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
            ValidateCodeDTO validateCodeDTO = webResponseExceptionTranslator.LOGIN_ERROR_CACHE.getUnchecked(username);
            if(validateCodeDTO.getError() > webResponseExceptionTranslator.getMaxLoginError()){  //错误超过指定次数，需要判断验证码
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                String vc = request.getParameter("vc");
                if(!validateCodeDTO.getVc().equalsIgnoreCase(vc)){
                    throw new ValidateCodeException("验证码错误");
                }
            }

            ThriftResponseResult thriftResponseResult = userServiceClient.client().getByAccountName(username);
            Optional<UserDTO> userDTOOptional = ThriftUtils.parseObject(thriftResponseResult, UserDTO.class);
            if(!userDTOOptional.isPresent()){
              //  return buildNotFoundUser(username);
                throw new UsernameNotFoundException("找不到用户");
            }
            return build(userDTOOptional.get());
        } catch (TException e) {
            e.printStackTrace();
        }
        throw new UsernameNotFoundException("找不到用户");
    }

    private MyUserDetails build( UserDTO userDTO ){
        return BeanUtils.copyBean(userDTO,MyUserDetails.class);
    }

    private MyUserDetails buildNotFoundUser(String username){
        MyUserDetails user = new MyUserDetails();
        user.setAccountName(username);
        user.setEnable(0);
        return user;
    }


}
