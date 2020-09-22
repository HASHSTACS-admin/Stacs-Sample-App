package io.stacs.dapp.helloworld.vo.demo;

import io.stacs.dapp.helloworld.constant.IdentityType;
import io.stacs.dapp.helloworld.vo.drs.IdentitySmtBody;
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
@ApiModel(value = "Identity parameters")
public class IdentityRequest extends DemoBaseRequest {

    @ApiModelProperty(value = "Identity Message body", required = true)
    @Valid
    private IdentitySmtBody body;

    @ApiModelProperty(value = "Identity Type", hidden = true)
    private IdentityType identityType;

}
