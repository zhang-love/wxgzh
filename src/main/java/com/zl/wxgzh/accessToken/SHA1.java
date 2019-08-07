package com.zl.wxgzh.accessToken;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA1 {
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    
    public static String encode(String str) {
        if(str == null) {
            return null;
        }
        try{
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.update(str.getBytes());
            return getFormattedText(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static String getFormattedText(byte[] bytes) {
        int length = bytes.length;
        StringBuilder buf = new StringBuilder(length * 2);
        for (byte b: bytes) {
            buf.append(HEX_DIGITS[(b >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[b & 0x0f]);
        }
        return buf.toString();
    }
}
