package com.ouyang.redislock.service;

import com.ouyang.redislock.entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author oy
 * @description
 * @date 2019/7/24
 */
@Service
public class WebSocketService {

    private static final String REDIS_ENTITY_PRO = "real-time-statistics";

    @Autowired
    GoodsService goodsService;

    @Autowired
    RedisTemplate redisTemplate;

    public void test(){
        goodsService.insertEntity();
    }

    //list 测试

    public void setList(){
        List<Goods> list = new ArrayList<>();
        list.add(new Goods("1","a",100));
        list.add(new Goods("2","b",200));
        list.add(new Goods("3","c",300));
        list.add(new Goods("4","d",400));
        BoundListOperations boundListOperations = redisTemplate.boundListOps(REDIS_ENTITY_PRO + "list");
        list.forEach(x->{
            boundListOperations.leftPush(x);
        });
    }

    public void getList(){
        BoundListOperations boundListOperations = redisTemplate.boundListOps(REDIS_ENTITY_PRO + "list");
        List<Goods> list = (List)boundListOperations.range(0,-1);
        list.forEach(System.out::println);
    }

    // hash 测试
    public void setHash(){
        BoundHashOperations boundHashOperations = redisTemplate.boundHashOps(REDIS_ENTITY_PRO + "hash");
        boundHashOperations.put("total",999);
    }

    public void getHash(){
        BoundHashOperations boundHashOperations = redisTemplate.boundHashOps(REDIS_ENTITY_PRO + "hash");
        Object total = boundHashOperations.get("total");
        System.out.println(total);
    }


}
