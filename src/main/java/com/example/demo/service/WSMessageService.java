package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public class WSMessageService {
	
	//声明websocket连接类
    private WebSocketServer webSocketServer = new WebSocketServer();
    
    /**
     * @Title: sendToAllTerminal
     * @Description: 调用websocket类给用户下的所有终端发送消息
     * @param @param userId 用户id
     * @param @param message 消息
     * @param @return 发送成功返回true，否则返回false
     */
    public Boolean sendToAllTerminal(String sid,String message){   
        System.out.println("向用户"+sid+"的消息："+message);
        if(webSocketServer.sendInfo(message, sid)){
            return true;
        }else{
            return false;
        }   
    } 
}
