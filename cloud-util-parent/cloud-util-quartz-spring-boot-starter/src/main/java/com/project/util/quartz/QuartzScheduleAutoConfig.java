package com.project.util.quartz;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 任务调度的自动化配置
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
