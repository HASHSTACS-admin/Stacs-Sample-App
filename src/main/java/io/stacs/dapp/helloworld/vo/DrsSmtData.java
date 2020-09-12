package io.stacs.dapp.helloworld.vo;

import com.alibaba.fastjson.JSONObject;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author HuangShengli
 * @ClassName DrsTxRequest
 * @Description DRS TX request
 * @since 2020/3/12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DrsSmtData implements Serializable {

    @NonNull
    private SmtHeader header;

    private SmtBody body;

    private SmtTrailer trailer;


    @Data
    @Builder
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SmtHeader extends DrsBaseRequest {

        @NonNull
        private String uuid;
        private String messageId;
        @NonNull
        private String messageSenderAddress;
        private String sessionId;
        @NonNull
        private String smtCode;
        private String version;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    @ToString(callSuper = true)
    @NoArgsConstructor
    public static class SmtBody extends JSONObject {
    }

    @Builder
    @Data
    @ToString(callSuper = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SmtTrailer implements Serializable {

        private String authenticationTrailer;
        private List blockchainTransaction;
        private String extraData;
        private String responseCode;
        private String responseMessage;
        private List<Txid> txs;

    }

}
