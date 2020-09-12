package io.stacs.dapp.helloworld.crypto;

import org.jasypt.util.binary.AES256BinaryEncryptor;
import org.jasypt.util.text.AES256TextEncryptor;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;


public class AESUtil {
    public static String generateKey(int len) {
        try {
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(len);
            //要生成多少位，只需要修改这里即可128, 192或256
            SecretKey sk = kg.generateKey();
            byte[] b = sk.getEncoded();
            String s = byteToHexString(b);
            return s;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("AES_generate_key_error", e);
        }
    }

    public static String generateKey256() {
        return generateKey(256);
    }

    public static String encryptString(String content, String key) {
        AES256TextEncryptor textEncryptor = new AES256TextEncryptor();
        textEncryptor.setPassword(key);
        return textEncryptor.encrypt(content);
    }


    public static String decryptString(String content, String key) {
        AES256TextEncryptor textEncryptor = new AES256TextEncryptor();
        textEncryptor.setPassword(key);
        return textEncryptor.decrypt(content);
    }


    public static byte[] encryptBinary(byte[] content, String key) {
        AES256BinaryEncryptor aes256BinaryEncryptor = new AES256BinaryEncryptor();
        aes256BinaryEncryptor.setPassword(key);
        return aes256BinaryEncryptor.encrypt(content);
    }


    public static byte[] decryptBinary(byte[] content, String key) {
        AES256BinaryEncryptor aes256BinaryEncryptor = new AES256BinaryEncryptor();
        aes256BinaryEncryptor.setPassword(key);
        return aes256BinaryEncryptor.decrypt(content);
    }

    private static String byteToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String strHex = Integer.toHexString(bytes[i]);
            if (strHex.length() > 3) {
                sb.append(strHex.substring(6));
            } else {
                if (strHex.length() < 2) {
                    sb.append("0" + strHex);
                } else {
                    sb.append(strHex);
                }
            }
        }
        return sb.toString();
    }
}
