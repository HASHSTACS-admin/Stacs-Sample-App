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
@ApiModel(value = "ABS Freeze for Redemption")
public class AbsBuybackFreezeRequest extends DemoBaseRequest {

    @ApiModelProperty(value = "ABS Freeze for Redemption", required = true)
    @Valid
    private AbsBuybackFreezeSmtBody body;

}
