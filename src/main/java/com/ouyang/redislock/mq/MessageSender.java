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

    /**
     * 直接发送到队列
     * @author oy
     * @date 2019/7/19
     * @param goods
     * @return void
     */
    public void sendByGoods(Goods goods){
        amqpTemplate.convertAndSend("ouyang.user",goods);//第一个表示队列 第二个表示信息
    }

    /**
     * 交换机发送消息
     * @author oy
     * @date 2019/7/19
     * @param goods
     * @return void
     */
    public void sendByGoodsByRounter(Goods goods){
        amqpTemplate.convertAndSend("ouyang.direct","user",goods);//第一个表示交换机 第二个表示路由器 第三个表示消息
    }

}
