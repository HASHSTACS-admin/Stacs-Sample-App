package io.stacs.dapp.helloworld.vo.demo;

import io.stacs.dapp.helloworld.vo.drs.AbsBDSmtBody;
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
@ApiModel(value = "ABS BD parameter setup")
public class AbsBDRequest extends DemoBaseRequest {


    @ApiModelProperty(value = "ABS BD parameter setup", required = true)
    @Valid
    private AbsBDSmtBody body;
}
