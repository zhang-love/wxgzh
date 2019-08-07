package com.zl.wxgzh.accessToken;

import java.util.Map;

public class UrlUtil {

    public static String spliceUrl(String url, Map<String,String> map){
        StringBuilder builder = new StringBuilder(url);
        int size = map.size();
        int i = 0;
        for (Map.Entry<String,String> entry: map.entrySet()) {
            if(i == 0) {
                builder.append("?");
            }
            builder.append(entry.getKey());
            builder.append("=");
            builder.append(entry.getValue());
            if(i < size - 1){
                builder.append("&");
            }
            i++;
        }
        return builder.toString();
    }
}
