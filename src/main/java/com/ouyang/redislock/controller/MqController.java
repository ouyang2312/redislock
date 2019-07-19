package com.ouyang.redislock.controller;

import com.ouyang.redislock.entity.Goods;
import com.ouyang.redislock.service.MqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/send2")
    public String sendByGoodsByRounter(@RequestBody Goods goods){
        mqService.sendByGoodsByRounter(goods);
        return "发送成功！";
    }

    @GetMapping("/send3")
    public String sendString(@RequestParam("key")String key){
        mqService.sendByGoodsByRounter(key);
        return "发送成功！";
    }
}
