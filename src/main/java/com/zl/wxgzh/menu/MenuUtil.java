package com.zl.wxgzh.menu;

import com.zl.wxgzh.redis.RedisUtil;
import com.zl.wxgzh.tools.factory.IdFactory;
import com.zl.wxgzh.tools.numberSwitch.NumberSwitch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Random;
import java.util.function.BinaryOperator;

@Component
public class MenuUtil {
    Logger logger = LoggerFactory.getLogger(MenuUtil.class);

    @Resource
    RedisUtil redisUtil;

    @Resource
    JedisPool jedisPool;

    public void initMenu() {
        Jedis jedis = jedisPool.getResource();
        jedis.select(0);
        try{
            if(jedis.setnx("wxgzh:menu:lock", "1") == 1) {
                HashMap<String, String> buttonMap = new HashMap<String, String>();
                Long id = IdFactory.getLongId();
                String ids = String.valueOf(id);
                buttonMap.put("name", "外部链接");
                buttonMap.put("type", "click");
                Pipeline pipeline = jedis.pipelined();
                pipeline.rpush("wxgzh:menu:list",ids);
                pipeline.hmset("wxgzh:menu:" + ids, buttonMap);
                HashMap<String, String> buttonMap1 = new HashMap<String, String>();
                Long id1 = IdFactory.getLongId();
                String ids1 = String.valueOf(id1);
                buttonMap1.put("name", "外部链接1");
                buttonMap1.put("type", "click");
                pipeline.rpush("wxgzh:menu:list",ids1);
                pipeline.hmset("wxgzh:menu:" + ids1, buttonMap1);
                pipeline.sync();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            jedis.del("wxgzh:menu:lock");
        }

    }
    public void getMenu(){
        Jedis jedis = jedisPool.getResource();
        if(jedis.setnx("wxgzh:menu:lock", "1") == 1) {
            Pipeline pipeline = jedis.pipelined();
            System.out.println(jedis.lrange("wxgzh:menu:list",0, -1));
        }
    }
}
