package com.example.demo.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.javassist.expr.NewArray;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.BizQuartzMapper;
import com.example.demo.model.BizQuartz;
import com.example.demo.model.BizQuartzExample;
import com.example.demo.model.BizQuartzExample.Criteria;
import com.example.demo.quartz.schedulerJob.CronSchedulerJob;
import com.example.demo.service.BizQuartzService;

@Transactional(rollbackFor = Exception.class)
@Service
public class BizQuartzServiceImpl implements BizQuartzService {
	
	@Autowired
	private BizQuartzMapper bizQuartzMapper;
	@Autowired
	private CronSchedulerJob scheduleJobs;
	
	@Override
	public long countByExample(BizQuartz record) {
		BizQuartzExample example = new BizQuartzExample();
		example.createCriteria().andBeanNameEqualTo(record.getBeanName());
		return bizQuartzMapper.countByExample(example);
	}

	@Override
	public int deleteByExample(BizQuartz record) {
		return 0;
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return 0;
	}

	@Override
	public int insert(BizQuartz record) {
		record.setStatus(true);  //状态为正常
		record.setIsPause(0);   //执行状态暂停
		Date startTime = record.getStartTime();
		Date endTime = record.getEndTime();
		Date nowTime = new Date();
		SimpleDateFormat sdfDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startTimeStr = sdfDateFormat.format(startTime);
		startTimeStr += " 00:00:00";
		try {
			startTime = sdfDateFormat2.parse(startTimeStr);
			record.setStartTime(startTime);
			
			String endTimeStr = sdfDateFormat.format(endTime);
			endTimeStr += " 23:59:59";
			endTime = sdfDateFormat2.parse(endTimeStr);
			record.setEndTime(endTime);
			
			String nwoTimeStr = sdfDateFormat.format(nowTime);
			nwoTimeStr += " 00:00:00";
			nowTime = sdfDateFormat2.parse(nwoTimeStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		System.out.println("定时任务起始时间：" + startTime.getTime());
		System.out.println("当前系统时间：" + nowTime.getTime());
		
		Boolean flagBoolean = false;
		if(nowTime.getTime() == startTime.getTime()) {
			flagBoolean = true;
			record.setIsPause(1);  //状态改为启动
		}
		
		int insert = bizQuartzMapper.insert(record);
		
		if(flagBoolean) {
			//说明是今天，立马执行定时任务
			try {
				scheduleJobs.addJobs(record);
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		}
		
		return insert;
	}

	@Override
	public int insertSelective(BizQuartz record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<BizQuartz> selectByExample(BizQuartz record) {
		
		BizQuartzExample example = new BizQuartzExample();
		Criteria createCriteria = example.createCriteria();
		if(record.getBeanName() != null) {
			createCriteria.andBeanNameEqualTo(record.getBeanName());
		}
		if(record.getStatus() != null) {
			createCriteria.andStatusEqualTo(record.getStatus());
		}
		if(record.getIsPause() != null) {
			createCriteria.andIsPauseEqualTo(record.getIsPause());
		}
		
		return bizQuartzMapper.selectByExample(example);
	}

	@Override
	public BizQuartz selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByExampleSelective(BizQuartz record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByExample(BizQuartz record) {
		return 0;
	}

	@Override
	public int updateByPrimaryKeySelective(BizQuartz record) {
		return bizQuartzMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(BizQuartz record) {
		return bizQuartzMapper.updateByPrimaryKey(record);
	}

	@Override
	public void findQuartzByScheduleTask() {
		//查询符合起始时间的任务
		List<BizQuartz> startData = bizQuartzMapper.findQuartzByScheduleTaskStart();
		for(BizQuartz dataBizQuartz : startData) {
			dataBizQuartz.setIsPause(1);
			updateByPrimaryKeySelective(dataBizQuartz);
			
			try {
				//推到任务组中
				scheduleJobs.addJobs(dataBizQuartz);
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		}
		
		//查询符合结束时间的任务
		List<BizQuartz> endData = bizQuartzMapper.findQuartzByScheduleTaskEnd();
		for(BizQuartz dataBizQuartz : endData) {
			dataBizQuartz.setIsPause(0);
			dataBizQuartz.setStatus(false);
			updateByPrimaryKeySelective(dataBizQuartz);
			
			//停止任务
			try {
				scheduleJobs.stopJobs(dataBizQuartz);
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
