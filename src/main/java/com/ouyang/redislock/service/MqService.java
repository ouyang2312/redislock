package com.ouyang.redislock.service;

import com.ouyang.redislock.entity.Goods;
import com.ouyang.redislock.mq.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author oy
 * @description
 * @date 2019/7/18
 */
@Service
public class MqService {

    @Autowired
    private MessageSender messageSender;

    public void send(Goods goods){
        messageSender.sendByGoods(goods);
    }

}
