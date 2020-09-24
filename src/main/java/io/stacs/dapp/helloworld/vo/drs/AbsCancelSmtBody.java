package io.stacs.dapp.helloworld.vo.drs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Buyer reject ABS order
 *
 * @author Su Wenbo
 * @since 2020/9/21
 */
@Data
public class AbsCancelSmtBody implements Serializable {

    @ApiModelProperty(value = "bid message Id", required = true)
    @NotNull(message = "messageId cannot be null")
    private String messageId;

    @ApiModelProperty(value = "additional info that is saved to the blockchain", required = false)
    private String info;

}
