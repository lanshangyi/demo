package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.BizUserMapper;
import com.example.demo.model.BizUser;
import com.example.demo.model.BizUserExample;
import com.example.demo.service.UserService;

@Transactional(rollbackFor = Exception.class)
@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private BizUserMapper bizUserMapper;
	
	@Override
	public int insert(BizUser record) {
		return bizUserMapper.insert(record);
	}

	@Override
	public List<BizUser> selectByExample(BizUser record) {
		
		BizUserExample example = new BizUserExample();
		if(record.getId() != null) {
			example.createCriteria().andIdEqualTo(record.getId());
		}
		if(record.getName() != null) {
			example.createCriteria().andNameEqualTo(record.getName());
		}
		
		return bizUserMapper.selectByExample(example);
	}

	@Override
	public List<BizUser> testVin(String paramString) {
		BizUser record = new BizUser();
		record.setName(paramString);
		return selectByExample(record);
		
	}
	
}
