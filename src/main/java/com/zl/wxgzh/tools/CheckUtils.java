package com.zl.wxgzh.tools;



import com.zl.wxgzh.accessToken.SHA1;

import java.util.Arrays;

public class CheckUtils {

    private static final String token = "zhanglun";

    public static boolean checkSignature(String signature,String timestamp,String nonce) {
        String[] str = {token, timestamp, nonce};
        Arrays.sort(str);
        StringBuffer buf = new StringBuffer();
        for (String s : str) {
            buf.append(s);
        }
        String temp = SHA1.encode(buf.toString());
        return signature.equals(temp);
    }
}
