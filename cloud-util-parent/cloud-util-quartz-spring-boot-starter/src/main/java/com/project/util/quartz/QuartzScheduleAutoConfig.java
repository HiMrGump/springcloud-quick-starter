package com.project.util.quartz;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 该类实现了XXXX相关操作接口的具体功能
 *
 * @ClassName: QuartzScheduleConfig
 * @Author: WangQingYun
 * @Date: Created in 2019/9/6 16:45
 * @Version: 1.0
 */
@Configuration
public class QuartzScheduleAutoConfig {

    @Bean
    public QuartzScheduleUtils quartzScheduleUtils(){
        return new QuartzScheduleUtils();
    }
}
