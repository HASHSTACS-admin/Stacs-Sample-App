package io.stacs.dapp.helloworld.utils;

import java.util.UUID;

/**
 * @author Huang Shengli
 * @date 2020-09-13
 */
public class UUIDUtil {

    /**
     * generate uuid
     *
     * @return uuid
     */
    public static String uuid() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }
}
