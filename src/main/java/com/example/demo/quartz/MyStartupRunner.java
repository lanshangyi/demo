package com.example.demo.quartz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.demo.quartz.schedulerJob.CronSchedulerJob;

/**
 * 定时任务监听器
 * @author Administrator
 * @Description 项目启动即执行
 *
 */
@Component
public class MyStartupRunner implements CommandLineRunner {
	
	@Autowired
	private CronSchedulerJob scheduleJobs;
	
	@Override
	public void run(String... args) throws Exception {
		
//		scheduleJobs.scheduleJobs();

	}

}
