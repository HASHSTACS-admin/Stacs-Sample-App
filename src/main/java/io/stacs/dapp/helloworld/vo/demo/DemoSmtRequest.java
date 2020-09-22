package io.stacs.dapp.helloworld.vo.demo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author HuangShengli
 * @ClassName DemoSmtRequest
 * @Description To simplify, all messages only require issuer address and SMT code, other required parameters by the SMT format are prepopulated
 * @since 2020/9/12
 */
@Data
@ApiModel(value = "parameters for sending API request")
public class DemoSmtRequest implements Serializable {


    @ApiModelProperty(value = "Sender Address, (sender has to pre-generate an address before setting this value)", required = true)
    @NotBlank(message = "Sender Address cannot be blank.")
    private String messageSenderAddress;

    @ApiModelProperty(value = "SMT format Code", required = true)
    @NotBlank(message = "SMT Code cannot be blank.")
    private String smtCode;
}
