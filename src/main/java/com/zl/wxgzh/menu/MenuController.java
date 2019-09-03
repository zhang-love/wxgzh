package com.zl.wxgzh.menu;

import com.alibaba.fastjson.JSONObject;
import com.zl.wxgzh.constants.WechatConstants;
import com.zl.wxgzh.httpsManage.HttpsRequestUtil;
import com.zl.wxgzh.redis.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class MenuController {

    Logger logger = LoggerFactory.getLogger(MenuController.class);

    @Resource
    WechatConstants wechatTokenBean;

    @Resource
    RedisUtil redisUtil;

    @GetMapping("/menus")
    public boolean menus(){
        MenuUtil menuUtil = new MenuUtil();
        Menu menu = null;

        String accessToken = redisUtil.get("wxgzh:access_token");
        logger.info("access_token : " + accessToken);
        boolean result = false;
        String url = wechatTokenBean.getMenuCreateUrl().replace("ACCESS_TOKEN",accessToken);
        String jsonMenu = JSONObject.toJSONString(menu);
        logger.info("url : " + url);
        logger.info("jsonMenu : " + jsonMenu);
        JSONObject jsonObject = HttpsRequestUtil.httpsRequest(url, "POST", jsonMenu);
        if(jsonObject != null) {
            logger.info("jsonObject : " + jsonObject.toJSONString());
            Integer errcode = jsonObject.getInteger("errcode");
            String errmsg = jsonObject.getString("errmsg");
            if(errcode == 0){
                result = true;
            } else {
                result = false;
                logger.info("创建菜单失败 errcode:{} errmsg:{}" ,errcode, errmsg);
            }
        }
        return result;
    }
}
