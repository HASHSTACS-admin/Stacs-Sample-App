package io.stacs.dapp.helloworld.httpclient;

import javax.net.ssl.*;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

/**
 * @author HuangShengli
 * @ClassName SslSocketHelper
 * @Description The defaut implementation in this class assumes use of a self-signed X.509 certificate on the DRS for a test environent. Additional implementation for a signed certificate will need to be built.
 * @since 2020/9/11
 */
public class SslSocketHelper {

    /**
     * SSLSocketFactory
     * The default method here assumes use of a self-signed certificate on a test DRS environment
     * This will not be used if self-signed certificates are not used
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
     * Trust Manager
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
     * Hostname Verification
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
