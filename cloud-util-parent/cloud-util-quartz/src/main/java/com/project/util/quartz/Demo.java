package com.project.util.quartz;

import com.project.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *  集成quartz示例代码
 *
 * @ClassName: Demo
 * @Author: WangQingYun
 * @Date: Created in 2019/8/27 14:15
 * @Version: 1.0
 */
public class Demo {

    @Autowired
    QuartzScheduleUtils quartzScheduleUtil;

    @GetMapping("/addTask")
    public ResponseResult addTask(){
        System.out.println("准备加入任务");
        ScheduleJob scheduleJob = new ScheduleJob();
        scheduleJob.setJobName("jobName");
        scheduleJob.setJobGroup("jobGroup");
        scheduleJob.setJobDescription("描述");
        scheduleJob.setCronExpression("0/1 * * * * ? *");
        scheduleJob.setJobClass(TestJob.class);
        quartzScheduleUtil.addTask(scheduleJob);
        return ResponseResult.success();
    }

    @GetMapping("/pauseTask")
    public ResponseResult pauseTask(){
        System.out.println("准备暂停任务");
        ScheduleJob scheduleJob = new ScheduleJob();
        scheduleJob.setJobName("jobName");
        scheduleJob.setJobGroup("jobGroup");
        quartzScheduleUtil.pauseTask(scheduleJob);
        return ResponseResult.success();
    }

    @GetMapping("/resumeTask")
    public ResponseResult resumeTask(){
        System.out.println("准备恢复任务");
        ScheduleJob scheduleJob = new ScheduleJob();
        scheduleJob.setJobName("jobName");
        scheduleJob.setJobGroup("jobGroup");
        quartzScheduleUtil.resumeTask(scheduleJob);
        return ResponseResult.success();
    }

    @GetMapping("/updateTask")
    public ResponseResult updateTask(){
        System.out.println("准备修改任务");
        ScheduleJob scheduleJob = new ScheduleJob();
        scheduleJob.setJobName("jobName");
        scheduleJob.setJobGroup("jobGroup");
        scheduleJob.setJobDescription("描述");
        scheduleJob.setCronExpression("0/5 * * * * ? *");
        quartzScheduleUtil.addTask(scheduleJob);
        return ResponseResult.success();
    }

    @GetMapping("/deleteTask")
    public ResponseResult deleteTask(){
        System.out.println("准备删除任务");
        ScheduleJob scheduleJob = new ScheduleJob();
        scheduleJob.setJobName("jobName");
        scheduleJob.setJobGroup("jobGroup");
        quartzScheduleUtil.deleteTask(scheduleJob);
        return ResponseResult.success();
    }
}
