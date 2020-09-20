package io.stacs.dapp.helloworld.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Huang Shengli
 * @date 2020-09-19
 */
public class StatusEnum {

    /**
     * ABS 业务状态
     */
    @Getter
    @AllArgsConstructor
    public enum BizStatus {
        NORMAL((byte) 0, "NORMAL"),
        FROZEN((byte) 1, "FROZEN"),
        REDEEM_FROZEN((byte) 2, "REDEEM_FROZEN"),
        REDEEM((byte) 3, "REDEEM"),;
        private Byte code;
        private String desc;

        public static BizStatus getByCode(byte code) {
            for (BizStatus status : values()) {
                if (code == status.code) {
                    return status;
                }
            }
            return null;
        }
    }

    /**
     * 链状态
     */
    @Getter
    @AllArgsConstructor
    public enum ChainStatus {

        PROCESSING((byte) 0, "PROCESSING"),
        SUCCESS((byte) 1, "SUCCESS"),
        FAIL((byte) 2, "FAIL"),;

        private Byte code;
        private String desc;

        public static ChainStatus getByCode(byte code) {
            for (ChainStatus status : values()) {
                if (code == status.code) {
                    return status;
                }
            }
            return null;
        }
    }

}
