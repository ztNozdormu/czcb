/**
 * keydom.com.cn Inc.
 * Copyright (c) 2005-2018 All Rights Reserved.
 */
package cn.com.czcb.pub;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

/**
 * 
 * @author AJiong
 * @version $Id: RedisUtil.java, v 0.1 2018年1月25日 上午10:02:23 Ajiong Exp $
 */
@Component
public class RedisUtil {
    /** 日志对象 */
    private Logger       logger = LoggerFactory.getLogger(getClass());
    /**  */
    @Resource
    private RedisTemplate<Serializable, Object> redisTemplate;

    /**
     * 批量删除对应的value
     * 
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     * 
     * @param pattern
     */
    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0){
            redisTemplate.delete(keys);
        }
    }

    /**
     * 删除对应的value
     * 
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     * 
     * @param key
     * @return boolean
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     * 
     * @param key
     * @return Object
     */
    public Object get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return operations.getAndSet(key,result);
    }

    /**
     * 写入缓存
     * 
     * @param key
     * @param value
     * @return boolean
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            logger.error("set cache error", e);
        }
        return result;
    }

    /**
     * 写入缓存
     * 
     * @param key
     * @param value
     * @param expireTime 
     * @return boolean
     */
    public boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            logger.error("set cache error", e);
        }
        return result;
    }
    
    /**
     * 
     * @param key
     * @param delta
     * @return long
     */
    public long increment(final String key , long delta){
         return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * Setter method for property <tt>redisTemplate</tt>.
     * 
     * @param redisTemplate value to be assigned to property redisTemplate
     */
    /*public void setRedisTemplate(RedisTemplate<Serializable, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }*/

}
