package io.stacs.dapp.helloworld.vo.demo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author HuangShengli
 * @ClassName DemoBaseRequest
 * @Description 示例报文 请求基类
 * @since 2020/9/12
 */
@Data
public class DemoBaseRequest implements Serializable {

    @ApiModelProperty(value = "报文请求头数据", required = true)
    @Valid
    private SimpleHeader header;
    @ApiModelProperty(value = "报文请求尾数据")
    @Valid
    private SimpleTrailer trailer;

    @Data
    public static class SimpleHeader implements Serializable {

        @ApiModelProperty(value = "商户号(示例中取配置的商户号，用户输入无效)")
        private String identifierId;
        @ApiModelProperty(value = "uuid(示例中系统自动生成，用户输入无效)")
        private String uuid;
        @ApiModelProperty(value = "messageId(DRS返回，示例中隐藏)", hidden = true)
        private String messageId;
        @ApiModelProperty(value = "提交地址(用户先使用获取地址API获取一个属于自己的地址)", required = true)
        @NotBlank(message = "提交地址不能为空")
        private String messageSenderAddress;
        @ApiModelProperty(value = "sessionId(DRS返回，示例中隐藏)", hidden = true)
        private String sessionId;
        @NotBlank(message = "报文code不能为空")
        @ApiModelProperty(value = "报文Code(用户需要体验的报文code)", required = true)
        private String smtCode;
        @ApiModelProperty(value = "报文版本号(DRS返回，示例中隐藏)", hidden = true)
        private String version;
    }

    @Data
    public static class SimpleTrailer implements Serializable {

        @ApiModelProperty(value = "附件信息(128英文字符以内)")
        private String authenticationTrailer;
        @ApiModelProperty(value = "其他备注信息(暂时无用，示例中隐藏)", hidden = true)
        private String extraData;
    }
}
