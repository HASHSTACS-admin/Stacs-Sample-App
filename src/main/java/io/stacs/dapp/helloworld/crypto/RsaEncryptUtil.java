package io.stacs.dapp.helloworld.crypto;


import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


public class RsaEncryptUtil {

    private static final String KEY_ALGORITHM = RsaKeyGeneratorUtil.KEY_ALGORITHM;
    private static final int KEY_SIZE = RsaKeyGeneratorUtil.KEY_SIZE;
    private static final int MAX_ENCODE_BLOCK = (KEY_SIZE / 8) - 11;
    private static final int MAX_DECODE_BLOCK = KEY_SIZE / 8;
    private static final String CHARSET = "utf-8";


    // # -------------------------- encrypt by private key -------------------------- #
    public static byte[] encryptByPrivateKeyString(String data, String privateKeyString) throws Exception {
        return encryptByPrivateKeyString(string2byte(data), privateKeyString);
    }

    public static byte[] encryptByPrivateKeyString(byte[] data, String privateKeyString) throws Exception {
        return encryptByPrivateKey(data, base64String2byte(privateKeyString));
    }

    public static byte[] encryptByPrivateKey(byte[] data, byte[] privateKeyByte) throws Exception {
        byte[] encryptedData = new byte[0];
        if (data.length == 0) {
            return encryptedData;
        }
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKeyByte);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);

            encryptedData = doFinal(data, cipher, out, MAX_ENCODE_BLOCK);
        }
        return encryptedData;
    }

    // # -------------------------- decrypt by private key -------------------------- #
    public static byte[] decryptByPrivateKeyString(String data, String privateKeyString) throws Exception {
        return decryptByPrivateKeyString(base64String2byte(data), privateKeyString);
    }

    public static byte[] decryptByPrivateKeyString(byte[] data, String privateKeyString) throws Exception {
        return decryptByPrivateKey(data, base64String2byte(privateKeyString));
    }

    public static byte[] decryptByPrivateKey(byte[] data, byte[] key) throws Exception {
        byte[] encryptedData = new byte[0];
        if (data.length == 0) {
            return encryptedData;
        }
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            encryptedData = doFinal(data, cipher, out, MAX_DECODE_BLOCK);
        }
        return encryptedData;
    }


    // # -------------------------- encrypt by public key -------------------------- #
    public static byte[] encryptByPublicKeyString(String data, String publicKeyString) throws Exception {
        return encryptByPublicKeyString(string2byte(data), publicKeyString);
    }

    public static byte[] encryptByPublicKeyString(byte[] data, String publicKeyString) throws Exception {
        return encryptByPublicKey(data, base64String2byte(publicKeyString));
    }

    public static byte[] encryptByPublicKey(byte[] data, byte[] key) throws Exception {
        byte[] encryptedData = new byte[0];
        if (data.length == 0) {
            return encryptedData;
        }
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
            PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);

            encryptedData = doFinal(data, cipher, out, MAX_ENCODE_BLOCK);
        }
        return encryptedData;
    }

    // # -------------------------- decrypt by public key -------------------------- #

    public static byte[] decryptByPublicKeyString(String data, String publicKeyString) throws Exception {
        return decryptByPublicKeyString(base64String2byte(data), publicKeyString);
    }

    public static byte[] decryptByPublicKeyString(byte[] data, String publicKeyString) throws Exception {
        return decryptByPublicKey(data, base64String2byte(publicKeyString));
    }

    public static byte[] decryptByPublicKey(byte[] data, byte[] key) throws Exception {
        byte[] encryptedData = new byte[0];
        if (data.length == 0) {
            return encryptedData;
        }
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
            PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, pubKey);

            encryptedData = doFinal(data, cipher, out, MAX_DECODE_BLOCK);
        }
        return encryptedData;
    }


    private static byte[] doFinal(byte[] data, Cipher cipher, ByteArrayOutputStream out, int MAX_BLOCK)
            throws BadPaddingException, IllegalBlockSizeException {
        int inputLen = data.length;
        int offSet = 0;
        byte[] cache;
        int i = 0;
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_BLOCK;
        }
        return out.toByteArray();
    }

    // utility
    private static byte[] base64String2byte(String str) throws UnsupportedEncodingException {
        return Base64.getDecoder().decode(str.getBytes(CHARSET));
    }

    public static String base64Byte2string(byte[] bytes) throws UnsupportedEncodingException {
        return new String(Base64.getEncoder().encode(bytes), CHARSET);
    }

    private static byte[] string2byte(String str) throws UnsupportedEncodingException {
        return str.getBytes(CHARSET);
    }

    public static String byte2string(byte[] bytes) throws UnsupportedEncodingException {
        return new String(bytes, CHARSET);
    }

}
