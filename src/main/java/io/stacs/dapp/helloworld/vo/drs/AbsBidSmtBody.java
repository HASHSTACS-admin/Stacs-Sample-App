package io.stacs.dapp.helloworld.vo.drs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * ABS Bid order
 *
 * @author Su Wenbo
 * @since 2020/9/21
 */
@Data
public class AbsBidSmtBody implements Serializable {

    @ApiModelProperty(value = "quantity", required = true)
    @NotNull(message = "quantity cannot be null")
    private BigDecimal quantity;

}
