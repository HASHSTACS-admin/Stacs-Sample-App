package io.stacs.dapp.helloworld.vo.demo;

import io.stacs.dapp.helloworld.vo.drs.AbsPaymentSmtBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;

/**
 * @author HuangShengli
 * @ClassName PermissionRequest
 * @Description
 * @since 2020/9/19
 */
@Data
@ApiModel(value = "confirmation of payment for ABS by seller")
public class AbsConfirmRequest extends DemoBaseRequest {

    @ApiModelProperty(value = "confirmation of payment for ABS by seller", required = true)
    @Valid
    private AbsPaymentSmtBody body;

}
