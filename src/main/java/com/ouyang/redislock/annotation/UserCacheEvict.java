package com.ouyang.redislock.annotation;

import java.lang.annotation.*;

/**
 * 用户缓存清除
 * @author oy
 * @date 2019/9/6
 * @param
 * @return
 */
@Target({ ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UserCacheEvict {

    /** 缓存前缀value */
    String value();

}
