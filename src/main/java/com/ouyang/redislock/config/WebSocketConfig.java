package com.ouyang.redislock.config;

import com.ouyang.redislock.service.WebSocketServer;
import com.ouyang.redislock.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author oy
 * @description 开启WebSocket支持
 * @date 2019/7/24
 */
@Configuration
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    /**
     * 因 SpringBoot WebSocket 对每个客户端连接都会创建一个 WebSocketServer（@ServerEndpoint 注解对应的） 对象，Bean 注入操作会被直接略过，因而手动注入一个全局变量
     *
     * @param webSocketService
     */
    @Autowired
    public void setMessageService(WebSocketService webSocketService) {
        WebSocketServer.webSocketService = webSocketService;
    }

}
