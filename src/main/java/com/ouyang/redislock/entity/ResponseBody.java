package com.ouyang.redislock.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author oy
 * @description 返回消息体
 * @date 2019/7/17
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseBody {

    private int code;
    private String msg;

}
