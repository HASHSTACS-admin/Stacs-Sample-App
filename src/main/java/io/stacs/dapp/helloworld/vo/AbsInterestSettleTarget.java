package io.stacs.dapp.helloworld.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * ABS结息目标
 *
 * @author Su Wenbo
 * @since 2020/9/21
 */
@Data
@ApiModel(value = "结息目标")
public class AbsInterestSettleTarget implements Serializable {

    @ApiModelProperty(value = "结息目标地址", required = true)
    @NotBlank(message = "结息目标地址不能为空")
    private String targetAddress;

    @ApiModelProperty(value = "数量", required = true)
    @NotNull(message = "数量不能为空")
    private BigDecimal qty;

}
