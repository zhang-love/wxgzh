package com.zl.wxgzh.redis;

import com.zl.wxgzh.aop.Log;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class RedisController {

    @Resource
    RedisUtil redisUtil;

    @Log(logStr = "测试redis功能")
    @RequestMapping("/test")
    public String test(String key, String value) throws Exception{
        String s = redisUtil.set(key, value, 1);
        String result = redisUtil.get(key,1);
        return result;
    }
}

