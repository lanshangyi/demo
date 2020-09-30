package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.BizQuartz;
import com.example.demo.service.BizQuartzService;

@Controller
@RequestMapping("/quartz")
public class BizQuartzController {
	
	@Autowired
	private BizQuartzService bizQuartzService;
	
	@GetMapping(value="/insert")
    @ResponseBody
	public int insert(BizQuartz record) {
		return bizQuartzService.insert(record);
		
	}
	
	@GetMapping(value="/all")
    @ResponseBody
	public List<BizQuartz> all(BizQuartz record) {
		return bizQuartzService.selectByExample(record);
		
	}
}
