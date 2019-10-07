package com.project.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 启动上下文
 *
 * @ClassName: AuthApplication
 * @Author: WangQingYun
 * @Date: Created in 2019/5/28 11:36
 * @Version: 1.0
 */
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class BaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class, args);
    }
}
