package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.BizUserMapper;
import com.example.demo.entity.PageRequest;
import com.example.demo.entity.PageResult;
import com.example.demo.model.BizUser;
import com.example.demo.model.BizUserExample;
import com.example.demo.model.BizUserExample.Criteria;
import com.example.demo.service.UserService;
import com.example.demo.utils.MD5Util;
import com.example.demo.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Transactional(rollbackFor = Exception.class)
@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private BizUserMapper bizUserMapper;
	
	@Override
	public int insert(BizUser record) {
		String password = record.getPassword();
		record.setPassword(MD5Util.getMD5Str(password));
		return bizUserMapper.insert(record);
	}

	@Override
	public List<BizUser> selectByExample(BizUser record) {
		
		BizUserExample example = new BizUserExample();
		Criteria createCriteria = example.createCriteria();
		if(record.getId() != null) {
			createCriteria.andIdEqualTo(record.getId());
		}
		if(record.getName() != null) {
			createCriteria.andNameEqualTo(record.getName());
		}
		
		return bizUserMapper.selectByExample(example);
	}

	@Override
	public List<BizUser> testVin(String paramString) {
		BizUser record = new BizUser();
		record.setName(paramString);
		return selectByExample(record);
		
	}

	@Override
	public int updateByPrimaryKeySelective(BizUser record) {
		
		return bizUserMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return bizUserMapper.deleteByPrimaryKey(id);
	}

	@Override
	public BizUser loadUserByUsername(String username) {
		BizUserExample example = new BizUserExample();
		Criteria createCriteria = example.createCriteria();
		if(username != null) {
			createCriteria.andNameEqualTo(username);
		}
		List<BizUser> selectByExample = bizUserMapper.selectByExample(example);
		if(!selectByExample.isEmpty()) {
			return selectByExample.get(0);
		}
		return null;
	}

	@Override
	public BizUser doLogin(BizUser record) {
		
		BizUserExample example = new BizUserExample();
		Criteria createCriteria = example.createCriteria();
		if(record.getAccount() != null) {
			createCriteria.andAccountEqualTo(record.getAccount());
		}
		List<BizUser> selectByExample = bizUserMapper.selectByExample(example);
		if(!selectByExample.isEmpty()) {
			String password = selectByExample.get(0).getPassword();
			String md5Str = MD5Util.getMD5Str(record.getPassword());
			if(password.equals(md5Str)) {
				return selectByExample.get(0);
			}
		}
		return null;
	}
	
	@Override
	public PageResult selectByPage(BizUser record) {
		
		return PageUtils.getPageResult(record, getPageInfo(record));
	}
	
	/**
     * 调用分页插件完成分页
     * @param pageQuery
     * @return
     */
    private PageInfo<BizUser> getPageInfo(BizUser bizUser) {
        int pageNum = bizUser.getPageNum();
        int pageSize = bizUser.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        
        BizUserExample example = new BizUserExample();
		Criteria createCriteria = example.createCriteria();
		if(bizUser.getId() != null) {
			createCriteria.andIdEqualTo(bizUser.getId());
		}
		if(bizUser.getName() != null) {
			createCriteria.andNameEqualTo(bizUser.getName());
		}
        List<BizUser> sysMenus = bizUserMapper.selectByExample(example);
        return new PageInfo<BizUser>(sysMenus);
        
    }
	
}
