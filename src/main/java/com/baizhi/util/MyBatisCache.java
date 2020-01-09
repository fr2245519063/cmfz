package com.baizhi.util;

import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;
import java.util.concurrent.locks.ReadWriteLock;

public class MyBatisCache implements Cache {
    //mybatis必须有一个ID属性
    private final String id;
    //因为final修饰的类所以需要提供有参构造
    public MyBatisCache(String id) {
        this.id = id;
    }


    @Override
    public String getId() {
        return this.id;
    }
    //添加缓存 2
    @Override
    public void putObject(Object key, Object value) {
        RedisTemplate redisTemplate = (RedisTemplate) MyWebWare.getBeanByName("redisTemplate");
        //通过redistempalte对象操作redis
        //存储时使用hash类型存储类型 方便数据更改删除方便删除namepace的所有数据
        //为什么要使用hash存储因为hash里面存储的是一个map 但是map里面还可以存储一个小的map先存储一个id这个id是用户的方法名
        redisTemplate.opsForHash().put(this.id,key.toString(),value);
    }
    //获取缓存 1
    @Override
    public Object getObject(Object key) {
        //先获取到redistemplate类通过这个类可以调用redis这个组件
       RedisTemplate redisTemplate = (RedisTemplate) MyWebWare.getBeanByName("redisTemplate");
       //根据大建获取到里面的小件再将里面的值返回
        Object o = redisTemplate.opsForHash().get(this.id, key.toString());
        return o;
    }

    @Override
    public Object removeObject(Object key) {
        return null;
    }
    //删除方法
    @Override
    public void clear() {
        RedisTemplate redidTemplate = (RedisTemplate) MyWebWare.getBeanByName("redidTemplate");
        //删除的话。因为咋们存储的是大建是方法名 所以才可以直接删除大建
        redidTemplate.delete(this.id);
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return null;
    }
}
