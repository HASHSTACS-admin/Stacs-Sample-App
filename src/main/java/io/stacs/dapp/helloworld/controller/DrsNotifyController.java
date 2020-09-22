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
 * @Description DRS Callback API Response handler
 * Encryption and Decryption processes of the API response is provided below. We recommend the use of Web Filter for encrypting and decrypting API requests and responses respectively.
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
        /** A.Decryption Phase: decrypting the request payload from the incoming API request via the DRS callback API*/
        //1.Get the Header from the incoming HTTP Request
        String identifierId = request.getHeader("identifierId");
        String signature = request.getHeader("signature");
        String aesKey = request.getHeader("aesKey");
        //2.Validate presence of required parameters
        if (!checkRequest(identifierId, signature, aesKey)) {
            return;
        }
        try {
            //3.Convert the request to a byte stream 
            byte[] requestEncryptBytes = IOUtils.toByteArray(request.getInputStream());
            //4.Verify the signature using the DRS public key
            if (!RsaSignUtil.check(requestEncryptBytes, signature, drsConfig.getPublicKey())) {
                log.error("Signature Validation Failed");
                errorResponse(response, 403);
                return;
            }
            //5.Decrypt and retrieve the AES key using the merchant's private key
            String decryptAesKey = RsaEncryptUtil.byte2string(RsaEncryptUtil.decryptByPrivateKeyString(aesKey, drsConfig.getMyPrivateKey()));
            //6.Use the decrypted AES key to decrypt the request byte stream
            byte[] decryptBytes = AESUtil.decryptBinary(requestEncryptBytes, decryptAesKey);
            //7.Deserialize the JSON to retrieve the Request Message
            String jsonBody = new String(decryptBytes, DEFAULT_CHARSET);
            DrsSmtMessage smtData = JSON.parseObject(jsonBody, DrsSmtMessage.class);
            log.info("DRS Callback Response decrypted is completed:{}", jsonBody);

            /** B.Processing Phase*/
            DrsResponse drsResponse = smtNotifyService.handle(smtData);

            /** C.Encryption of Receipt Confirmation Phase: encrypt a message to send back to the DRS to confirm receipt of the HTTP Request*/
            //1.Generate new AES Key
            String respAesKey = AESUtil.generateKey256();
            //2.Encrypt receipt confirmation request with AES Key
            byte[] respEncryptBytes = AESUtil.encryptBinary(JSON.toJSONString(drsResponse).getBytes(DEFAULT_CHARSET), respAesKey);
            //3.Sign the encrypted request with the merchant private key
            String respSign = RsaSignUtil.sign(respEncryptBytes, drsConfig.getMyPrivateKey());
            //4.Encrypt the generated AES Key in Step 1 with the DRS public key
            String respEncryptAesKey = RsaEncryptUtil.base64Byte2string(RsaEncryptUtil.encryptByPublicKeyString(respAesKey, drsConfig.getPublicKey()));
            //5.Add the header to the HTTP Request 
            response.addHeader("Content-Type", "application/json;charset=utf-8");
            //Merchant Id
            response.addHeader("identifierId", drsConfig.getMyIdentifierId());
            //Encrypted AES Key (for DRS to decrypt)
            response.addHeader("aesKey", respEncryptAesKey);
            //Signature (using the merchant private key)
            response.addHeader("signature", respSign);
            //6.Create the REsponse
            okResponse(response, respEncryptBytes);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            errorResponse(response, 500);
        }


    }


    /**
     * DRS Required Parameters Validation
     *
     * @param identifierId
     * @param signature
     * @param aesKey
     * @return
     */
    private boolean checkRequest(String identifierId, String signature, String aesKey) {
        if (StringUtils.isEmpty(identifierId) || StringUtils.isEmpty(signature) || StringUtils.isEmpty(aesKey)) {
            log.warn("DRS API Callback Missing Parameters");
            return false;
        }
        if (!identifierId.equals(drsConfig.getMyIdentifierId())) {
            log.warn("Invalid request received,identifierId={}", identifierId);
            return false;
        }
        return true;
    }

    /**
     * Successful Response
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
     * Failed Response
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
