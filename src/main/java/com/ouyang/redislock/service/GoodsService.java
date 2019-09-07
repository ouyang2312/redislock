package com.ouyang.redislock.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ouyang.redislock.annotation.RedisLock;
import com.ouyang.redislock.cache.GoodsCache;
import com.ouyang.redislock.dao.GoodsDao;
import com.ouyang.redislock.entity.Goods;
import com.ouyang.redislock.exception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author oy
 * @description
 * @date 2019/7/16
 */
@Service
public class GoodsService extends ServiceImpl<GoodsDao, Goods> {

    @Autowired
    RedisLockService redisLockService;

    @Autowired
    GoodsCache goodsCache;

    private static final int TIMEOUT = 10 * 1000;//10s

    /**
     * 逻辑
     * @author oy
     * @date 2019/7/18
     * @param goodsId
     * @return void
     */
    public void kill(Long goodsId) throws Exception{
        long timeOut = System.currentTimeMillis() + TIMEOUT;
        //锁住
        boolean lock = redisLockService.lock(String.valueOf(goodsId), String.valueOf(timeOut));
        //一会儿加 while 线程sleep试试
        if(!lock){
           throw new MyException(9999,"太忙了",null);
        }
        //请求获取当前的商品数量 判断
        Goods goods = baseMapper.selectById(goodsId);
        if(goods != null){
            Integer goodsNum = goods.getGoodsNum();
            if(goodsNum > 0){
                //每次请求 数量 -1
                baseMapper.kill(goodsId);
            }else{
                System.out.println("库存不足！");
            }
        }
        //解锁
        redisLockService.unlock(String.valueOf(goodsId), String.valueOf(timeOut));
    }


    /**
     * 注解
     * @author oy
     * @date 2019/7/18
     * @param goodsId
     * @return void
     */
    @RedisLock(value = "#goodsId" )
    public void kill2(Long goodsId) throws MyException {
        //请求获取当前的商品数量 判断
        Goods goods = baseMapper.selectById(goodsId);
        if(goods != null){
            Integer goodsNum = goods.getGoodsNum();
            if(goodsNum > 0){
                //每次请求 数量 -1
                baseMapper.kill(goodsId);
            }else{
                System.out.println("库存不足！");
            }
        }
    }

    public void insertEntity(){
        Goods goods = new Goods();
        goods.setGoodsName("测试websocket")
                .setGoodsNum(999);
        save(goods);
        System.out.println("保存成功");
    }


    public void getGoodsByCache(int id) {
        Goods goods = new Goods();
        goods.setId(10020+"");
        Goods goodsById = goodsCache.getGoodsById(id,goods);
        System.out.println(goodsById);
    }

    public void getGoodsListByCache(List<Integer> list) {
        List<Goods> goods = goodsCache.getGoodsListByCache(list);
        goods.forEach(item->{
            System.out.println(item);
        });
    }

    public void deleteGoodsCache() {
        goodsCache.deleteGoodsCache();
    }

    public void deleteGoodsCacheAll() {
        goodsCache.deleteGoodsCacheAll();
    }
}
