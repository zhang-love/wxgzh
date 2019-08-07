package com.zl.wxgzh.accessToken;

public class WechatAuthBean {
    private String timestamp;

    private String signature;

    private String nonce;

    private String echostr;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getEchostr() {
        return echostr;
    }

    public void setEchostr(String echostr) {
        this.echostr = echostr;
    }

    public WechatAuthBean(String timestamp, String signature, String nonce, String echostr) {
        this.timestamp = timestamp;
        this.signature = signature;
        this.nonce = nonce;
        this.echostr = echostr;
    }
}
