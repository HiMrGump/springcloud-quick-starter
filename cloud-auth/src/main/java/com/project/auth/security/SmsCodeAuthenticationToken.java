package com.project.auth.security;

import lombok.Data;
import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * 该类实现了XXXX相关操作接口的具体功能
 *
 * @ClassName: SmsCodeAuthenticationToken
 * @Author: WangQingYun
 * @Date: Created in 2019/6/21 17:37
 * @Version: 1.0
 */
@Data
public class SmsCodeAuthenticationToken extends AbstractAuthenticationToken {

    /**
     * 在 UsernamePasswordAuthenticationToken 中该字段代表登录的用户名，
     * 在认证前这里就代表登录的手机号码
     * 在认证后这里就代表登录的用户数据
     */
    private Object principal;

    private String smsCode;

    /**
     * 构建一个没有鉴权的 SmsCodeAuthenticationToken
     */
    public SmsCodeAuthenticationToken(MyUserDetails principal) {
        super(principal.getAuthorities());
        this.principal = principal;
        setAuthenticated(true);
    }

    public SmsCodeAuthenticationToken(String principal,String smsCode) {
        super(null);
        this.principal = principal;
        this.smsCode = smsCode;
        setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        super.setAuthenticated(isAuthenticated);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }
}
