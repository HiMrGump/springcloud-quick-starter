package com.project.util.quartz;

import lombok.Data;

/**
 * 该类定义了任务调度的基本属性
 *
 * @ClassName: ScheduleJob
 * @Author: WangQingYun
 * @Date: Created in 2019/8/26 17:12
 * @Version: 1.0
 */
@Data
public class ScheduleJob {
    //主键
    private Long id;
    //任务名称
    private String jobName;
    //任务分组
    private String jobGroup;
    //任务状态
    private String jobStatus;
    //任务执行方法
    private Class jobClass;
    // cron 表达式
    private String cronExpression;
    //任务描述
    private String jobDescription;
    // 时区
    private String timeZoneId;
    private Long startTime;
    private Long endTime;
    //状态
    private String state;
}
