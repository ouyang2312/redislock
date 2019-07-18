package com.ouyang.redislock.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author oy
 * @description
 * @date 2019/7/18
 */
@Data
@NoArgsConstructor
public class MyException extends RuntimeException {

    private static final long serialVersionUID = 194906846739586856L;

    protected int code;

    protected String message;

    protected Object data;

    public MyException(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

}
