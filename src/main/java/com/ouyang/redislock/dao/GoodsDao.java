package com.ouyang.redislock.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ouyang.redislock.entity.Goods;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

@Component
public interface GoodsDao extends BaseMapper<Goods>{

    @Update(" update goods set goods_num = goods_num - 1 where id = #{goodsId} ")
    void kill(@Param("goodsId") Long goodsId);

}
