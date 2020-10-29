package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.BizQuartz;
import com.example.demo.service.BizQuartzService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@Api(tags = "BizQuartzController", description = "动态定时任务管理")
@RequestMapping("/quartz")
public class BizQuartzController {
	
	@Autowired
	private BizQuartzService bizQuartzService;
	
	@ApiOperation("创建动态定时任务")	
	@GetMapping(value="/insert")
    @ResponseBody
	public int insert(@Valid BizQuartz record) {
		return bizQuartzService.insert(record);
		
	}
	
	@ApiOperation("获取所有动态定时任务")	
	@GetMapping(value="/all")
    @ResponseBody
	public List<BizQuartz> all(BizQuartz record) {
		return bizQuartzService.selectByExample(record);
		
	}
}
