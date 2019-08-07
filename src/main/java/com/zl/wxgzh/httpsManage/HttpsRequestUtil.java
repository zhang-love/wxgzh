package com.zl.wxgzh.httpsManage;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

/**
 * 发送https请求
 */
@Component
public class HttpsRequestUtil {
    Logger logger = LoggerFactory.getLogger(HttpsRequestUtil.class);

    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String output){
        JSONObject jsonObject = null;
        try{
            TrustManager[] trustManagers = {new CertificateManage()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, trustManagers, new SecureRandom());
            SSLSocketFactory sslSocket = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.setSSLSocketFactory(sslSocket);
            httpsURLConnection.setDoOutput(true);
            httpsURLConnection.setDoInput(true);
            httpsURLConnection.setUseCaches(false);
            httpsURLConnection.setRequestMethod(requestMethod);
            if("GET".equalsIgnoreCase(requestMethod)){
                httpsURLConnection.connect();
            }
            if(null != output) {
                OutputStream outputStream = httpsURLConnection.getOutputStream();
                outputStream.write(output.getBytes("UTF-8"));
                outputStream.close();
            }

            InputStream inputStream = httpsURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer stringBuffer = new StringBuffer();
            while((str = bufferedReader.readLine()) != null) {
                stringBuffer.append(str);
            }
            bufferedReader.close();
            inputStream = null;
            httpsURLConnection.disconnect();
            jsonObject = JSONObject.parseObject(stringBuffer.toString());
            System.out.println("jsonObject ->" +jsonObject);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
