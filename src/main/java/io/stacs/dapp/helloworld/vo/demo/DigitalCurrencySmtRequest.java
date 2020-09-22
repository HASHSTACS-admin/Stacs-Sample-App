package io.stacs.dapp.helloworld.vo.demo;

import io.stacs.dapp.helloworld.vo.drs.DigitalCurrencySmtBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;

/**
 * @author Huang Shengli
 * @date 2020-09-13
 */
@Data
@ApiModel(value = "Digital Currency Parameters")
public class DigitalCurrencySmtRequest extends DemoBaseRequest {

    @ApiModelProperty(value = "Digital Currency Message body", required = true)
    @Valid
    private DigitalCurrencySmtBody body;
}
