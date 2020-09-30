package io.stacs.dapp.helloworld.vo.drs;

import io.stacs.dapp.helloworld.vo.AbsInterestSettleTarget;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * ABS freeze for payments, create Request Body
 *
 * @author Su Wenbo
 * @since 2020/9/21
 */
@Data
public class AbsInterestSettleSmtBody implements Serializable {

    @ApiModelProperty(value = "asset Id for payments", required = true)
    @NotBlank(message = "asset id cannot be blank")
    private String assetId;

    @ApiModelProperty(value = "Maximum of 200 addresses for a single call. Multiple calls are separated by commas.", required = true)
    @Valid
    @NotNull(message = "The destination address and quantity of interest payment cannot be null")
    @NotEmpty(message = "The destination address and quantity of interest payment cannot be empty")
    private List<AbsInterestSettleTarget> targetAddrAndQty;

}
