package com.ouyang.redislock.cache;

import com.ouyang.redislock.annotation.UserCacheEvict;
import com.ouyang.redislock.annotation.UserCacheable;
import com.ouyang.redislock.dao.GoodsDao;
import com.ouyang.redislock.entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author oy
 * @description
 * @date 2019/9/6
 */
@Component
@CacheConfig(cacheNames = "Goods")
public class GoodsCache {

    @Autowired
    GoodsDao goodsDao;

//    @Cacheable(value = "Goods" , key = "'getGoodsById_'+#id")
    @UserCacheable(value = "Goods" , key = "'getGoodsById_'+'_'+#id+'_'+#goods.getId()")
    public Goods getGoodsById(int id, Goods goods){
        return goodsDao.getGoodsById(id);
    }

    @UserCacheable(value = "Goods" , key = "'getGoodsListByCache_'+#list")
    public List<Goods> getGoodsListByCache(List<Integer> list) {
        return goodsDao.getGoodsListByCache(list);
    }

    @UserCacheEvict(value = "Goods")
    public void deleteGoodsCache() {
        //随便写点东西
    }

    @CacheEvict(value = "Goods",allEntries = true)
    public void deleteGoodsCacheAll() {
        //随便写
    }
}
