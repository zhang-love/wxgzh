package com.zl.wxgzh.task;


import com.zl.wxgzh.accessToken.AccessTokenController;
import com.zl.wxgzh.redis.RedisUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@EnableScheduling
@Configuration
public class TimeTask implements SchedulingConfigurer {

    @Resource
    AccessTokenController authController;

    @Resource
    RedisUtil redisUtil;
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.addTriggerTask(new Runnable() {
            @Override
            public void run() {
                try {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
                    System.out.println("++" + simpleDateFormat.format(System.currentTimeMillis()));
                    authController.getAccessToken();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                String token = redisUtil.get("wxgzh:access_token");
                if("".equals(token) || token == null) {
                   return null;
                }
                CronTrigger cronTrigger = new CronTrigger("0 0 */1 * * ?");
                return cronTrigger.nextExecutionTime(triggerContext);
            }
        });
    }
}
