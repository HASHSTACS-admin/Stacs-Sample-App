package io.stacs.dapp.helloworld.httpclient;

import javax.net.ssl.*;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

/**
 * @author HuangShengli
 * @ClassName SslSocketHelper
 * @Description 由于测试网络中drs服务可能使用的自签证书，需要本地client配置相关ssl协议，正式网络中可以去掉
 * @since 2020/9/11
 */
public class SslSocketHelper {

    /**
     * 获取SSLSocketFactory
     * 测试网络drs可能使用自签证书
     * 正式网络里可以忽略该配置
     *
     * @return
     */
    public static SSLSocketFactory getSSLSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, getTrustManager(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 信任所有
     *
     * @return
     */
    private static TrustManager[] getTrustManager() {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[]{};
                    }
                }
        };
        return trustAllCerts;
    }


    /**
     * 域名验证
     *
     * @return
     */
    public static HostnameVerifier getHostnameVerifier() {
        HostnameVerifier hostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        };
        return hostnameVerifier;
    }
}
