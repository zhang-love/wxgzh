package com.zl.wxgzh.accessToken;

import com.alibaba.fastjson.JSONObject;
import com.zl.wxgzh.constants.WechatConstants;
import com.zl.wxgzh.httpsManage.HttpsRequestUtil;
import com.zl.wxgzh.redis.RedisUtil;
import com.zl.wxgzh.tools.CheckUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class AccessTokenController {

    @Resource
    WechatConstants wechatConstants;

    @Resource
    RedisUtil redisUtil;

//    @RequestMapping("/accessToken")
//    public void accessToken(WechatAuthBean authBean){
//        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
//        HttpServletResponse response = requestAttributes.getResponse();
//        PrintWriter printWriter = null;
//        try {
//            printWriter = response.getWriter();
//            if(CheckUtils.checkSignature(authBean.getSignature(),authBean.getTimestamp(),authBean.getNonce())){
//                printWriter.write(authBean.getEchostr());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            printWriter.close();
//        }
//    }

    /**
     * 微信平台验证
     * @param authBean
     * @return
     */
    @RequestMapping("/auth")
    public String auth(WechatAuthBean authBean){
        if(CheckUtils.checkSignature(authBean.getSignature(),authBean.getTimestamp(),authBean.getNonce())){
            return authBean.getEchostr();
        }
        return "error";
    }

    /**
     * 调用https://api.weixin.qq.com/cgi-bin/token,获取accessToken
     * @return
     */
    @RequestMapping("/accessToken")
    public String accessToken(){
        return getAccessToken();
    }

    public String getAccessToken() {
        String url = wechatConstants.getAccessTokenUrl();
        String newUrl = url.replace("APPID", wechatConstants.getAppid())
                .replace("APPSECRET", wechatConstants.getAppsecret());
        JSONObject jsonObject = HttpsRequestUtil.httpsRequest(newUrl, "GET", null);
        String accessToken = jsonObject.getString("access_token");
        redisUtil.set("wxgzh:access_token", accessToken);
        return accessToken;
    }
}
