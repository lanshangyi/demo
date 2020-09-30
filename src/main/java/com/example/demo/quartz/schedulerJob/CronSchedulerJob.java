package com.example.demo.quartz.schedulerJob;

import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import com.example.demo.model.BizQuartz;
import com.example.demo.quartz.job.BusinessJob;
import com.example.demo.quartz.job.ScheduledJob;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CronSchedulerJob {
	
	 private static final String JOB_NAME = "TASK_";
	 
	 public static final String JOB_KEY = "JOB_KEY";
	
	@Autowired
    private SchedulerFactoryBean schedulerFactoryBean;
	
	private void scheduleJob1(Scheduler scheduler) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(ScheduledJob.class) .withIdentity("job1", "group1").build();
        //获取工作数据集，往工作数据集中添加动态参数，用作定时任务查询
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        jobDataMap.put("ruleId", "123");
        
        // 6的倍数秒执行 也就是 6 12 18 24 30 36 42 ....
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/6 * * * * ?");
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")
                .usingJobData("name","王智1").withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
    }
	
	private void addScheduleJob(Scheduler scheduler, BizQuartz quertz) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(BusinessJob.class).withIdentity(JOB_NAME + quertz.getId()).build();
        
        jobDetail.getJobDataMap().put(JOB_KEY, quertz);
        
        //通过触发器名和cron 表达式创建 Trigger
	    Trigger cronTrigger = newTrigger()
	            .withIdentity(JOB_NAME + quertz.getId())
	            .startNow()
	            .withSchedule(CronScheduleBuilder.cronSchedule(quertz.getCronExpression()))
	            .build();

        //重置启动时间
        ((CronTriggerImpl)cronTrigger).setStartTime(new Date());

        //执行定时任务
        scheduler.scheduleJob(jobDetail,cronTrigger);
    }
	
	 /**
     * @Author LShangyi
     * @Description 启动定时任务
     * @Param
     * @return void
     **/
    public void scheduleJobs() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        scheduleJob1(scheduler);
    }
    
    /**
     * @Author LShangyi
     * @Description 添加定时任务
     * @Param
     * @return void
     **/
    public void addJobs(BizQuartz quertz) throws SchedulerException {
    	log.info("添加定时任务：{}",quertz);
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        addScheduleJob(scheduler, quertz);
    }
    
    /**
     * @Author LShangyi
     * @Description 停止定时任务
     * @Param
     * @return void
     **/
    public void stopJobs(BizQuartz quertz) throws SchedulerException {
    	log.info("停止定时任务：{}",quertz);
    	Scheduler scheduler = schedulerFactoryBean.getScheduler();
    	try {
            JobKey jobKey = JobKey.jobKey(JOB_NAME + quertz.getId());
            scheduler.pauseJob(jobKey);
            scheduler.deleteJob(jobKey);
        } catch (Exception e){
            log.error("删除定时任务失败", e);
        }
    }
}
