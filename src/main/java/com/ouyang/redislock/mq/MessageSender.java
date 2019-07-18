package com.ouyang.redislock.mq;

import com.ouyang.redislock.entity.Goods;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author oy
 * @description 消息队列发送消息
 * @date 2019/7/18
 */
@Component
public class MessageSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(){
        String hello = "helloWorld";
        amqpTemplate.convertAndSend("ouyang",hello);
    }

    public void sendByGoods(Goods goods){
        amqpTemplate.convertAndSend("user",goods);
    }

}
