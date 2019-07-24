package com.ouyang.redislock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author oy
 * @description
 * @date 2019/7/24
 */
@Service
public class WebSocketService {

    @Autowired
    GoodsService goodsService;

    public void test(){
        goodsService.insertEntity();
    }

}
