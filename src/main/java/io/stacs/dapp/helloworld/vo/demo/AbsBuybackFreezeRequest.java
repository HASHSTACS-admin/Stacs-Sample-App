package io.stacs.dapp.helloworld.vo.demo;

import io.stacs.dapp.helloworld.vo.drs.AbsBuybackFreezeSmtBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;

/**
 * @author Su Wenbo
 * @since 2020/9/24
 */
@Data
@ApiModel(value = "ABS回购冻结参数")
public class AbsBuybackFreezeRequest extends DemoBaseRequest {

    @ApiModelProperty(value = "ABS回购冻结报文体", required = true)
    @Valid
    private AbsBuybackFreezeSmtBody body;

}
