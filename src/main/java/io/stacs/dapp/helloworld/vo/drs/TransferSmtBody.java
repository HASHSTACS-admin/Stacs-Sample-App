package io.stacs.dapp.helloworld.vo.drs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author HuangShengli
 * @since 2020/9/11
 */
@Data
public class TransferSmtBody implements Serializable {

    /**
     * Asset ID
     */
    @ApiModelProperty(value = "Asset ID", required = true)
    @NotBlank(message = "Asset Id cannot be blank")
    private String assetId;
    /**
     * Target Address
     */
    @ApiModelProperty(value = "target address", required = true)
    @NotBlank(message = "target address cannot be blank")
    private String targetAddress;

    /**
     * transfer quantity
     */
    @ApiModelProperty(value = "transfer quantity", required = true)
    @NotNull(message = "transfer quantity cannot be null")
    private BigDecimal quantity;
}
