package com.example.demo.dao;

import com.example.demo.model.BizQuartz;
import com.example.demo.model.BizQuartzExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BizQuartzMapper {
    long countByExample(BizQuartzExample example);

    int deleteByExample(BizQuartzExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BizQuartz record);

    int insertSelective(BizQuartz record);

    List<BizQuartz> selectByExample(BizQuartzExample example);

    BizQuartz selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BizQuartz record, @Param("example") BizQuartzExample example);

    int updateByExample(@Param("record") BizQuartz record, @Param("example") BizQuartzExample example);

    int updateByPrimaryKeySelective(BizQuartz record);

    int updateByPrimaryKey(BizQuartz record);
    
    /**
     * 查询符合起始时间的定时任务
     * @return
     */
    List<BizQuartz> findQuartzByScheduleTaskStart();
    
    /**
     * 查询符合结束时间的定时任务
     * @return
     */
    List<BizQuartz> findQuartzByScheduleTaskEnd();
}