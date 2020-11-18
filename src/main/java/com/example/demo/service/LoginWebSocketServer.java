package com.example.demo.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import net.sf.json.JSONObject;

/**
 * webSocket服务类
 * @author Administrator
 *
 */
@ServerEndpoint("/loginWebsocket/{sid}")
@Component
public class LoginWebSocketServer {
	
	//静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    //记录每个用户下多个终端的连接
    private static Map<String, CopyOnWriteArraySet<LoginWebSocketServer>> userSocket = new HashMap<>();
    
    private static CopyOnWriteArraySet<LoginWebSocketServer> webSocketSet = new CopyOnWriteArraySet<LoginWebSocketServer>();
 
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
 
    //接收sid
    private String sid="";
 
    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        this.session = session;
        this.sid=sid;
        addOnlineCount();           //在线数加1
        
        if (userSocket.containsKey(this.sid)) {
            System.out.println("当前用户id:"+sid+"已有其他终端登录");
            userSocket.get(this.sid).add(this); //增加该用户set中的连接实例
        }else {
        	System.out.println("当前用户id:"+sid+"第一个终端登录");
        	CopyOnWriteArraySet<LoginWebSocketServer> webSocketSet_ = new CopyOnWriteArraySet<LoginWebSocketServer>();
            webSocketSet_.add(this);     //加入set中
            userSocket.put(this.sid, webSocketSet_);
        }
        System.out.println("用户"+sid+"登录的终端个数是为"+userSocket.get(this.sid).size());
        System.out.println("当前在线用户数为："+userSocket.size()+",所有终端个数为："+onlineCount);
       
        Map<String, String> map = new HashMap<String, String>();
        map.put("msg", sid+" 登录成功");
        map.put("msgType", "text");
        
        String string = JSONObject.fromObject(map).toString();
        
        onMessage(string,this.session);
        
//        try {
//        	
//            sendMessage(sid+" 加入聊天室");
//        } catch (IOException e) {
//        	System.out.println("websocket IO异常");
//        }
    }
    
    
    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
    	//移除当前用户终端登录的websocket信息,如果该用户的所有终端都下线了，则删除该用户的记录
    	
    	userSocket.get(this.sid).remove(this);
    	
        if (userSocket.get(this.sid).size() == 0) {
            userSocket.remove(this.sid);
        }
        
        subOnlineCount();           //在线数减1
        
        Map<String, String> map = new HashMap<String, String>();
        map.put("msg", sid+" 退出登录");
        map.put("msgType", "text");
        
        String string = JSONObject.fromObject(map).toString();
        
        onMessage(string,this.session);
        
//        try {
//        	
////			sendMessage(this.sid+" 退出聊天室");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
        
        if(userSocket.get(this.sid) != null) {
        	 System.out.println("用户"+this.sid+"登录的终端个数是为"+userSocket.get(this.sid).size());
        }
        System.out.println("当前在线用户数为："+userSocket.size()+",所有终端个数为："+onlineCount);
    }
    
    
    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
    	System.out.println("收到来自窗口"+sid+"的信息:"+message);
    	
    	JSONObject fromObject = JSONObject.fromObject(message);
    	Map<String,String> map = (Map)JSONObject.toBean(fromObject, Map.class);
    	String msg = map.get("msg");
    	String msgType = map.get("msgType");
    	
    	if(msg.indexOf("聊天室") == -1) {
    		String mString = sid +" 说：";
    		if("img".equals(msgType)) {
    			String imgDocString = "<img src=\""+msg+"\" style=\"width: 100px; Height: 100px;\"/>";
    			mString += imgDocString;
    		}else if("video".equals(msgType)) {
    			String imgDocString = "<video src=\""+msg+"\" style=\"width: 200px; Height: 200px;\"/>";
    			mString += imgDocString;
    		}else {
    			mString += msg;
    		}
    		map.put("msg", mString);
    	}
    	String string = JSONObject.fromObject(map).toString();
    	
    	
        //群发消息
    	for(String sidString : userSocket.keySet()) {
    		CopyOnWriteArraySet<LoginWebSocketServer> copyOnWriteArraySet = userSocket.get(sidString);
    		for (LoginWebSocketServer item : copyOnWriteArraySet) {
                try {
                    item.sendMessage(string);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    	}
    }
    
    
    /**
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
    	System.out.println("发生错误");
    	try {
    		if(session.isOpen()) {
    			session.close();
    		}
		} catch (IOException e) {
			e.printStackTrace();
		}
        error.printStackTrace();
    }
    
    
    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }
    
    /**
     * 群发自定义消息
     * 
     */
    public static Boolean sendInfo(String message,@PathParam("sid") String sid) {
    	
    	if (userSocket.containsKey(sid)) {
            System.out.println(" 给用户id为："+sid+"的所有终端发送消息："+message);
            for (LoginWebSocketServer WS : userSocket.get(sid)) {
            	System.out.println("sessionId为:"+WS.session.getId());
                try {
                    WS.session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println(" 给用户id为："+sid+"发送消息失败");
                    return false;
                }
            }
            return true;
        }
    	System.out.println("发送错误：当前连接不包含id为："+sid+"的用户");
        return false;
    	
//    	System.out.println("推送消息到窗口"+sid+"，推送内容:"+message);
//        for (WebSocketServer item : webSocketSet) {
//            try {
//                //这里可以设定只推送给这个sid的，为null则全部推送
//                if(sid==null) {
//                    item.sendMessage(message);
//                }else if(item.sid.equals(sid)){
//                    item.sendMessage(message);
//                }
//            } catch (IOException e) {
//                continue;
//            }
//        }
    }
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }
    public static synchronized void addOnlineCount() {
        LoginWebSocketServer.onlineCount++;
    }
    public static synchronized void subOnlineCount() {
        LoginWebSocketServer.onlineCount--;
    }
    public static CopyOnWriteArraySet<LoginWebSocketServer> getWebSocketSet() {
        return webSocketSet;
    }
    
    public static Boolean getUserSocket(String userName) {
    	CopyOnWriteArraySet<LoginWebSocketServer> copyOnWriteArraySet = userSocket.get(userName);
    	if(copyOnWriteArraySet != null) {
    		
    		for (LoginWebSocketServer item : copyOnWriteArraySet) {
                try {
                    item.sendMessage("out!有人登录，你可以下去了");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    		
    		return true;
    	}
        return false;
    }
}
