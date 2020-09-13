package io.stacs.dapp.helloworld.vo.demo;

import io.stacs.dapp.helloworld.vo.drs.TransferSmtBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;

/**
 * @author Huang Shengli
 * @Description 转账类:数字货币，ABS，债券，凭证
 * @date 2020-09-13
 */
@Data
@ApiModel(value = "数字货币，ABS，债券，凭证的转账")
public class TransferSmtRequest extends DemoBaseRequest {

    @ApiModelProperty(value = "转账操作报文体", required = true)
    @Valid
    private TransferSmtBody body;
}
