package com.keega.plat.wecp.util.http;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * http请求工具类
 *
 * Created by zun.wei on 2016/12/24.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Component
public class HttpRequestUtil {

    /**
     * get请求
     * @param urlWithParams 请求的url和参数
     * @throws IOException 异常
     */
    public void get(String urlWithParams) throws IOException {
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpGet httpget = new HttpGet(urlWithParams);
        httpget.addHeader("Content-Type", "text/html;charset=UTF-8");
        //配置请求的超时设置
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(50)
                .setConnectTimeout(50)
                .setSocketTimeout(50).build();
        httpget.setConfig(requestConfig);

        CloseableHttpResponse response = httpclient.execute(httpget);
        System.out.println("StatusCode -> " + response.getStatusLine().getStatusCode());

        HttpEntity entity = response.getEntity();
        String jsonStr = EntityUtils.toString(entity);
        System.out.println(jsonStr);

        httpget.releaseConnection();
    }

    /**
     * post请求
     * @param url post的url
     * @param params BasicNameValuePair(final String name, final String value) 请求参数
     * @throws IOException 异常
     */
    public void post(String url, List<NameValuePair> params) throws IOException {
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();

        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));

        CloseableHttpResponse response = httpclient.execute(httppost);
        System.out.println(response.toString());

        HttpEntity entity = response.getEntity();
        String jsonStr = EntityUtils.toString(entity, "utf-8");
        System.out.println(jsonStr);

        httppost.releaseConnection();
    }

}
