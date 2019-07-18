package com.ouyang.redislock.handler;

import com.ouyang.redislock.entity.ResponseBody;
import com.ouyang.redislock.exception.MyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author oy
 * @description
 * @date 2019/7/18
 */
@ControllerAdvice
public class MyExceptionHandler {

    @org.springframework.web.bind.annotation.ResponseBody
    @ExceptionHandler({MyException.class})
    public ResponseBody handleArithmeticException(Exception ex, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ResponseBody responseBody = new ResponseBody(9999, "活动太火爆了，请重新尝试！");
        return responseBody;
    }
}