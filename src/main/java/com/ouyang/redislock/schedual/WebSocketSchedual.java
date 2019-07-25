package com.ouyang.redislock.schedual;

import com.ouyang.redislock.service.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author oy
 * @description 定时发送器
 * @date 2019/7/24
 */
@Component
public class WebSocketSchedual {

    @Autowired
    WebSocketServer webSocketServer;

    /**
     * 间隔一秒发送一次
     * @author oy
     * @date 2019/7/24
     * @param
     * @return void
     */
//    @Scheduled(cron = "0/1 * * * * *")
//    public void sendMsg(){
//        Date date = new Date();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String format = simpleDateFormat.format(date);
//        try {
//            webSocketServer.sendMessage(format);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}
