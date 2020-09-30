package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.WSMessageService;

@Controller
@RequestMapping("/demo")
public class TestController {
	
	@Autowired
	private WSMessageService wsMessageService;
	
	@RequestMapping("/hello")
	@ResponseBody
	public String hello() {
		return "Hello world";
	}
	
//	@GetMapping("/index")
//	public String index() {
//		return "index";
//	}
	
	//请求入口
    @GetMapping(value="/TestWS")
    @ResponseBody
    public String TestWS(@RequestParam(value="sid",required=true) String userId, @RequestParam(value="message",required=true) String message){
        
    	if(wsMessageService.sendToAllTerminal(userId, message)){
            return "发送成功";
        }else{
            return "发送失败";
        }   
    }
}
