package io.stacs.dapp.helloworld.vo.demo;

import io.stacs.dapp.helloworld.vo.drs.AbsBuybackSmtBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;

/**
 * @author Su Wenbo
 * @since 2020/9/24
 */
@Data
@ApiModel(value = "ABS Redemption")
public class AbsBuybackRequest extends DemoBaseRequest {

    @ApiModelProperty(value = "ABS Redemption", required = true)
    @Valid
    private AbsBuybackSmtBody body;

}
