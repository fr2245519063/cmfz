package com.baizhi.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CacheAspect {
    @Autowired
    RedisTemplate redisTemplate;
    @Around(value = "@annotation(com.baizhi.util.SelectCache)")
    public Object addCache(ProceedingJoinPoint proceedingJoinPoint){
        //Key(大建) ：原始类的全限定名  key(小件)：为方法名+参数
        //通过proceedingJoinPoint.getTarget()拿到类再通过反射.getclass.toGenericString拿到类的名字
        String clazz= proceedingJoinPoint.getTarget().getClass().toGenericString();
        System.out.println(clazz);
        //再通过proceedingJoinPoint。getsignature.getname 拿到方法名
        String name = proceedingJoinPoint.getSignature().getName();
        //getArgs拿到参数列表  参数列表可以有多个所以为数组
        Object[] args = proceedingJoinPoint.getArgs();
        //首先将小件定义起来
        String key=name;
        //接着在遍历参数
        for (int i = 0; i < args.length; i++) {
            //将小件的方法名再将参数列表拼接起来
            Object arg = args[i];
            key +=arg;
        }
        //查询数据库之前 先查询redis中的缓存数据  将大建和小件储存起来
        Object o = redisTemplate.opsForHash().get(clazz, key);
        //如果查到直接返回缓存数据    进行判断 如果在内存中存在的话就直接返回 不存在就去数据库中查找
            if(o!=null){
                return o;
            }
        //r如果查不到就去数据库查询  返回之后将数据添加到缓存中
        //proceed()放行方法
        try {
            //因为是环绕通知 所以要先放行目标方法
            Object proceed = proceedingJoinPoint.proceed();
            //放行  之后在数据库中查好数据之后再将数据存到redis中
            redisTemplate.opsForHash().put(clazz,key,proceed);
            //最后返回
            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }

    }


    @Around(value = "@annotation(com.baizhi.util.ClearCache)")
    public Object delete(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String s = proceedingJoinPoint.getTarget().getClass().toGenericString();
        redisTemplate.delete(s);
        return proceedingJoinPoint.proceed();
    }
}
