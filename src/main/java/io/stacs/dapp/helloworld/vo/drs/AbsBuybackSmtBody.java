package io.stacs.dapp.helloworld.vo.drs;

import io.stacs.dapp.helloworld.vo.AbsBuybackTarget;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * ABS redemption, create Request Body
 *
 * @author Su Wenbo
 * @since 2020/9/21
 */
@Data
public class AbsBuybackSmtBody implements Serializable {

    @ApiModelProperty(value = "asset Id for redemption", required = true)
    @NotBlank(message = "asset id cannot be blank")
    private String assetId;

    @ApiModelProperty(value = "A single address pair contains issued quantity and redemption quantity. " +
            "Maximum of 200 addresses for a single call. Multiple calls are separated by commas.", required = true)
    @Valid
    @NotNull(message = "For an address pair, issued quantity and redemption quantity cannot be null")
    @NotEmpty(message = "For an address pair, issued quantity and redemption quantity cannot be empty")
    private List<AbsBuybackTarget> targetAddrAndDisburseQtyAndRedeemQty;

}
