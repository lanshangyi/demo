package com.example.demo.service;

import java.util.List;

import com.example.demo.model.BizUser;

public interface UserService {
	
	public int insert(BizUser record);
	
	int updateByPrimaryKeySelective(BizUser record);
	
	int deleteByPrimaryKey(Integer id);
	
	public List<BizUser> selectByExample(BizUser record);
	
	BizUser loadUserByUsername(String username);
	
	BizUser doLogin(BizUser record);
	
	public List<BizUser> testVin(String paramString);
}
