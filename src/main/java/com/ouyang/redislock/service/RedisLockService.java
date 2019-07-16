package com.ouyang.redislock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author oy
 * @description
 * @date 2019/7/16
 */
@Service
public class RedisLockService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 加锁
     * @author oy
     * @date 2019/7/16
     * @param key, value
     * @return boolean
     */
    public boolean lock(String key,String value){
        if(stringRedisTemplate.opsForValue().setIfAbsent(key,value)){
            return true;
        }
        String currentValue = stringRedisTemplate.opsForValue().get(key);
        //如果锁过期
        if(!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue) < System.currentTimeMillis()){
            //获取上一个锁的时间
            String oldValue = stringRedisTemplate.opsForValue().getAndSet(key, value);
            if(!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)){
                return true;
            }
        }
        return false;
    }

    /**
     * 解锁
     * @author oy
     * @date 2019/7/16
     * @param key, value
     * @return void
     */
    public void unlock(String key,String value){
        try {
            String currentValue = stringRedisTemplate.opsForValue().get(key);
            if(!StringUtils.isEmpty(currentValue) && currentValue.equals(value)){
                stringRedisTemplate.opsForValue().getOperations().delete(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("解锁异常！");
        }
    }

}
