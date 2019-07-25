package com.ouyang.redislock.service;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSONObject;
import com.ouyang.redislock.entity.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//这个id 可能要改成对应的token
@ServerEndpoint("/websocket/{id}")
@Component
public class WebSocketServer {

    public static Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    public static WebSocketService webSocketService;

    //用户-session 在每次关闭的时候 及时清除session
    private static ConcurrentHashMap<String, Session> map = new ConcurrentHashMap<>();

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(@PathParam("id") String id, Session session) {
        //处理一下token
        Session session1 = map.get(id);
        if (session1 != null) {
            //告知用户已经下线了
            try {
                ResponseBody responseBody = new ResponseBody()
                        .setCode(1001)
                        .setMsg("你的账号在另外一个地方登录");
                String s = JSONObject.toJSONString(responseBody);
                sendMessage(session1, s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        map.put(id, session);
        //this.session = session;
        try {
            sendMessage(session,"连接成功");
        } catch (IOException e) {
            log.error("websocket IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(@PathParam("id") String id,Session session) {
        //清除对应的socket
        Session session1 = map.get(id);
        if(session1 != null){
            //清除对应的session
            map.remove(id);
        }
        log.info("socket连接关闭！");
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        try {
            sendMessage(session,message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        try {
            sendMessage(session,"发生错误");
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.error("发生错误");
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(Session session, String message) throws IOException {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 实现服务器主动推送（根据用户）
     */
    public void sendMessageByUser(String id, String message) throws IOException {
        Session session = map.get(id);
        try {
            if(session != null){
                session.getBasicRemote().sendText(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


