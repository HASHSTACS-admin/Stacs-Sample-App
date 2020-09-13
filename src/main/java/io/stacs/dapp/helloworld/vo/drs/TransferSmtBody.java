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
     * 资产ID
     */
    @ApiModelProperty(value = "资产ID", required = true)
    @NotBlank(message = "资产ID不能为空")
    private String assetId;
    /**
     * 目标地址
     */
    @ApiModelProperty(value = "目标地址", required = true)
    @NotBlank(message = "目标地址不能为空")
    private String targetAddress;

    /**
     * 数量
     */
    @ApiModelProperty(value = "转账数量", required = true)
    @NotNull(message = "转账数量不能为空")
    private BigDecimal quantity;
}
