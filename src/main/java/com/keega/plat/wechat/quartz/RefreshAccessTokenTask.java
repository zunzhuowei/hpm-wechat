package com.keega.plat.wechat.quartz;

import com.alibaba.fastjson.JSON;
import com.keega.plat.wechat.log.WriteLogUtil;
import com.keega.plat.wechat.util.WeiXinConstant;
import com.keega.plat.wechat.util.http.HttpsGetClient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 因为token是每隔7200秒更新一次，所以需要自动刷新获取，
 * 且每天获取token次数有限制，故需要自动定时刷新获取新token
 * <p>
 * Created by zun.wei on 2016/12/6.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Component
public class RefreshAccessTokenTask {

    private static int count = 0;

    //刷新获取access token
    public void refreshAccessToken() throws IOException, KeyManagementException, NoSuchAlgorithmException {
        HttpClient httpClient = null;
        HttpGet httpGet = null;
        String result = null;
        try {
            if (WeiXinConstant.isOpentRefresh) {//如果开启则去微信api那里获取access token
                System.out.println("--------------refresh Access Token !-----------------");
                //client = HttpClients.createDefault();//这个发送不了https://
                httpClient = new HttpsGetClient();//可以发送https://
                httpGet = new HttpGet(getAccessTokenGetUrl());
                HttpResponse response = httpClient.execute(httpGet);
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode >= 200 && statusCode < 300) {
                    HttpEntity resEntity = response.getEntity();
                    if (resEntity != null) {
                        result = EntityUtils.toString(resEntity, "utf-8");
                        saveAccessToken(result);
                    }
                }
            } else {//如果关闭去本地tempAccessToken.txt去找access token
                WeiXinConstant.setAccessToken(TempAccessTokenTask.getTempAccessToken());
            }
        } finally {
            if (httpGet != null) {
                httpGet.releaseConnection();
            }
        }
    }

    //保存AccessToken
    //{"access_token":"ACCESS_TOKEN","expires_in":7200}   成功的时候
    //{"errcode":40013,"errmsg":"invalid appid"}    失败的时候
    private void saveAccessToken(String content) throws IOException, KeyManagementException, NoSuchAlgorithmException {
        Map<String, Object> map = JSON.parseObject(content, Map.class);//返回的json字符串转成map
        String accessToken = map.get("access_token") + "";
        String errcode = "errcode:" + map.get("errcode") + "    errmsg:" + map.get("errmsg");
        if ("null".equals(accessToken)) {//如果获取不到access token的话，把错误信息写入日志。
            //写入日志
            SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd");
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            WriteLogUtil.writeLog2File(format.format(new Date()), format1.format(new Date()), errcode);
            tryAgainRefreshAccessToken();//尝试重新发送
        } else {
            TempAccessTokenTask.saveTempAccessToken(accessToken);//保存一份到临时文件
            WeiXinConstant.setAccessToken(accessToken);
            reSetCount4RefreshAccessToken();//重置计数器
        }
    }


    //获取get请求的url用于获取accessToken
    private String getAccessTokenGetUrl() {
        String url = WeiXinConstant.URL;
        url = url.replace("APPID", WeiXinConstant.APP_ID);
        url = url.replace("APPSECRET", WeiXinConstant.APP_SECRET);
        return url;
    }

    //尝试再次刷新
    private void tryAgainRefreshAccessToken() throws IOException,NoSuchAlgorithmException,
            KeyManagementException {
        if (count < 3) {
            count++;
            refreshAccessToken();
        }
    }

    //重置计数器
    private void reSetCount4RefreshAccessToken() {
        if (count != 0) count = 0;
    }

}
