package io.stacs.dapp.helloworld.vo.demo;

import io.stacs.dapp.helloworld.vo.drs.IndividualIdentitySmtBody;
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
@ApiModel(value = "Individual Type Identity")
public class IndividualIdentityRequest extends DemoBaseRequest {

    @ApiModelProperty(value = "Individual Type Identity", required = true)
    @Valid
    private IndividualIdentitySmtBody body;

}
