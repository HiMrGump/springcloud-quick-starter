package com.project.util.quartz;

import cn.hutool.core.date.DateUtil;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;

/**
 * Quartz工具类调用
 *
 * @ClassName: QuartzScheduleUtil
 * @Author: WangQingYun
 * @Date: Created in 2019/8/27 11:05
 * @Version: 1.0
 */
public class QuartzScheduleUtils {

    @Autowired
    SchedulerFactoryBean schedulerFactoryBean;

    /**
     * 添加/修改任务
     * @param job 任务描述
     */
    public void addTask(ScheduleJob job) {
        try {
              String jobName = job.getJobName(),
                jobGroup = job.getJobGroup(),
                cronExpression = job.getCronExpression(),
                jobDescription = job.getJobDescription();

            if (checkExists(jobName, jobGroup)) {
                String createTime = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
                Scheduler scheduler = getScheduler();
                TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
                JobKey jobKey = new JobKey(jobName, jobGroup);
                CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing();
                CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withDescription(createTime).withSchedule(cronScheduleBuilder).build();

                JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                jobDetail.getJobBuilder().withDescription(jobDescription);
                HashSet<Trigger> triggerSet = new HashSet<>();
                triggerSet.add(cronTrigger);
                scheduler.scheduleJob(jobDetail, triggerSet, true);
            }else{
                TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
                JobKey jobKey = JobKey.jobKey(jobName, jobGroup);

                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing();
                CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withDescription(jobDescription).withSchedule(scheduleBuilder).build();

                JobDetail jobDetail = JobBuilder.newJob(job.getJobClass()).withIdentity(jobKey).withDescription(jobDescription).build();
                getScheduler().scheduleJob(jobDetail, trigger);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 恢复任务
     * @param job 任务描述
     */
    @Transactional(rollbackFor = Exception.class)
    public void resumeTask(ScheduleJob job) {
        try {
            getScheduler().resumeJob(JobKey.jobKey(job.getJobName(), job.getJobGroup()));
        } catch (Exception e) {
            throw new RuntimeException("恢复任务异常",e);
        }
    }


 /*   @Transactional(readOnly = true)
    public List<ScheduleJob> findTaskList(ScheduleJob job) {
        return taskDao.findTaskList(taskEntity);
    }*/

    /**
     * 暂停任务
     * @param job 任务描述
     */
    @Transactional(rollbackFor = Exception.class)
    public void pauseTask(ScheduleJob job) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
            if (checkExists(job.getJobName(), job.getJobGroup())) {
                getScheduler().pauseTrigger(triggerKey); //停止触发器
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     *  删除任务
     * @param job 任务描述
     */
    public void deleteTask(ScheduleJob job) {

        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
            if (checkExists(job.getJobName(), job.getJobGroup())) {
                getScheduler().pauseTrigger(triggerKey); //停止触发器
                getScheduler().unscheduleJob(triggerKey); //移除触发器
            }
        } catch (SchedulerException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 检查任务是否存在
     *
     * @param jobName 任务名
     * @param jobGroup 任务组
     * @throws SchedulerException
     */
    private boolean checkExists(String jobName, String jobGroup) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        return getScheduler().checkExists(triggerKey);
    }

    private Scheduler getScheduler(){
        return schedulerFactoryBean.getScheduler();
    }
}
