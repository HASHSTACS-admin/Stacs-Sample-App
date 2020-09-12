package io.stacs.dapp.helloworld.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author HuangShengli
 * @ClassName DrsResponse
 * @Description drs response
 * @since 2020/3/12
 */
@Data
public class DrsResponse<T> implements Serializable {
    private String code;

    private String message;

    private T data;

    public DrsResponse() {
        super();
    }

    public DrsResponse(String code, String message, T t) {
        this.code = code;
        this.message = message;
        this.data = t;
    }

    public static <T> DrsResponse<T> success(T t) {
        return new DrsResponse(DrsRespCode.SUCCESS.getCode(), null, t);
    }

    public static <T> DrsResponse<T> fail(String errorCode, String message) {
        return new DrsResponse(errorCode, message, null);
    }

    @Data
    public static class SmtResult implements Serializable {
        private String messageId;
        private String sessionId;
    }
}
