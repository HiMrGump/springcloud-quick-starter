package com.project.auth;

import io.ostenant.rpc.thrift.client.annotation.EnableThriftClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 启动上下文
 *
 * @ClassName: AuthApplication
 * @Author: WangQingYun
 * @Date: Created in 2019/5/28 11:36
 * @Version: 1.0
 */

@SpringBootApplication
@EnableDiscoveryClient
@EnableThriftClient
@EnableFeignClients
@EnableHystrix
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}
