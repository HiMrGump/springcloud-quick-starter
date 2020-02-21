package com.project.auth.security;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import com.project.auth.dto.ValidateCodeDTO;
import com.project.auth.util.ValidateCodeUtils;
import com.project.constant.ResultCode;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * 不能重写申请token错误返回体，但可以拓展返回的信息
 * 如果是InvalidGrantException：则找不到用户信息
 * 如果是UsernameNotFoundException：则找不到用户信息
 * 如果是BadCredentialsException：则是密码验证错误
 * 如果是InternalAuthenticationServiceException，则是验证码错误
 *
 * @Author Gump
 * @Date 2020/2/1814:45
 * @Version 1.0
 **/
@Slf4j
@Data
public class MyWebResponseExceptionTranslator implements WebResponseExceptionTranslator<OAuth2Exception> {

    private int maxLoginError = 3; //最大登陆错误次数

    private boolean showValidate = true; //是否错误超过3次开启验证

    private int expire = 120; //有效期,秒

    //缓存,记录最大错误数
    public final LoadingCache<String, ValidateCodeDTO> LOGIN_ERROR_CACHE = CacheBuilder
            .newBuilder().refreshAfterWrite(expire, TimeUnit.SECONDS)// 给定时间内没有被读/写访问，则回收。
            .expireAfterAccess(expire, TimeUnit.SECONDS)//
            .maximumSize(100) // 设置缓存个数
            .build(new CacheLoader<String, ValidateCodeDTO>() {
                @Override
                /** 当本地缓存命没有中时，调用load方法获取结果并将结果缓存 **/
                public ValidateCodeDTO load(String account) {
                    return new ValidateCodeDTO();
                }
            });

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        OAuth2Exception oAuth2Exception = new OAuth2Exception("");

        //不管发生几次错误，当超过三次时都要计算错误
        //1.如果错误超过3次则需要输入验证码
        //2.如果错误超过3次，账号错误
        if(showValidate){ //需要验证错误
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = requestAttributes.getRequest();
            String username = request.getParameter("username");
            if(StringUtils.isNotBlank(username)){
                ValidateCodeDTO validateCodeDTO = LOGIN_ERROR_CACHE.getUnchecked(username);
                int errorCount = validateCodeDTO.getError();
                if(errorCount >= maxLoginError){ //超过允许的最大次数，验证码出现
                    boolean isResetVc = true;
                    if( e instanceof UsernameNotFoundException ){ //找不到用户
                        oAuth2Exception.addAdditionalInformation("code", ResultCode.LOGIN_NOTCOUNT_SHOW_VALIDATE.getCode());
                        oAuth2Exception.addAdditionalInformation("msg",ResultCode.LOGIN_NOTCOUNT_SHOW_VALIDATE.getMsg());
                    }else if( e instanceof InvalidGrantException){ //密码错误
                        oAuth2Exception.addAdditionalInformation("code", ResultCode.LOGIN_PASSWORDERROR_SHOW_VALIDATE.getCode());
                        oAuth2Exception.addAdditionalInformation("msg",ResultCode.LOGIN_PASSWORDERROR_SHOW_VALIDATE.getMsg());
                    }else if(e instanceof BadCredentialsException){ //密码错误
                        oAuth2Exception.addAdditionalInformation("code", ResultCode.LOGIN_PASSWORDERROR_SHOW_VALIDATE.getCode());
                        oAuth2Exception.addAdditionalInformation("msg",ResultCode.LOGIN_PASSWORDERROR_SHOW_VALIDATE.getMsg());
                    }else{
                        isResetVc = false;
                        oAuth2Exception.addAdditionalInformation("code", ResultCode.LOGIN_VALIDATE_ERROR.getCode());
                        oAuth2Exception.addAdditionalInformation("msg",ResultCode.LOGIN_VALIDATE_ERROR.getMsg());

                    }
                    if(isResetVc){
                        String vc = ValidateCodeUtils.createText();
                        validateCodeDTO.setVc(vc);
                        validateCodeDTO.setExpire(expire);
                        HashMap<String, Object> dataMap = Maps.newHashMapWithExpectedSize(2);
                        dataMap.put("code",vc);
                        dataMap.put("expire",expire);
                        oAuth2Exception.addAdditionalInformation("data", JSON.toJSONString(dataMap));
                    }
                }else{ //没超过三次计数加一
                    if(e instanceof UsernameNotFoundException ){ //找不到用户
                        oAuth2Exception.addAdditionalInformation("code", ResultCode.LOGIN_NOTFOUND.getCode());
                        oAuth2Exception.addAdditionalInformation("msg",ResultCode.LOGIN_NOTFOUND.getMsg());
                    }else{ //密码错误不需要验证码
                        oAuth2Exception.addAdditionalInformation("code", ResultCode.LOGIN_DONTSHOW_VALIDATE.getCode());
                        oAuth2Exception.addAdditionalInformation("msg",ResultCode.LOGIN_DONTSHOW_VALIDATE.getMsg());
                    }
                }
                validateCodeDTO.setError(errorCount + 1);//无论错误正确都要登陆次数+1
                LOGIN_ERROR_CACHE.put(username,validateCodeDTO);
            }else{
                if(e instanceof UsernameNotFoundException ){ //找不到用户
                    oAuth2Exception.addAdditionalInformation("code", ResultCode.LOGIN_NOTFOUND.getCode());
                    oAuth2Exception.addAdditionalInformation("msg",ResultCode.LOGIN_NOTFOUND.getMsg());
                }else{ //密码错误不需要验证码
                    oAuth2Exception.addAdditionalInformation("code", ResultCode.LOGIN_DONTSHOW_VALIDATE.getCode());
                    oAuth2Exception.addAdditionalInformation("msg",ResultCode.LOGIN_DONTSHOW_VALIDATE.getMsg());
                }
            }

        }else{
            if(e instanceof UsernameNotFoundException ){ //找不到用户
                oAuth2Exception.addAdditionalInformation("code", ResultCode.LOGIN_NOTFOUND.getCode());
                oAuth2Exception.addAdditionalInformation("msg",ResultCode.LOGIN_NOTFOUND.getMsg());
            }else{ //密码错误不需要验证码
                oAuth2Exception.addAdditionalInformation("code", ResultCode.LOGIN_DONTSHOW_VALIDATE.getCode());
                oAuth2Exception.addAdditionalInformation("msg",ResultCode.LOGIN_DONTSHOW_VALIDATE.getMsg());
            }
        }

        ResponseEntity responseEntity = new ResponseEntity<OAuth2Exception>(oAuth2Exception,HttpStatus.OK);
        return responseEntity;
    }

    //刷新二维码,并重置有效时间
    public void flushCode(String username,String vc){
        ValidateCodeDTO validateCodeDTO = LOGIN_ERROR_CACHE.getUnchecked(username);
        validateCodeDTO.setVc(vc);
        validateCodeDTO.setExpire(expire);
        LOGIN_ERROR_CACHE.put(username,validateCodeDTO);
    }

}
