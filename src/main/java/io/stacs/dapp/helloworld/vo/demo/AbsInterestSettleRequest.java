package io.stacs.dapp.helloworld.vo.demo;

import io.stacs.dapp.helloworld.vo.drs.AbsInterestSettleSmtBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;

/**
 * @author Su Wenbo
 * @since 2020/9/24
 */
@Data
@ApiModel(value = "ABS结息参数")
public class AbsInterestSettleRequest extends DemoBaseRequest {

    @ApiModelProperty(value = "ABS结息报文体", required = true)
    @Valid
    private AbsInterestSettleSmtBody body;

}
