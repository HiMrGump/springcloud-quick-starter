package com.project.user;

import io.ostenant.rpc.thrift.server.annotation.EnableThriftServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextListener;

/**
 * 该类实现了XXXX相关操作接口的具体功能
 *
 * @ClassName: AuthApplication
 * @Author: WangQingYun
 * @Date: Created in 2019/5/28 11:36
 * @Version: 1.0
 */

@EnableDiscoveryClient
@EnableThriftServer
@EnableFeignClients
@SpringBootApplication(scanBasePackages = "com.project")
public class UserApplication {

    @Bean
    public RequestContextListener requestContextListener(){
        return new RequestContextListener();
    }

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
