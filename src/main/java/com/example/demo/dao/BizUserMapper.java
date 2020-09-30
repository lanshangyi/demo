package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.example.demo.model.BizUser;
import com.example.demo.model.BizUserExample;

public interface BizUserMapper {
    long countByExample(BizUserExample example);

    int deleteByExample(BizUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BizUser record);

    int insertSelective(BizUser record);

    List<BizUser> selectByExample(BizUserExample example);

    BizUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BizUser record, @Param("example") BizUserExample example);

    int updateByExample(@Param("record") BizUser record, @Param("example") BizUserExample example);

    int updateByPrimaryKeySelective(BizUser record);

    int updateByPrimaryKey(BizUser record);
}