package com.ouyang.redislock.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ouyang.redislock.entity.Goods;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface GoodsDao extends BaseMapper<Goods>{

    @Update(" update goods set goods_num = goods_num - 1 where id = #{goodsId} ")
    void kill(@Param("goodsId") Long goodsId);

    @Select(" select * from goods where id = #{id} ")
    Goods getGoodsById(@Param("id") int id);

    @Select("<script> select * from goods where id in <foreach collection=\"lists\" item=\"item\" open=\"(\" close=\")\" separator=\",\">#{item}</foreach></script>")
    List<Goods> getGoodsListByCache(@Param("lists") List<Integer> list);
}
