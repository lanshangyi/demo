package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.BizUser;
import com.example.demo.service.UserService;
import com.example.demo.utils.ResponseData;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@Api(tags = "UserController", description = "用户管理")
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@ApiOperation("用户登录")	
	@GetMapping(value="/doLogin")
    @ResponseBody
	public ResponseData doLogin(BizUser record) {
		ResponseData responseData = new ResponseData();
		BizUser doLogin = userService.doLogin(record);
		if(doLogin != null) {
			responseData.setCode("200");
			responseData.setObject(doLogin);
			responseData.setMsg("登录成功");
		}else {
			responseData.setCode("400");
			responseData.setObject(null);
			responseData.setMsg("登录失败");
		}
		return responseData;
		
	}
	
	@ApiOperation("创建用户")	
	@GetMapping(value="/insert")
    @ResponseBody
	public int insert(BizUser record) {
		return userService.insert(record);
		
	}
	
	@ApiOperation("获取所有用户")	
	@GetMapping(value="/all")
    @ResponseBody
	public List<BizUser> all(BizUser record) {
		return userService.selectByExample(record);
		
	}
}
