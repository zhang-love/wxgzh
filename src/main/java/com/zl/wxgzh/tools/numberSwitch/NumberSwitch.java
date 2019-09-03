package com.zl.wxgzh.tools.numberSwitch;

public class NumberSwitch {

    public static String longToBinary(Long x, int size, char y) {
        String binaryString = Long.toBinaryString(x);
        StringBuffer result = new StringBuffer();
        for(int i = 0; i < size - binaryString.length(); i++) {
            result.append(y);
        }
        return result.append(binaryString).toString();
    }
}
