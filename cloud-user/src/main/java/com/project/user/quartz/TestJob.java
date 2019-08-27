package com.project.user.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 该类实现了XXXX相关操作接口的具体功能
 *
 * @ClassName: TestJob
 * @Author: WangQingYun
 * @Date: Created in 2019/8/26 17:44
 * @Version: 1.0
 */
public class TestJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String jobName = jobExecutionContext.getJobDetail().getKey().getName();
        System.out.println(jobName);
        System.out.println("this is a test task!!current time is " + System.currentTimeMillis());
    }
}
