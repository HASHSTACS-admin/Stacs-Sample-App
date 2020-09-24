package io.stacs.dapp.helloworld.vo.demo;

import io.stacs.dapp.helloworld.vo.drs.AbsCancelSmtBody;
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
@ApiModel(value = "cancel ABS Order by Buyer")
public class AbsCancelRequest extends DemoBaseRequest {

    @ApiModelProperty(value = "cancel ABS Order by Buyer", required = true)
    @Valid
    private AbsCancelSmtBody body;

}
