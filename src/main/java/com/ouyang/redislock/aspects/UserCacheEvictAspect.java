package com.ouyang.redislock.aspects;

import com.ouyang.redislock.annotation.UserCacheEvict;
import com.ouyang.redislock.annotation.UserCacheable;
import com.ouyang.redislock.utils.SerializeUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * @author oy
 * @description 清除用户的缓存
 * @date 2019/9/6
 */
@Aspect
@Component
public class UserCacheEvictAspect {

    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    RedisTemplate redisTemplate;


    @Pointcut("@annotation(com.ouyang.redislock.annotation.UserCacheEvict)")
    public void annotationPointCut() {
    }

    @Around("annotationPointCut()")
    private Object around(JoinPoint joinPoint) throws Throwable {
        ProceedingJoinPoint tempJoinPoint = (ProceedingJoinPoint) joinPoint;
        //方法
        MethodSignature sign = (MethodSignature) joinPoint.getSignature();
        Method method = sign.getMethod();

        //参数具体值
        UserCacheEvict annotation = method.getAnnotation(UserCacheEvict.class);
        String value = annotation.value();


        //拿用户 uid
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String token = request.getHeader("token");
        String key = value + "::" + token + "_";
        Object obj = tempJoinPoint.proceed();
        Set<String> keys = redisTemplate.keys(key + "*");
        Set<String> keys1 = redisTemplate.keys("Goods::1001_" + "*");
        //删除
        if (!CollectionUtils.isEmpty(keys)) {
            redisTemplate.delete(keys);
        }
        return obj;
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
