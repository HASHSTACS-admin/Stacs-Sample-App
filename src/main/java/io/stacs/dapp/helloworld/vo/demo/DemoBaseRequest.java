package io.stacs.dapp.helloworld.vo.demo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author HuangShengli
 * @ClassName DemoBaseRequest
 * @Description Demo SMT Format with APIs, base requests
 * @since 2020/9/12
 */
@Data
public class DemoBaseRequest implements Serializable {

    @ApiModelProperty(value = "Message request header parameters", required = true)
    @Valid
    private SimpleHeader header;
    @ApiModelProperty(value = "message request trailer")
    @Valid
    private SimpleTrailer trailer;

    @Data
    public static class SimpleHeader implements Serializable {

        @ApiModelProperty(value = "Merchant Id, is retrieved from config and not set here by default")
        private String identifierId;
        @ApiModelProperty(value = "uuid, is created automatically and does not need to be set by default")
        private String uuid;
        @ApiModelProperty(value = "message ID, is sent by the DRS callback API, not shown in the demo UI", hidden = true)
        private String messageId;
        @ApiModelProperty(value = "Sender Address (sender has to pre-generate an address before setting this value)", required = true)
        @NotBlank(message = "Sender address cannot be blank.")
        private String messageSenderAddress;
        @ApiModelProperty(value = "session id, is sent by the DRS callback API, not shown in the demo UI")
        private String sessionId;
        @NotBlank(message = "SMT Format Code cannot be blank")
        @ApiModelProperty(value = "SMT Format Code", required = true)
        private String smtCode;
        @ApiModelProperty(value = "version, is sent by the DRS callback API, not shown in the demo UI", hidden = true)
        private String version;
    }

    @Data
    public static class SimpleTrailer implements Serializable {

        @ApiModelProperty(value = "authentication trailer payload, limited to 128 char")
        private String authenticationTrailer;
        @ApiModelProperty(value = "additional payload, not shown in the demo UI", hidden = true)
        private String extraData;
    }
}
