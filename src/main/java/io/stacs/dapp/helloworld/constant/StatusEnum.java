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
     * offer订单业务状态
     * 合约对应状态:1-init, 2-subscribing,3-refund, 4-settled
     */
    @Getter
    @AllArgsConstructor
    public enum OfferBizStatus {
        OFFER((byte) 0, "OFFER"),
        REFUND((byte) 1, "REFUND"),
        SETTLEMENT((byte) 2, "SETTLEMENT"),
        ;
        private Byte code;
        private String desc;

        public static OfferBizStatus getByCode(byte code) {
            for (OfferBizStatus status : values()) {
                if (code == status.code) {
                    return status;
                }
            }
            return null;
        }
    }

    /**
     * bid 订单业务状态
     * 合约对应状态:1-subscribed, 2-payment, 3-confirmed，4-refund, 5-disputing, 6-canceled,7-settled
     */
    @Getter
    @AllArgsConstructor
    public enum BidBizStatus {
        SUBSCRIBED((byte) 0, "SUBSCRIBED"),
        PAYMENT((byte) 1, "PAYMENT"),
        CONFIRMED((byte) 2, "CONFIRMED"),
        REFUND((byte) 3, "REFUND"),
        DISPUTING((byte) 4, "DISPUTING"),
        CANCELED((byte) 5, "CANCELED"),
        SETTLED((byte) 6, "SETTLED"),
        ;
        private Byte code;
        private String desc;

        public static BidBizStatus getByCode(byte code) {
            for (BidBizStatus status : values()) {
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
        FAIL((byte) 2, "FAIL"),
        ;

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
