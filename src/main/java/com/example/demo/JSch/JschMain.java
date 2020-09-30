package com.example.demo.JSch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.demo.JSch.utils.HttpUtil;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import cn.hutool.json.JSON;
import net.sf.json.JSONObject;

/**
 * java抓取服务器日志信息
 * @author Administrator
 *
 */
public class JschMain {
	
	public static void main(String[] args) throws JSchException, IOException {
		
		Map<JSch,String> map = new HashMap<JSch, String>();
		//服务器ip地址
        map.put(new JSch(),"120.76.188.130");
//        map.put(new JSch(),"11.166.32.1000004");
//        map.put(new JSch(),"11.166.225.50");
        Set<JSch> keys = map.keySet();
        for(JSch key: keys) {
            String host = map.get(key);
            String username = "root";  //登录账号
            Session session = key.getSession(username, host, 22);
            session.setPassword("Aliyun2018!");  //登录密码
            session.setConfig("StrictHostKeyChecking", "no");
            session.setTimeout(3000);
            try {
                session.connect();
                System.out.println();
                
                String command1 = "tail -f  /data/logs/socket/*info_log.log";
                String command2 = "tail -f /data/logs/gateway/*info_log.log";  //需要抓取的日志
                String command3 = "tail -f /data/logs/pkt/*info_log.log";  //需要抓取的日志
                String command4 = "tail -f /data/logs/pl/*info_log.log";  //需要抓取的日志
                String command5 = "tail -f /data/logs/order/*info_log.log";  //需要抓取的日志
                
                ChannelExec channel = (ChannelExec) session.openChannel("exec");
                channel.setCommand(command4);
                channel.connect();
                BufferedReader in = new BufferedReader(new InputStreamReader(channel.getInputStream()));

                String msg;
                System.out.println("服务器名称：" + host + "日志信息：");
                
                while ((msg = in.readLine()) != null) {
                    if(msg.contains("Exception")) {
                    	
                    	System.out.println(msg);
                    	
                    	msg = "警告：检测到服务器异常信息 -- >"+msg;
                    	sendMsg(msg);
                    }
                }
                System.out.println("执行命令完毕");
                channel.disconnect();
                session.disconnect();
            }catch (JSchException e){
                System.out.println("服务器:"+host+"异常");

            };

        }
		
	}
	
	/**
	 * 推送钉钉机器人
	 * @param msg
	 * @return
	 */
	public static String sendMsg(String msg) {
		
		try {
            //钉钉机器人地址（配置机器人的webhook）
            String dingUrl = "https://oapi.dingtalk.com/robot/send?access_token=0f89a739d3bf7bbc752bc3d467befa7a3b0d8444343d52397e0d55a054ab1566";

            //是否通知所有人
            boolean isAtAll = false;
            //通知具体人的手机号码列表
            List<String> mobileList =new ArrayList();

            //组装请求内容
            String reqStr = buildReqStr(msg, isAtAll, mobileList);

            //推送消息（http请求）
            String result = HttpUtil.postJson(dingUrl, reqStr);
            System.out.println("result == " + result);

        }catch (Exception e){
            e.printStackTrace();

        }
		
		return msg;
	}
	
	/**
     * 组装请求报文
     * @param content
     * @return
     */
    private static String buildReqStr(String content, boolean isAtAll, List<String> mobileList) {
        //消息内容
        Map<String, String> contentMap = new HashMap();
        contentMap.put("content", content);

        //通知人
        Map<String, Object> atMap = new HashMap();
        //1.是否通知所有人
        atMap.put("isAtAll", isAtAll);
        //2.通知具体人的手机号码列表
        atMap.put("atMobiles", mobileList);

        Map<String, Object> reqMap = new HashMap();
        reqMap.put("msgtype", "text");
        reqMap.put("text", contentMap);
        reqMap.put("at", atMap);

        return JSONObject.fromObject(reqMap).toString();
    }
	
}
