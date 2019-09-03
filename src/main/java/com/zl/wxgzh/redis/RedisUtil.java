package com.zl.wxgzh.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import javax.annotation.Resource;
import java.util.Map;

@Component
public class RedisUtil {
    @Resource
    JedisPool jedisPool;

    Logger logger = LoggerFactory.getLogger(RedisUtil.class);


    /**
     * string 类型操作
     * @param key
     * @return
     */
    public String get(String key) {
        return this.get(key,0);
    }
    public String set(String key,String value) {
        return this.set(key,value,0);
    }
    public String get(String key, int db) {
        String value = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(db);
            value = jedis.get(key);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
        return value;
    }

    public String set(String key, String value, int db) {
        String result = null;
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            jedis.select(db);
            result = jedis.set(key, value);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    public Long sadd(String list,String... value){
        return this.sadd(list,0,value);
    }
    public Long sadd(String list, int db, String... value) {
        Long result = null;
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            jedis.select(db);
            result = jedis.sadd(list,value);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    public Long rpush(String list,String value){
        return rpush(list,value,0);
    }
    public Long rpush(String list, String value, int db){
        Long result = null;
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            jedis.select(db);
            result = jedis.rpush(list, new String[]{value});
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    public Long zadd(String key,Map<String,Double> value) {
        return zadd(key,value,0);
    }

    public Long zadd(String key, Map<String,Double> value, int db) {
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(db);
            result = jedis.zadd(key,value);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    public Long hset(String key, String field, String value) {
        return hset(key, field, value, 0);
    }

    public Long hset(String key, String field, String value, int db) {
        Jedis jedis = null;
        Long result = null;
        try{
            jedis = jedisPool.getResource();
            jedis.select(db);
            result = jedis.hset(key, field, value);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    public String hmset(String key, Map<String,String> map) {
        return hmset(key,map,0);
    }

    public String hmset(String key, Map<String,String> map, int db) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(db);
            result = jedis.hmset(key, map);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return result;
    }

}
