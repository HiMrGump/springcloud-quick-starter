package com.project.auth.config;

import com.alibaba.fastjson.JSON;
import com.project.auth.security.MyUserDetailsService;
import com.project.util.ResponseResult;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * webSecurity 权限控制类
 *
 * @author TerryYu
 * @date 2019-04-11 13:58
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private MyUserDetailsService myUserDetailsService;

    @Resource
    private PasswordEncoder passwordEncoder;

    //供给oauth2.0使用
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        http.authorizeRequests()
                .antMatchers("/actuator/**", "/public/**").permitAll()//不需要身份认证可以访问
                .antMatchers("/**", "/oauth/**").authenticated()//必须要有身份认证
                .and()
                .formLogin().failureHandler((request,response,e) -> {  //只针对form表单登录登陆失败
                     e.printStackTrace();
                     response.setContentType("application/json;charset=UTF-8");
                     response.getWriter().println(JSON.toJSONString(ResponseResult.unauthorize()));
                     response.getWriter().flush();
                })
                .and()
                .exceptionHandling().accessDeniedHandler((request,response,e) -> {   //登录成功,访问某个接口无权限
                     e.printStackTrace();
                     response.setContentType("application/json;charset=UTF-8");
                     response.getWriter().println(JSON.toJSONString(ResponseResult.unauthorize()));
                     response.getWriter().flush();
                })
                .authenticationEntryPoint((request,response,e) -> {     //postman登录
                      e.printStackTrace();
                      response.setContentType("application/json;charset=UTF-8");
                      response.getWriter().println(JSON.toJSONString(ResponseResult.unauthorize()));
                      response.getWriter().flush();
                });
    }

    /**
     * 注入自定义的userDetailsService实现，获取用户信息，设置密码加密方式
     *
     * @param authManagerBuilder
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
        authManagerBuilder.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
