package io.stacs.dapp.helloworld.httpclient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.stacs.dapp.helloworld.config.DrsConfig;
import io.stacs.dapp.helloworld.crypto.AESUtil;
import io.stacs.dapp.helloworld.crypto.RsaEncryptUtil;
import io.stacs.dapp.helloworld.crypto.RsaSignUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @author HuangShengli
 * @ClassName DrsClient
 * @Description DRS Client class for Encryption and Signature Verification
 * @since 2020/9/11
 */
@Slf4j
@Component
public class DrsClient {

    /**
     * content-type for request
     */
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * Default Charset as UTF-8
     */
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    /**
     * Configuration for connecting to DRS
     */
    private static DrsConfig CONFIG;

    /**
     * HTTPClient creation using OkHttpClient
     */
    private static final OkHttpClient CLIENT = new OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectionPool(new ConnectionPool())
            .sslSocketFactory(SslSocketHelper.getSSLSocketFactory())
            .hostnameVerifier(SslSocketHelper.getHostnameVerifier())
            .build();

    @Autowired
    private DrsConfig drsConfig;

    @PostConstruct
    public void init() {
        CONFIG = drsConfig;
    }

    /**
     * HTTP POST Request
     *
     * @param url
     * @param request
     */
    public static JSONObject post(String url, Object request) {

        try {
            /** A.Preparation Phase:encrypt request body, generate AES key and signature */
            //1. Create byte stream from request
            byte[] requestBytes = JSON.toJSONString(request).getBytes(DEFAULT_CHARSET);
            //2. Generate AES Key 
            String aesKey = AESUtil.generateKey256();
            //3.Encrypt Request body with AES key
            byte[] encryptedData = AESUtil.encryptBinary(requestBytes, aesKey);
            //4. Encrypt AES key with DRS public key
            String encryptAesKey = RsaEncryptUtil.base64Byte2string(RsaEncryptUtil.encryptByPublicKeyString(aesKey, CONFIG.getPublicKey()));
            //5.Create request signature using Merchant private key
            String signature = RsaSignUtil.sign(encryptedData, CONFIG.getMyPrivateKey());
            log.info("A. DRS Request:{}，Request Encryption completed.", url);

            /** B.RPC Phase:setup Request Header and send over HTTP Request*/
            Request.Builder requestBuilder = new Request.Builder();
            //1.Request Header Setup
            requestBuilder
                    //Merchant Id
                    .addHeader("identifierId", CONFIG.getMyIdentifierId())
                    //Encrypted AES key
                    .addHeader("aesKey", encryptAesKey)
                    //Signature
                    .addHeader("signature", signature);
            //2. Add encrypted Request body
            RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, encryptedData);
            //3. Create HTTP Request
            Request req = requestBuilder
                    .url(url)
                    .post(body)
                    .build();
            //4. Send Remote Call

            Response response = CLIENT.newCall(req).execute();
            log.info("B.DRS HTTP Request sent successfully.");

            /** C.Response Processing Phase */
            //1.Check for successful response
            checkResponse(response);
            //2. Extract header from Response
            String respIdentifierId = response.header("identifierId");
            String respSignatue = response.header("signature");
            String respEncryptAesKey = response.header("aesKey");
            ResponseBody responseBody = response.body();
            byte[] respEncryptedBody = responseBody.bytes();
            //3. Verify Response Signature with DRS public key

            if (!RsaSignUtil.check(respEncryptedBody, respSignatue, CONFIG.getPublicKey())) {
                log.error("DRS Signature verification failed.");
                throw new RuntimeException("signature verification error");
            }
            //4. Decrypt AES key using Merchant private key
            String decryptAesKey = RsaEncryptUtil.byte2string(RsaEncryptUtil.decryptByPrivateKeyString(respEncryptAesKey, CONFIG.getMyPrivateKey()));
            //5. Decrypt Response Body with decrypted AES key
            byte[] decryptBytes = AESUtil.decryptBinary(respEncryptedBody, decryptAesKey);
            String jsonStringResp = new String(decryptBytes, DEFAULT_CHARSET);
            log.info("C. Decrypt HTTP response payload successful:{}", jsonStringResp);
            return JSONObject.parseObject(jsonStringResp);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * check Response for successful status
     *
     * @param response
     */
    private static void checkResponse(Response response) {
        if (response == null) {
            log.error("Null Response found.");
            throw new RuntimeException("drs network error");
        }
        if (response.isSuccessful()) {
            return;
        }
        log.error("DRS Response is not 200:{}", response.toString());
        if (response.code() == 403) {
            throw new RuntimeException("DRS access is unauthorized");
        }
        if (400 <= response.code() && response.code() < 500) {
            throw new RuntimeException("DRS bad request");
        }
        if (500 <= response.code()) {
            throw new RuntimeException("DRS internal  error");
        }
        throw new RuntimeException("unknown error");
    }

}
