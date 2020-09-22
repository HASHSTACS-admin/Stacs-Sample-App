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
@ApiModel(value = "个人Identity设置报文参数")
public class IndividualIdentityRequest extends DemoBaseRequest {

    @ApiModelProperty(value = "个人Identity设置报文体", required = true)
    @Valid
    private IndividualIdentitySmtBody body;

}
