package com.zl.wxgzh.tools;

import org.springframework.web.client.RestTemplate;

public class HttpUtil {

    public static String doGet(String url){
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);
        System.out.println("result=" + result);
        return result;
    }
}
