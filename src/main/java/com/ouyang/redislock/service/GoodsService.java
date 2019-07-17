package com.ouyang.redislock.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ouyang.redislock.dao.GoodsDao;
import com.ouyang.redislock.entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author oy
 * @description
 * @date 2019/7/16
 */
@Service
public class GoodsService extends ServiceImpl<GoodsDao, Goods> {

    @Autowired
    RedisLockService redisLockService;

    private static final int TIMEOUT = 10 * 1000;//10s

    public void kill(Long goodsId) throws Exception{
        long timeOut = System.currentTimeMillis() + TIMEOUT;
        //锁住
        boolean lock = redisLockService.lock(String.valueOf(goodsId), String.valueOf(timeOut));
        //一会儿加 while 线程sleep试试
        while(!lock){
            Thread.sleep(200);
            boolean lock1 = redisLockService.lock(String.valueOf(goodsId), String.valueOf(timeOut));
            if(lock1){
                break;
            }
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
//        //解锁
        redisLockService.unlock(String.valueOf(goodsId), String.valueOf(timeOut));
    }
}
