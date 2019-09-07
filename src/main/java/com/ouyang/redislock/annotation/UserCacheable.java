package com.ouyang.redislock.annotation;

import java.lang.annotation.*;

/**
 * 用户缓存（在全局的配置下 可以用redis全局）
 * @author oy
 * @date 2019/9/6
 * @param
 * @return
 */
@Target({ ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UserCacheable {

    /** 缓存key */
    String key();

    /** 缓存前缀value */
    String value();

}
