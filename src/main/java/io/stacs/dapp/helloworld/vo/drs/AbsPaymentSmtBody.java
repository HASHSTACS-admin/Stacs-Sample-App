package io.stacs.dapp.helloworld.vo.drs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Payment for ABS
 *
 * @author Su Wenbo
 * @since 2020/9/21
 */
@Data
public class AbsPaymentSmtBody implements Serializable {

    @ApiModelProperty(value = "bid message id", required = true)
    @NotNull(message = "messageId cannot be null")
    private String messageId;

    @ApiModelProperty(value = "payment additional info, will be uploaded onto the blockchain", required = false)
    private String paymentInfo;

}
