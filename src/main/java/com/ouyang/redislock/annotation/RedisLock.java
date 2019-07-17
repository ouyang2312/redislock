package com.ouyang.redislock.annotation;

import java.lang.annotation.*;

/**
 * @author oy
 * @description 商品锁
 * @date 2019/7/17
 */
@Target({ ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisLock {

    /**  存放当前商品id */
    String value();

}
