package com.monolithic.web.rest.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

import java.util.concurrent.TimeUnit;

public class RedisUtil {

    public static Long increment(String key, Long liveTime, RedisTemplate redisTemplate) {
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        Long increment = entityIdCounter.getAndIncrement();
        if ((null == increment || increment.longValue() == 0) && null != liveTime && liveTime > 0) {
            entityIdCounter.expire(liveTime, TimeUnit.SECONDS);
        }
        return increment;
    }

    public static void removeAtomicLong(String key, RedisTemplate redisTemplate) {
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        entityIdCounter.expire(0, TimeUnit.SECONDS);
    }
}


