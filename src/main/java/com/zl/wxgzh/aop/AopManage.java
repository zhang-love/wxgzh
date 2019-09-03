package com.zl.wxgzh.aop;


import com.alibaba.fastjson.JSONObject;
import com.zl.wxgzh.redis.RedisUtil;
import com.zl.wxgzh.tools.TimeUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.Operation;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 设置切面的几种规则：
 * @Pointcut("target(com.xx)") 匹配实现xx接口的目标对象的方法
 * @Pointcut("bean(*Controller)") 匹配以Controller结尾的bean里的方法
 * @Pointcut("excution(**..find*(Long))") 匹配以find开头而且只有一个Long参数的方法
 * @Pointcut("args(Long)") 匹配只有一个Long参数的方法
 * @Pointcut("execution(**..find*(Long,..))") 匹配以find开头而且第一个参数为Long的方法
 * @Pointcut("args(Long,..)") 匹配第一个参数为Long的方法
 */
@Aspect
@Configuration
public class AopManage {

    Logger logger = LoggerFactory.getLogger(AopManage.class);

    @Resource
    RedisUtil redisUtil;

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void webLog(){}

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws ClassNotFoundException {
//        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = requestAttributes.getRequest();
//        StringBuilder builder = new StringBuilder();
//        //反射获取全类名
//        String name = joinPoint.getTarget().getClass().getName();
//        name = name.substring(name.lastIndexOf(".") + 1,name.length());
//        String method = request.getRequestURI();
//        builder.append(TimeUtil.getDate(System.currentTimeMillis())).append(";class=").append(name).append(";method=").append(method);
//        Object[] args = joinPoint.getArgs();
//        for (Object o: args) {
//            if(o != null) {
//                String s = JSONObject.toJSONString(o);
//                builder.append(";params=").append(s);
//            }
//        }
//        redisUtil.sadd("logList",builder.toString());
//        logger.info(builder.toString());
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String url = request.getRequestURL().toString();
        String uri = request.getRequestURI();
        String method = request.getMethod();
        String userAgent = request.getHeader("User-Agent");
        Cookie cookie = null;
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie c: cookies) {
                if(c.getName().endsWith("Token")) {
                    cookie = c;
                    break;
                }
            }
        }
        String Cookie = cookie == null ? "" : JSONObject.toJSONString(cookie);
        String params = JSONObject.toJSONString(request.getParameterMap());
        String logStr = "";

        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        Class<?> targetClass = Class.forName(className);
        Method[] methods = targetClass.getMethods();
        for (Method m : methods) {
            if(m.getName().equalsIgnoreCase(methodName)) {
                Class<?>[] parameterTypes = m.getParameterTypes();
                if(parameterTypes.length == args.length) {
                    Log annotation = m.getAnnotation(Log.class);
                    if(annotation != null) {
                        logStr = annotation.logStr();
                        break;
                    }
                }
            }
        }
        StringBuilder builder = new StringBuilder();
        builder.append("time:").append(TimeUtil.getDate(System.currentTimeMillis()));
        builder.append(" ,logStr:").append(logStr);
        builder.append(" ,url:").append(url);
        builder.append(" ,uri:").append(uri);
        builder.append(" ,method:").append(method);
        builder.append(" ,params:").append(params);
        builder.append(" ,Cookie:").append(Cookie);
        builder.append(" ,userAgent:").append(userAgent);
        String logResult = builder.toString();
        logger.info(logResult);
        redisUtil.rpush("wxgzh:log:method:param", logResult);
    }

    @AfterReturning(returning = "obj", pointcut = "webLog()")
    public void doAfterReturning(Object obj) {
        StringBuilder builder = new StringBuilder();
        builder.append("time:").append(TimeUtil.getDate(System.currentTimeMillis()));
        builder.append(" ,result:").append(JSONObject.toJSONString(obj));
        String logResult = builder.toString();
        redisUtil.rpush("wxgzh:log:method:result", logResult);
        logger.info(logResult);
    }

    @AfterThrowing(pointcut = "webLog()",throwing = "e")
    public void doAfterThrowing(Throwable e) {
        int i = 1 *2;
        i++;
        logger.info(JSONObject.toJSONString(e));
    }

}
