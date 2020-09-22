package io.stacs.dapp.helloworld.vo.demo;

import io.stacs.dapp.helloworld.vo.drs.PermissionSmtBody;
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
@ApiModel(value = "Permission request parameters")
public class PermissionRequest extends DemoBaseRequest {


    @ApiModelProperty(value = "Permission message body", required = true)
    @Valid
    private PermissionSmtBody body;
}
