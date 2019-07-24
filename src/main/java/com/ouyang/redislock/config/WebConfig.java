package com.ouyang.redislock.config;

import com.ouyang.redislock.Interceptor.CountIntercepter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author oy
 * @description web配置
 * @date 2019/7/24
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 添加自定义拦截器
     * @author oy
     * @date 2019/7/24
     * @param registry
     * @return void
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CountIntercepter()).addPathPatterns("/**");
    }


}
