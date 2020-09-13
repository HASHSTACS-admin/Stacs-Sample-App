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
@ApiModel(value = "数字货币发行参数")
public class DigitalCurrencySmtRequest extends DemoBaseRequest {

    @ApiModelProperty(value = "数字货币报文体数据", required = true)
    @Valid
    private DigitalCurrencySmtBody body;
}
