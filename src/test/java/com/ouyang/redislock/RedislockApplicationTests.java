package com.ouyang.redislock;

import com.ouyang.redislock.entity.Goods;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedislockApplicationTests {

    private static final String REDIS_ENTITY_PRO = "real-time-statistics";

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void contextLoads() {
        //往redis 中存入一个变量数字
        stringRedisTemplate.opsForValue().set("lockKey", 20 + "", 10, TimeUnit.SECONDS);
    }

    /*
      新增用户数  首次访问小程序页面的用户数，同一用户多次访问不重复计；
     */

    @Test
    public void setList(){
        List<Goods> list = new ArrayList<>();
        list.add(new Goods("1","a",100));
        list.add(new Goods("2","b",200));
        list.add(new Goods("3","c",300));
        list.add(new Goods("4","d",400));
        BoundListOperations boundListOperations = redisTemplate.boundListOps("REDIS_ENTITY_PRO" + "list");
        list.forEach(x->{
            boundListOperations.leftPush(x);
        });
    }

    @Test
    public void getList(){
        BoundListOperations boundListOperations = redisTemplate.boundListOps(REDIS_ENTITY_PRO + "list");
        List<Goods> list = (List)boundListOperations.leftPop();
        list.forEach(System.out::println);
    }

}
