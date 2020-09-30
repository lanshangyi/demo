package com.example.demo.scheduledTask;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.example.demo.service.BizQuartzService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class ScheduleTask {
	
	@Autowired
	private BizQuartzService bizQuartzService;
	
    @Scheduled(cron = "0 0/1 * * * ?")
    private void configureTasks() {
       log.info("每天隔1分钟执行定时任务: " + LocalDateTime.now());
       bizQuartzService.findQuartzByScheduleTask();
       
    }
}
