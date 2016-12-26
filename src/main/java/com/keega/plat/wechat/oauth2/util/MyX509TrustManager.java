package com.keega.plat.wechat.oauth2.util;

import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by zun.wei on 2016/12/14.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public class MyX509TrustManager implements X509TrustManager {

    @Override // 检查客户端证书
    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

    }

    @Override // 检查服务器端证书
    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

    }

    @Override // 返回受信任的X509证书数组
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}
