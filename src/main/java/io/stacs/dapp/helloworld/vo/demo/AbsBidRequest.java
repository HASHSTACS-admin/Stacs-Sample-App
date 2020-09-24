package io.stacs.dapp.helloworld.vo.demo;

import io.stacs.dapp.helloworld.vo.drs.AbsBidSmtBody;
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
@ApiModel(value = "ABS Bid Request by Buyer")
public class AbsBidRequest extends DemoBaseRequest {

    @ApiModelProperty(value = "ABS Bid Request by Buyer", required = true)
    @Valid
    private AbsBidSmtBody body;

}
