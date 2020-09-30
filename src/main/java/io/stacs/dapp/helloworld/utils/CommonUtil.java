package io.stacs.dapp.helloworld.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;
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

    /**
     * Current value is suitable for low concurrency scenarios
     *
     * @return the long
     */
    public static long nextTimeId() {
        String now = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").format(LocalDateTime.now());
        int buffer = new Random().nextInt(99);
        return Long.parseLong(now) * 100 + buffer;
    }

}
