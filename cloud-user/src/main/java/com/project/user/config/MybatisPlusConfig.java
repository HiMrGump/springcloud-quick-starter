package com.project.user.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 这个类实现了XXXX相关功能
 *
 * @Author Gump
 * @Date 2019/8/2415:16
 * @Version 1.0
 **/
@Configuration
@MapperScan("com.wang.user.dao.*")
public class MybatisPlusConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
