package com.ouyang.redislock.controller;

import com.ouyang.redislock.service.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author oy
 * @description 测试websocket 发送消息
 * @date 2019/7/24
 */
@RestController
@RequestMapping(value = "/websocket")
public class WebSocketController {

    @Autowired
    WebSocketServer webSocketServer;

    @GetMapping(value = "/sendMsg")
    public void sendMsg(@RequestParam("msg")String msg,@RequestParam("id")String id) throws Exception{
        System.out.println("要发送的消息："+msg);
        webSocketServer.sendMessageByUser(id,msg);
    }

}
