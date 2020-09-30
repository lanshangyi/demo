package com.example.demo.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.example.demo.model.BizQuartz;
import com.example.demo.quartz.schedulerJob.CronSchedulerJob;
import com.example.demo.service.BizQuartzService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * spring启动后执行定时任务
 * @author Administrator
 *
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JobRunner implements ApplicationRunner {
	
	@Autowired
	private BizQuartzService bizQuartzService;
	@Autowired
	private CronSchedulerJob cronSchedulerJob;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		BizQuartz example = new BizQuartz();
		example.setIsPause(1);
		example.setStatus(true);
		List<BizQuartz> selectByExample = bizQuartzService.selectByExample(example);
		for(BizQuartz bizQuartz : selectByExample) {
			cronSchedulerJob.addJobs(bizQuartz);;
		}
	}

}
