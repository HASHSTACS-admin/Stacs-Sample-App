package io.stacs.dapp.helloworld.vo.demo;

import io.stacs.dapp.helloworld.vo.drs.AbsDisputeSmtBody;
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
@ApiModel(value = "Dispute request for ABS")
public class AbsDisputeRequest extends DemoBaseRequest {

    @ApiModelProperty(value = "Dispute request for ABS", required = true)
    @Valid
    private AbsDisputeSmtBody body;

}
