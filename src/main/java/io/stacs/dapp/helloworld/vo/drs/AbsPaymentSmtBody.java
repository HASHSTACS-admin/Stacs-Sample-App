package io.stacs.dapp.helloworld.vo.drs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 买方付款ABS报文，创建参数的Body
 *
 * @author Su Wenbo
 * @since 2020/9/21
 */
@Data
public class AbsPaymentSmtBody implements Serializable {

    @ApiModelProperty(value = "bid报文的message ID，确定对哪一个订单付款", required = true)
    @NotNull(message = "messageId不能为空")
    private String messageId;

    @ApiModelProperty(value = "填写后会存储在这笔区块链交易的extend data里", required = false)
    private String paymentInfo;

}
