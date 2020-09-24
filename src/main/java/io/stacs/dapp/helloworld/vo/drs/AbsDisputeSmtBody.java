package io.stacs.dapp.helloworld.vo.drs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 买/卖方发起争议ABS报文，创建参数的Body
 *
 * @author Su Wenbo
 * @since 2020/9/21
 */
@Data
public class AbsDisputeSmtBody implements Serializable {

    @ApiModelProperty(value = "bid报文的message ID，确定是要确认哪一个订单；可以不用对方付款也可以直接确认收款", required = true)
    @NotNull(message = "messageId不能为空")
    private String messageId;

}
