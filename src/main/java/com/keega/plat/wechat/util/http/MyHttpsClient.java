package com.keega.plat.wechat.util.http;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by zun.wei on 2016/12/9.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public abstract class MyHttpsClient extends HttpUtil {

    private static CloseableHttpClient closeableHttpClient = null;
    private static CloseableHttpClient myCloseableHttpClient = null;

    //此构造方法用来发送https请求
    public MyHttpsClient(){
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }
                @Override
                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            ClientConnectionManager ccm = new DefaultHttpClient().getConnectionManager();
            SchemeRegistry sr = ccm.getSchemeRegistry();
            sr.register(new Scheme("https", 443, ssf));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取HttpClientBuilder.create().build()制作出来的CloseableHttpClient
     * @return CloseableHttpClient
     */
    public synchronized CloseableHttpClient getHttpsClient() {
        if (null == closeableHttpClient) {
            synchronized (MyHttpsClient.class) {
                if (null == closeableHttpClient) {
                    return HttpClientBuilder.create().build();
                }
            }
        }
        return closeableHttpClient;
    }

    /**
     * 获取ApacheHttpClientBuilder制作出来的CloseableHttpClient
     * @return CloseableHttpClient
     */
    public synchronized CloseableHttpClient getMyHttpsClient() throws NoSuchAlgorithmException {
        if (null == myCloseableHttpClient) {
            synchronized (MyHttpsClient.class) {
                if (null == myCloseableHttpClient) {
                    ApacheHttpClientBuilder apacheHttpClientBuilder = DefaultApacheHttpClientBuilder.get().
                            sslConnectionSocketFactory(new SSLConnectionSocketFactory(SSLContext.getDefault()));
                    return apacheHttpClientBuilder.build();//方法一
                }
            }
        }
        return myCloseableHttpClient;
    }
}
