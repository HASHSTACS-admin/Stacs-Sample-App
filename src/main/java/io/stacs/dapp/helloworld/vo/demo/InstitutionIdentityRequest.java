package io.stacs.dapp.helloworld.vo.demo;

import io.stacs.dapp.helloworld.vo.drs.InstitutionIdentitySmtBody;
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
@ApiModel(value = "Institution Type Identity")
public class InstitutionIdentityRequest extends DemoBaseRequest {

    @ApiModelProperty(value = "Institution Type Identity", required = true)
    @Valid
    private InstitutionIdentitySmtBody body;

}
