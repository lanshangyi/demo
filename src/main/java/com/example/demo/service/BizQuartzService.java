package com.example.demo.service;

import java.util.List;

import com.example.demo.model.BizQuartz;

public interface BizQuartzService {
	
	long countByExample(BizQuartz record);

    int deleteByExample(BizQuartz record);

    int deleteByPrimaryKey(Integer id);

    int insert(BizQuartz record);

    int insertSelective(BizQuartz record);

    List<BizQuartz> selectByExample(BizQuartz example);

    BizQuartz selectByPrimaryKey(Integer id);

    int updateByExampleSelective(BizQuartz record);

    int updateByExample(BizQuartz record);

    int updateByPrimaryKeySelective(BizQuartz record);

    int updateByPrimaryKey(BizQuartz record);
    
    /**
     * 每天定时查询任务
     */
    void findQuartzByScheduleTask();
}
