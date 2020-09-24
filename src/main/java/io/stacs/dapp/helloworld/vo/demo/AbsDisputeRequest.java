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
@ApiModel(value = "买/卖方发起争议ABS报文参数")
public class AbsDisputeRequest extends DemoBaseRequest {

    @ApiModelProperty(value = "买/卖方发起争议ABS报文体", required = true)
    @Valid
    private AbsDisputeSmtBody body;

}
