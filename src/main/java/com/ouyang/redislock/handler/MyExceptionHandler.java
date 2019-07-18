package com.ouyang.redislock.handler;

import com.alibaba.fastjson.JSONObject;
import com.ouyang.redislock.entity.ResponseBody;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author oy
 * @description 全局拦截器处理异常
 * @date 2019/7/18
 */
@Component
@ControllerAdvice
public class MyExceptionHandler implements HandlerInterceptor {

    private void writeResp(HttpServletResponse response, ResponseBody res) throws IOException {
        byte[] resBytes = JSONObject.toJSONString(res).getBytes(StandardCharsets.UTF_8);
        response.setStatus(res.getCode());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getOutputStream().write(resBytes);
    }

}
