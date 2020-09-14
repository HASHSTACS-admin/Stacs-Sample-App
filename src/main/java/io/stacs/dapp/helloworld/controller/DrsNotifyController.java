package io.stacs.dapp.helloworld.controller;

import com.alibaba.fastjson.JSON;
import io.stacs.dapp.helloworld.config.DrsConfig;
import io.stacs.dapp.helloworld.crypto.AESUtil;
import io.stacs.dapp.helloworld.crypto.RsaEncryptUtil;
import io.stacs.dapp.helloworld.crypto.RsaSignUtil;
import io.stacs.dapp.helloworld.service.SmtNotifyService;
import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.DrsSmtMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author HuangShengli
 * @ClassName DrsNotifyController
 * @Description DRS回调入口
 * 为清晰展示加解密处理过程，本入口处理逻辑较为冗长，正式项目建议使用Web Filter的方式统一处理加解密
 * @since 2020/9/11
 */

@ApiIgnore
@Slf4j
@RestController
@RequestMapping("/api/v1/notify/transaction")
public class DrsNotifyController {

    @Autowired
    private DrsConfig drsConfig;
    @Autowired
    private SmtNotifyService smtNotifyService;

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    @PostMapping
    public void notify(HttpServletRequest request, HttpServletResponse response) {
        //
        /** A.解密阶段: 从request流中解密请求数据*/
        //1.获取请求头
        String identifierId = request.getHeader("identifierId");
        String signature = request.getHeader("signature");
        String aesKey = request.getHeader("aesKey");
        //2.基础校验
        if (!checkRequest(identifierId, signature, aesKey)) {
            return;
        }
        try {
            //3.获取字节流数据
            byte[] requestEncryptBytes = IOUtils.toByteArray(request.getInputStream());
            //4.使用DRS公钥验签
            if (!RsaSignUtil.check(requestEncryptBytes, signature, drsConfig.getPublicKey())) {
                log.error("验签失败");
                errorResponse(response, 403);
                return;
            }
            //5.使用商户自己的私钥解密AESKEY
            String decryptAesKey = RsaEncryptUtil.byte2string(RsaEncryptUtil.decryptByPrivateKeyString(aesKey, drsConfig.getMyPrivateKey()));
            //6.使用解密的AESKEY解密请求数据
            byte[] decryptBytes = AESUtil.decryptBinary(requestEncryptBytes, decryptAesKey);
            //7.json反序列化得到整个报文
            String jsonBody = new String(decryptBytes, DEFAULT_CHARSET);
            DrsSmtMessage smtData = JSON.parseObject(jsonBody, DrsSmtMessage.class);
            log.info("DRS回调请求数据解密完成:{}", jsonBody);

            /** B.业务处理阶段*/
            DrsResponse drsResponse = smtNotifyService.handle(smtData);

            /** C.回执加密阶段：加密回执数据，并返回给DRS*/
            //1.生成AESKEY
            String respAesKey = AESUtil.generateKey256();
            //2.使用AESKEY对回执数据加密
            byte[] respEncryptBytes = AESUtil.encryptBinary(JSON.toJSONString(drsResponse).getBytes(DEFAULT_CHARSET), respAesKey);
            //3.使用商户自己的私钥对加密数据签名
            String respSign = RsaSignUtil.sign(respEncryptBytes, drsConfig.getMyPrivateKey());
            //4.使用DRS公钥对AESKEY加密
            String respEncryptAesKey = RsaEncryptUtil.base64Byte2string(RsaEncryptUtil.encryptByPublicKeyString(respAesKey, drsConfig.getPublicKey()));
            //5.添加到header
            response.addHeader("Content-Type", "application/json;charset=utf-8");
            //商户号
            response.addHeader("identifierId", drsConfig.getMyIdentifierId());
            //AESKEY
            response.addHeader("aesKey", respEncryptAesKey);
            //签名
            response.addHeader("signature", respSign);
            //6.响应数据回写
            okResponse(response, respEncryptBytes);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            errorResponse(response, 500);
        }


    }


    /**
     * DRS请求基础验证
     *
     * @param identifierId
     * @param signature
     * @param aesKey
     * @return
     */
    private boolean checkRequest(String identifierId, String signature, String aesKey) {
        if (StringUtils.isEmpty(identifierId) || StringUtils.isEmpty(signature) || StringUtils.isEmpty(aesKey)) {
            log.warn("DRS回调请求参数不合法");
            return false;
        }
        if (!identifierId.equals(drsConfig.getMyIdentifierId())) {
            log.warn("收到无效请求,identifierId={}", identifierId);
            return false;
        }
        return true;
    }

    /**
     * 成功响应
     *
     * @param response
     * @param body
     */
    private void okResponse(HttpServletResponse response, byte[] body) {
        try {
            response.setContentLength(body.length);
            response.getOutputStream().write(body);
            response.getOutputStream().flush();
        } catch (IOException e) {
            log.error("write response IO_Error", e);
            errorResponse(response, 500);
        }
    }

    /**
     * 失败响应
     *
     * @param response
     * @param httpStatus
     */
    private void errorResponse(HttpServletResponse response, int httpStatus) {
        try {
            response.sendError(httpStatus);
        } catch (IOException e) {
            log.error("write empty error", e);
        }
    }
}
