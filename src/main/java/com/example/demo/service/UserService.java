package com.example.demo.service;

import java.util.List;

import com.example.demo.model.BizUser;

public interface UserService {
	
	public int insert(BizUser record);
	
	public List<BizUser> selectByExample(BizUser record);
	
	public List<BizUser> testVin(String paramString);
}
