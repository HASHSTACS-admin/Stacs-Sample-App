package io.stacs.dapp.helloworld.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * ABS Redemption
 *
 * @author Su Wenbo
 * @since 2020/9/21
 */
@Data
@ApiModel(value = "ABS Redemption")
public class AbsBuybackTarget implements Serializable {

    @ApiModelProperty(value = "target address", required = true)
    @NotBlank(message = "target address cannot be blank")
    private String targetAddress;

    @ApiModelProperty(value = "disbursement quantity", required = true)
    @NotNull(message = "disbursement quantity cannot be null")
    private BigDecimal disburseQty;

    @ApiModelProperty(value = "redemption quantity", required = true)
    @NotNull(message = "redemption quantity cannot be null")
    private BigDecimal redeemQty;

}
