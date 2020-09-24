package io.stacs.dapp.helloworld.utils;

import java.util.Date;
import java.util.UUID;

/**
 * @author Huang Shengli
 * @date 2020-09-13
 */
public class CommonUtil {

    /**
     * generate uuid
     *
     * @return uuid
     */
    public static String uuid() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }

    public static long getSmtDateTime(Date datetime) {
        if (datetime == null) {
            return 0;
        }
        return datetime.getTime() / 1000;
    }
}
