
package com.project.auth.security;

import com.project.auth.dto.UserDTO;
import com.project.auth.thrift.ThriftUserServiceClient;
import com.project.thrift.entity.ThriftResponseResult;
import com.project.thrift.util.ThriftUtils;
import com.project.util.BeanUtils;
import org.apache.thrift.TException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * 该类实现了XXXX相关操作接口的具体功能
 *
 * @ClassName: SmsCodeAuthenticationProvider
 * @Author: WangQingYun
 * @Date: Created in 2019/6/24 17:51
 * @Version: 1.0
 */
@Component
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    @Resource
    ThriftUserServiceClient userServiceClient;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try{
            System.out.println("手机登陆》》》》》》》》》》》》》》》》》》》》》》》》");
            //接受未认证的SmsCodeAuthenticationToken
            System.out.println(authentication);
            SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken) authentication;
            String mobile = (String) authenticationToken.getPrincipal();
            String smsCode = authenticationToken.getSmsCode();
            System.out.println("手机号是：" + mobile + ",准备验证验证码：" + smsCode);
            if(!"888888".equals(smsCode)){
                throw new BadCredentialsException("验证码错误");
            }

            ThriftResponseResult thriftResponseResult = userServiceClient.client().getByMobile(mobile);
            Optional<UserDTO> userDTOOptional = ThriftUtils.parseObject(thriftResponseResult, UserDTO.class);
            if(!userDTOOptional.isPresent()){
                throw new BadCredentialsException("找不到用户,请重试");
            }
            System.out.println("用户是:" + userDTOOptional.get());
            //重新构建SmsCodeAuthenticationToken（已认证）
            SmsCodeAuthenticationToken authenticationResult = new SmsCodeAuthenticationToken( build(userDTOOptional.get()));
            authenticationResult.setDetails(authenticationToken.getDetails());
            return authenticationResult;

        }catch (TException e){
            e.printStackTrace();
        }
        throw new BadCredentialsException("找不到用户,请重试");
    }

/**
     * 只有Authentication为SmsCodeAuthenticationToken使用此Provider认证
     * @param authentication
     * @return
     */

    @Override
    public boolean supports(Class<?> authentication) {
       return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private MyUserDetails build( UserDTO userDTO ){
        return BeanUtils.copyBean(userDTO,MyUserDetails.class);
    }


}

