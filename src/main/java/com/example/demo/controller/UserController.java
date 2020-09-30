package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.BizUser;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping(value="/insert")
    @ResponseBody
	public int insert(BizUser record) {
		return userService.insert(record);
		
	}
	
	@GetMapping(value="/all")
    @ResponseBody
	public List<BizUser> all(BizUser record) {
		return userService.selectByExample(record);
		
	}
}
