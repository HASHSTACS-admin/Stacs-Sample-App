package io.stacs.dapp.helloworld.vo.demo;

import io.stacs.dapp.helloworld.vo.drs.TransferSmtBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;

/**
 * @author Huang Shengli
 * @Description Transfer of digital currencies, ABS, debt, certificates
 * @date 2020-09-13
 */
@Data
@ApiModel(value = "Digital Currency, ABS, bonds, certificates")
public class TransferSmtRequest extends DemoBaseRequest {

    @ApiModelProperty(value = "message body", required = true)
    @Valid
    private TransferSmtBody body;
}
