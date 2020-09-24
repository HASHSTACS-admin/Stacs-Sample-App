package io.stacs.dapp.helloworld.vo.drs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Dispute of ABS order
 *
 * @author Su Wenbo
 * @since 2020/9/21
 */
@Data
public class AbsDisputeSmtBody implements Serializable {

    @ApiModelProperty(value = "bid message ID can be used to confirm collection directly without payment by the other party", required = true)
    @NotNull(message = "messageId cannot be null")
    private String messageId;

}
