package io.stacs.dapp.helloworld.vo.demo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author HuangShengli
 * @ClassName DemoSmtRequest
 * @Description 为了方便用户体验，所有报文只需用户输入发起人地址和报文code，报文相关的参数由系统自动填充
 * @since 2020/9/12
 */
@Data
@ApiModel(value = "发送报文体验参数")
public class DemoSmtRequest implements Serializable {


    @ApiModelProperty(value = "提交地址(用户先使用获取地址API获取一个属于自己的地址)", required = true)
    @NotBlank(message = "提交地址不能为空")
    private String messageSenderAddress;

    @ApiModelProperty(value = "报文Code(用户需要体验的报文code)", required = true)
    @NotBlank(message = "smt code 不能为空")
    private String smtCode;
}
