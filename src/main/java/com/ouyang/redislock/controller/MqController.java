package com.ouyang.redislock.controller;

import com.ouyang.redislock.entity.Goods;
import com.ouyang.redislock.service.MqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author oy
 * @description
 * @date 2019/7/18
 */
@RestController
@RequestMapping("/rabbit")
public class MqController {

    @Autowired
    private MqService mqService;

    @PostMapping("/send")
    public String sendMsg(@RequestBody Goods goods){
        mqService.send(goods);
        return "发送成功！";
    }

}