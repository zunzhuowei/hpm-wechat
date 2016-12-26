package com.keega.plat.wechat.util.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by zun.wei on 2016/12/9.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Component
public class HttpsPostClient extends MyHttpsClient {

    /**
     * @param url     url地址
     * @param map     请求的参数和值key是参数名，value是请求的值
     * @param charset 编码格式
     * @return
     */
    public String doPostByMap(String url, Map<String, String> map, String charset) throws IOException {
        HttpPost httpPost = null;
        String result = null;
        httpPost = new HttpPost(url);
        //设置参数
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> elem = (Map.Entry<String, String>) iterator.next();
            list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
        }
        if (list.size() > 0) {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
            httpPost.setEntity(entity);
        }
        HttpResponse response = this.getHttpsClient().execute(httpPost);
        if (response != null) {
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                result = EntityUtils.toString(resEntity, charset);
            }
        }
        return result;
    }

    /**
     * 发送文本消息
     * @param url     url地址
     * @param json     请求json
     * @param charset 编码格式
     * @return
     */
    public String doPostByJson(String url,String json, String charset) throws IOException {
        HttpPost httpPost = null;
        String result = null;
        httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type","application/json");
        StringEntity stringEntity = new StringEntity(json,
                ContentType.create("application/json", "utf-8"));
        httpPost.setEntity(stringEntity);
        try {
            HttpResponse response = this.getHttpsClient().execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    if (charset == null) charset = "utf-8";
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
        }finally {
            httpPost.releaseConnection();
        }
        return result;
    }

    /**
     *发送媒体文件
     * @param file 文件
     * @param url uri
     * @return 请求返回值
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public String doPostMedia(File file, String url) throws NoSuchAlgorithmException, IOException {
        String result = null;
        HttpPost httpPost = new HttpPost(url);
        ApacheHttpClientBuilder apacheHttpClientBuilder = DefaultApacheHttpClientBuilder.get().
                sslConnectionSocketFactory(new SSLConnectionSocketFactory(SSLContext.getDefault()));
        CloseableHttpClient httpclient = apacheHttpClientBuilder.build();//方法一
        //CloseableHttpClient httpclient = HttpClientBuilder.create().build(); //方法二
        if (file != null) {
            HttpEntity entity = MultipartEntityBuilder
                    .create()
                    .addBinaryBody("media", file)
                    .setMode(HttpMultipartMode.RFC6532)
                    .build();
            httpPost.setEntity(entity);
            httpPost.setHeader("Content-Type", ContentType.MULTIPART_FORM_DATA.toString());
        }
        try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, "utf-8");
                }
            }
            return result;
        } finally {
            httpPost.releaseConnection();
        }
    }


}
