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
 * @Description drs通讯client, 实现drs交互过程中的加解密及验签处理
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
     * 默认字符集
     */
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    /**
     * 公私钥及商户号配置
     */
    private static DrsConfig CONFIG;

    /**
     * 创建httpclient实例,采用okHttpClient
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
     * post 请求
     *
     * @param url
     * @param request
     */
    public static JSONObject post(String url, Object request) {

        try {
            /** A.准备阶段：对request body做加密处理,并生成签名和aseKey */
            //1.请求数据转化为字节数组
            byte[] requestBytes = JSON.toJSONString(request).getBytes(DEFAULT_CHARSET);
            //2.生成AESKEY
            String aesKey = AESUtil.generateKey256();
            //3.使用AESKEY对数据加密
            byte[] encryptedData = AESUtil.encryptBinary(requestBytes, aesKey);
            //4.对AESKEY加密,使用DRS的公钥加密，DRS通过私钥解密
            String encryptAesKey = RsaEncryptUtil.base64Byte2string(RsaEncryptUtil.encryptByPublicKeyString(aesKey, CONFIG.getPublicKey()));
            //5.对数据签名，使用商户自己的私钥签名，DRS通过商户的公钥验签
            String signature = RsaSignUtil.sign(encryptedData, CONFIG.getMyPrivateKey());
            log.info("A.请求DRS:{}，请求数据加密完成", url);

            /** B.执行RPC阶段：设置请求头，执行HTTP请求 */
            Request.Builder requestBuilder = new Request.Builder();
            //1.设置请求头
            requestBuilder
                    //商户号
                    .addHeader("identifierId", CONFIG.getMyIdentifierId())
                    //加密后的AESKEY
                    .addHeader("aesKey", encryptAesKey)
                    //签名
                    .addHeader("signature", signature);
            //2.加密数据生成request body
            RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, encryptedData);
            //3.生成request请求
            Request req = requestBuilder
                    .url(url)
                    .post(body)
                    .build();
            //4.执行远程调用

            Response response = CLIENT.newCall(req).execute();
            log.info("B.请求DRS，HTTP调用成功");

            /** C.响应数据处理阶段：检查http状态码，解密响应数据 */
            //1.校验是否通讯成功
            checkResponse(response);
            //2.获取响应头
            String respIdentifierId = response.header("identifierId");
            String respSignatue = response.header("signature");
            String respEncryptAesKey = response.header("aesKey");
            ResponseBody responseBody = response.body();
            byte[] respEncryptedBody = responseBody.bytes();
            //3.对响应数据验签，使用DRS公钥验签

            if (!RsaSignUtil.check(respEncryptedBody, respSignatue, CONFIG.getPublicKey())) {
                log.error("DRS响应数据验签失败");
                throw new RuntimeException("sign verify error");
            }
            //4.解密AESKEY，使用商户自己的私钥
            String decryptAesKey = RsaEncryptUtil.byte2string(RsaEncryptUtil.decryptByPrivateKeyString(respEncryptAesKey, CONFIG.getMyPrivateKey()));
            //5.使用AESKEY解密返回值
            byte[] decryptBytes = AESUtil.decryptBinary(respEncryptedBody, decryptAesKey);
            String jsonStringResp = new String(decryptBytes, DEFAULT_CHARSET);
            log.info("C.解密HTTP响应数据完成:{}", jsonStringResp);
            return JSONObject.parseObject(jsonStringResp);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * parse http code to biz exception
     *
     * @param response
     */
    private static void checkResponse(Response response) {
        if (response == null) {
            log.error("响应空数据");
            throw new RuntimeException("drs network error");
        }
        if (response.isSuccessful()) {
            return;
        }
        log.error("DRS响应状态非200:{}", response.toString());
        if (response.code() == 403) {
            throw new RuntimeException("access drs unauthorized");
        }
        if (400 <= response.code() && response.code() < 500) {
            throw new RuntimeException("drs bad request");
        }
        if (500 <= response.code()) {
            throw new RuntimeException("drs internal  error");
        }
        throw new RuntimeException("unknown error");
    }

}
