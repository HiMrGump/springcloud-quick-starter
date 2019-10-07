package com.project.user.config;

import com.alibaba.fastjson.JSON;
import com.project.util.ResponseResult;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

/**
 * 该类实现了oAuth2资源服务器功能
 *
 * @ClassName: ResourceServerConfig
 * @Author: WangQingYun
 * @Date: Created in 2019/6/4 15:53
 * @Version: 1.0
 */

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "user_resource";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID).stateless(true);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/public/**","/actuator/**").permitAll()
                .anyRequest().authenticated()
                .and().exceptionHandling().accessDeniedHandler((request,response,e) -> {
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().println(JSON.toJSONString(ResponseResult.unauthorize()));
                    response.getWriter().flush();
                 }).authenticationEntryPoint((request,response,e) ->{
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().println(JSON.toJSONString(ResponseResult.unauthorize()));
                    response.getWriter().flush();
                 });
    }

    //对token进行非对称解密
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        // 导入证书
        KeyStoreKeyFactory keyStoreKeyFactory =
                new KeyStoreKeyFactory(new ClassPathResource("keystore.jks"), "mypass".toCharArray());
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("UnitedNations"));
        return converter;
    }

    //基于jwt实现令牌（Access Token）
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }
}
