package com.project.util.lock;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 分布式锁自动配置
 *
 * @ClassName: LockAutoConfig
 * @Author: WangQingYun
 * @Date: Created in 2019/9/6 17:26
 * @Version: 1.0
 */
@Configuration
public class LockAutoConfig {
    /**
     * 如果不存在DistrbuteLock则注入，即使用RedisDistrbuteLock作为默认的分布式锁配置
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(DistrbuteLock.class)
    public DistrbuteLock RedisDistrbuteLock(){
        return new RedisDistrbuteLock();
    }
}
