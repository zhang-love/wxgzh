package com.zl.wxgzh.tools.factory;

import com.zl.wxgzh.tools.numberSwitch.NumberSwitch;

public class IdFactory {

    public static Long getLongId() {
        String time = Long.toBinaryString(System.currentTimeMillis());
        String random = NumberSwitch.longToBinary(new Double(Math.random() * Math.pow(2,20)).longValue(), 20, '0');
        if(time.length() <= 44) {
            return Long.parseLong(time + random, 2);
        }
        return null;
    }
}
