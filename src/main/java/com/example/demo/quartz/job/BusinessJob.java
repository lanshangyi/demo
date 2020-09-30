package com.example.demo.quartz.job;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.example.demo.model.BizQuartz;
import com.example.demo.model.BizUser;
import com.example.demo.quartz.schedulerJob.CronSchedulerJob;
import com.example.demo.utils.SpringUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BusinessJob implements Job {
	
	 /** 该处仅供参考 */
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		BizQuartz bizQuartz = (BizQuartz)jobDataMap.get(CronSchedulerJob.JOB_KEY);
		
		long startTime = System.currentTimeMillis();
		
		try {
            // 执行任务
            log.info("任务准备执行，任务名称：{}", bizQuartz.getQuartzName());
            
            Class beanClass = SpringUtil.getBean(bizQuartz.getBeanName()).getClass();
            Method method = beanClass.getMethod(bizQuartz.getMethodName(), new Class[] {String.class});
            List<BizUser> resultBizUsers = (List<BizUser>)method.invoke(SpringUtil.getBean(bizQuartz.getBeanName()), new Object[] {new String(bizQuartz.getParams())});
            long times = System.currentTimeMillis() - startTime;
            log.info("任务执行完毕，任务名称：{} 总共耗时：{} 毫秒，得到结果集：{}", bizQuartz.getQuartzName(), times, resultBizUsers);
            
        } catch (Exception e) {
            log.error("任务执行失败，任务名称：{}" + bizQuartz.getQuartzName(), e);
        }

	}

}
