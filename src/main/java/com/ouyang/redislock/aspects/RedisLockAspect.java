package com.ouyang.redislock.aspects;

import com.ouyang.redislock.annotation.RedisLock;
import com.ouyang.redislock.service.RedisLockService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * @author oy
 * @description 商品的分布式锁
 * @date 2019/7/17
 */
@Aspect
@Component
public class RedisLockAspect {

    private static final int TIMEOUT = 10 * 1000;//10s

    @Autowired
    RedisLockService redisLockService;

    @Pointcut("@annotation(com.ouyang.redislock.annotation.RedisLock)")
    public void annotationPointCut(JoinPoint joinPoint) {
    }

    @Before("@annotation(com.ouyang.redislock.annotation.RedisLock)")
    public void before(JoinPoint joinPoint) throws Exception {
        System.out.println("进来了!");
        //方法
        MethodSignature sign = (MethodSignature) joinPoint.getSignature();
        Method method = sign.getMethod();

        //参数具体值
        Object[] args = joinPoint.getArgs();

        RedisLock annotation = method.getAnnotation(RedisLock.class);
        String value = annotation.value();

        //拿到的值 parseKey(value, method, args);
        String goodsId = parseKey(value, method, args);

        //拿到了商品id 去锁住当前的商品
        long timeOut = System.currentTimeMillis() + TIMEOUT;

        //锁住
        boolean lock = redisLockService.lock(String.valueOf(goodsId), String.valueOf(timeOut));
        //一会儿加 while 线程sleep试试

        while (!lock) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean lock1 = redisLockService.lock(String.valueOf(goodsId), String.valueOf(timeOut));
            if (lock1) {
                //放行
                break;
            }
        }
    }

    @After("@annotation(com.ouyang.redislock.annotation.RedisLock)")
    public void after(JoinPoint joinPoint){
        //方法
        MethodSignature sign = (MethodSignature) joinPoint.getSignature();
        Method method = sign.getMethod();

        //参数具体值
        Object[] args = joinPoint.getArgs();

        RedisLock annotation = method.getAnnotation(RedisLock.class);
        String value = annotation.value();

        //拿到的值
        String goodsId = parseKey(value, method, args);
        //解锁
        long timeOut = System.currentTimeMillis() + TIMEOUT;
        redisLockService.unlock(String.valueOf(goodsId), String.valueOf(timeOut));

    }

    /**
     * 获取缓存的key
     * key 定义在注解上，支持SPEL表达式
     *
     * @return
     */
    private String parseKey(String key, Method method, Object[] args) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        //获取被拦截方法参数名列表(使用Spring支持类库)
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paraNameArr = u.getParameterNames(method);

        //使用SPEL进行key的解析
        ExpressionParser parser = new SpelExpressionParser();
        //SPEL上下文
        StandardEvaluationContext context = new StandardEvaluationContext();
        //把方法参数放入SPEL上下文中
        for (int i = 0; i < paraNameArr.length; i++) {
            context.setVariable(paraNameArr[i], args[i]);
        }
        return parser.parseExpression(key).getValue(context, String.class);
    }
}
