package com.ouyang.redislock.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author oy
 * @description
 * @date 2019/7/16
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("Goods")
public class Goods implements Serializable {

    @TableId(value = "id",type = IdType.AUTO)
    private String id;

    @TableField(value = "goods_name")
    private String goodsName;

    @TableField(value = "goods_num")
    private Integer goodsNum;

}
