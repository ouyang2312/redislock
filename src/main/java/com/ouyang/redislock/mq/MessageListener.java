package com.ouyang.redislock.mq;

import com.ouyang.redislock.entity.Goods;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author oy
 * @description 消息接受者（监听某个队列）
 * @date 2019/7/18
 */
@Component
public class MessageListener {

    @RabbitListener(queues = "ouyang")
    public void handlerOuyang(String content){
        System.out.println("ouyang接受到的消息： " + content);
    }

    @RabbitListener(queues = "ouyang.message")
    public void handlerOuyangMessage(String content){
        System.out.println("ouyang.message接受到的消息： " + content);
    }

    @RabbitListener(queues = "ouyang2.news")
    public void handlerOuyangNews(String content){
        System.out.println("ouyang2.news接受到的消息： " + content);
    }

    @RabbitListener(queues = "ouyang.user")
    public void handlerOuyangUser(Goods content){
        System.out.println("ouyang.user接受到的消息： " + content);
    }
}
