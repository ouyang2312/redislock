package com.ouyang.redislock.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author oy
 * @description rabbit配置
 * @date 2019/7/18
 */
@Configuration
public class RabbitMQConfig {


    /**
     * 配置自定义的序列化规则
     *
     * @return
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 创建一个 ouyang.goods 的队列
     *
     * @param
     * @return org.springframework.amqp.core.Queue
     * @author oy
     * @date 2019/7/18
     */
    @Bean
    public Queue createOuyangGoods() {
        return new Queue("ouyang.goods");
    }

    /**
     * 创建一个 ouyang.user 的队列
     *
     * @param
     * @return org.springframework.amqp.core.Queue
     * @author oy
     * @date 2019/7/18
     */
    @Bean
    public Queue createOuyangUser() {
        return new Queue("ouyang.user");
    }


    /**
     * 创建一个 fanout 交换器
     *
     * @param
     * @return org.springframework.amqp.core.FanoutExchange
     * @author oy
     * @date 2019/7/18
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("ouyang.fanout");
    }

    /**
     * 创建一个 direct 交换器
     *
     * @param
     * @return org.springframework.amqp.core.FanoutExchange
     * @author oy
     * @date 2019/7/18
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("ouyang.direct");
    }

    /**
     *  将 ouyang.goods 队列绑定到路由fanoutExchange
     * @author oy
     * @date 2019/7/18
     * @param
     * @return org.springframework.amqp.core.Binding
     */
    @Bean
    public Binding bindFanoutBinding() {
        return BindingBuilder.bind(createOuyangGoods()).to(fanoutExchange());
    }

    /**
     *  将 ouyang.user 队列绑定到路由 directExchange 路由key user
     * @author oy
     * @date 2019/7/18
     * @param
     * @return org.springframework.amqp.core.Binding
     */
    @Bean
    public Binding bindDirectExchange() {
        return BindingBuilder.bind(createOuyangUser()).to(directExchange()).with("user");
    }



}
