package io.stacs.dapp.helloworld.crypto;


import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class RsaKeyGeneratorUtil {

    public static final String KEY_ALGORITHM = "RSA";
    public static final int KEY_SIZE = 2048;
    public static final String PUBLIC_KEY = "RSAPublicKey";
    public static final String PRIVATE_KEY = "RSAPrivateKey";
    private static final String CHARSET = "utf-8";

    public static void main(String[] args) throws Exception {
        Map<String, String> stringStringMap = generateKeyPairString();
        System.out.println(stringStringMap.get(PUBLIC_KEY));
        System.out.println(">>>>>>>>>>>>>");
        System.out.println(stringStringMap.get(PRIVATE_KEY));
    }

    public static Map<String, String> generateKeyPairString() throws Exception {
        Map<String, Object> keyMap = generateKeyPair();
        byte[] publicKey = getPublicKey(keyMap);
        byte[] privateKey = getPrivateKey(keyMap);

        Map<String, String> retKeyMap = new HashMap<String, String>();
        retKeyMap.put(PUBLIC_KEY, new String(Base64.getEncoder().encode(publicKey), CHARSET));
        retKeyMap.put(PRIVATE_KEY, new String(Base64.getEncoder().encode(privateKey), CHARSET));
        return retKeyMap;
    }

    private static Map<String, Object> generateKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGenerator.initialize(KEY_SIZE);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>();
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }


    // ----------------------------------------------------------------------------
    private static byte[] getPrivateKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return key.getEncoded();
    }

    private static byte[] getPublicKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return key.getEncoded();
    }
}
