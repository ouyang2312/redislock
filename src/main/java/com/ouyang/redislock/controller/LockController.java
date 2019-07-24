package com.ouyang.redislock.controller;

import com.ouyang.redislock.entity.Goods;
import com.ouyang.redislock.exception.MyException;
import com.ouyang.redislock.service.GoodsService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author oy
 * @description
 * @date 2019/7/16
 */
@RestController
@RequestMapping("/lock")
public class LockController {

    @Autowired
    GoodsService goodsService;

    /**
     * 查看当前商品的数量
     * @author oy
     * @date 2019/7/16
     * @param
     * @return com.ouyang.redislock.entity.Goods
     */
    @GetMapping("/test")
    public Goods test(){
        Goods byId = goodsService.getById(1);
        return byId;
    }

    @GetMapping("/kill/{goodsId}")
    public void kill(@PathVariable("goodsId") Long goodsId)throws Exception{
        goodsService.kill(goodsId);
    }

    @GetMapping("/kill2/{goodsId}")
    public void kill2(@PathVariable("goodsId") Long goodsId)throws MyException {
        goodsService.kill2(goodsId);
    }

}
