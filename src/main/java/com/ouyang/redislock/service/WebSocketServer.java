package com.ouyang.redislock.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;


@ServerEndpoint("/websocket")
@Component
public class WebSocketServer {

    public static Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    public static WebSocketService webSocketService;

    //需要注意的是 换成map 用户-session 在每次关闭的时候 及时清除session
    private static List<Session> sessionsList = new ArrayList<>();

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        //处理一下token
        webSocketService.setHash();
        webSocketService.getHash();

        sessionsList.add(session);
        //this.session = session;
        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            log.error("websocket IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
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
            sendMessage(message);
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
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException{
        if(!CollectionUtils.isEmpty(sessionsList)){
            sessionsList.forEach(session->{
                try {
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        //this.session.getBasicRemote().sendText(message);
    }

}


