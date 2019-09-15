package com.ouyang.redislock.handler;

import com.ouyang.redislock.entity.ResponseBody;
import com.ouyang.redislock.exception.MyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author oy
 * @description
 * @date 2019/7/18
 */
@org.springframework.web.bind.annotation.ResponseBody
@ControllerAdvice
public class MyExceptionHandler {

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({MyException.class})
    public ResponseBody handleArithmeticException(Exception ex, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String message = ex.getMessage();
        ResponseBody responseBody = new ResponseBody(9999, message);
        return responseBody;
    }
}
