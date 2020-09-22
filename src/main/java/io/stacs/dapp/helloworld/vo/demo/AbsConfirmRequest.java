package io.stacs.dapp.helloworld.vo.demo;

import io.stacs.dapp.helloworld.vo.drs.AbsPaymentSmtBody;
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
@ApiModel(value = "卖方确认收款ABS报文参数")
public class AbsConfirmRequest extends DemoBaseRequest {

    @ApiModelProperty(value = "卖方确认收款ABS报文体", required = true)
    @Valid
    private AbsPaymentSmtBody body;

}
