package com.keega.plat.wechat.util.http;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * httpclient build interface
 * Created by zun.wei on 2016/12/8.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public interface ApacheHttpClientBuilder {

    /**
     * 构建httpclient实例
     *
     * @return new instance of CloseableHttpClient
     */
    CloseableHttpClient build();

    /**
     * 代理服务器地址
     *
     * @param httpProxyHost
     */
    ApacheHttpClientBuilder httpProxyHost(String httpProxyHost);

    /**
     * 代理服务器端口
     *
     * @param httpProxyPort
     */
    ApacheHttpClientBuilder httpProxyPort(int httpProxyPort);

    /**
     * 代理服务器用户名
     *
     * @param httpProxyUsername
     */
    ApacheHttpClientBuilder httpProxyUsername(String httpProxyUsername);

    /**
     * 代理服务器密码
     *
     * @param httpProxyPassword
     */
    ApacheHttpClientBuilder httpProxyPassword(String httpProxyPassword);

    /**
     * ssl连接socket工厂
     *
     * @param sslConnectionSocketFactory
     */
    ApacheHttpClientBuilder sslConnectionSocketFactory(SSLConnectionSocketFactory sslConnectionSocketFactory);

}
