package com.ouyang.redislock.controller;

import com.ouyang.redislock.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author oy
 * @description
 * @date 2019/9/6
 */
@RestController
@RequestMapping("/cache")
public class CacheController {

    @Autowired
    GoodsService goodsService;

    @GetMapping("/test")
    public void test(){
        goodsService.getGoodsByCache(1);
    }

    @GetMapping("/list")
    public void list(){
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        goodsService.getGoodsListByCache(list);
    }

    @GetMapping("/delete")
    public void delete(){
        goodsService.deleteGoodsCache();
    }

    @GetMapping("/deleteAll")
    public void deleteAll(){
        goodsService.deleteGoodsCacheAll();
    }
}
