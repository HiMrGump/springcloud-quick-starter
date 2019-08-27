package com.project.user.quartz;

import lombok.Data;

/**
 * 该类实现了XXXX相关操作接口的具体功能
 *
 * @ClassName: ScheduleJob
 * @Author: WangQingYun
 * @Date: Created in 2019/8/26 17:12
 * @Version: 1.0
 */
@Data
public class ScheduleJob {
    private Long id; //ID
    private String jobName; //任务名称
    private String jobGroup; //任务分组
    private String jobStatus; //任务状态
    private Class jobClass;//任务执行方法
    private String cronExpression; // cron 表达式
    private String jobDescription; //任务描述
    private String timeZoneId; // 时区
    private Long startTime;
    private Long endTime;
    private String state; //状态
}
