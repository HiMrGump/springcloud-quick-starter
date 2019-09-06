package com.project.util.lock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 该类实现了XXXX相关操作接口的具体功能
 *
 * @ClassName: LockAutoConfig
 * @Author: WangQingYun
 * @Date: Created in 2019/9/6 17:26
 * @Version: 1.0
 */
@Configuration
public class LockAutoConfig {

    @Bean
    public DistrbuteLock RedisDistrbuteLock(){
        return new RedisDistrbuteLock();
    }
}
