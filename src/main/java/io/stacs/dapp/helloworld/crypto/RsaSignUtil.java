package io.stacs.dapp.helloworld.crypto;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


public class RsaSignUtil {

    private static final String KEY_ALGORITHM = RsaKeyGeneratorUtil.KEY_ALGORITHM;
    private static final String SIGN_ALGORITHMS = "SHA256withRSA";
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;


    public static String sign(String content, String privateKey) throws Exception {
        return sign(content.getBytes(DEFAULT_CHARSET), privateKey);
    }


    public static String sign(byte[] content, String privateKey) throws Exception {

        PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(base64String2byte(privateKey));
        KeyFactory keyf = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey priKey = keyf.generatePrivate(priPKCS8);

        java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);

        signature.initSign(priKey);
        signature.update(content);

        byte[] signed = signature.sign();

        return base64Byte2string(signed);
    }


    public static boolean check(String content, String sign, String publicKey) throws Exception {
        return check(content.getBytes(DEFAULT_CHARSET), sign, publicKey);
    }

    public static boolean check(byte[] content, String sign, String publicKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        byte[] encodedKey = base64String2byte(publicKey);
        PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

        java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);

        signature.initVerify(pubKey);
        signature.update(content);

        return signature.verify(base64String2byte(sign));
    }

    // utility
    private static byte[] base64String2byte(String str) throws UnsupportedEncodingException {
        return Base64.getDecoder().decode(str.getBytes(DEFAULT_CHARSET));
    }

    private static String base64Byte2string(byte[] bytes) throws UnsupportedEncodingException {
        return new String(Base64.getEncoder().encode(bytes), DEFAULT_CHARSET);
    }
}
