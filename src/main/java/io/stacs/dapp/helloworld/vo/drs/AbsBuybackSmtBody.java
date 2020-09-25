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
 * ABS赎回，创建参数的Body
 *
 * @author Su Wenbo
 * @since 2020/9/21
 */
@Data
public class AbsBuybackSmtBody implements Serializable {

    @ApiModelProperty(value = "赎回的资产ID", required = true)
    @NotBlank(message = "赎回的资产ID不能为空")
    private String assetId;

    @ApiModelProperty(value = "对一个地址对发放数量与回购数量。请注意，回购数量必须与地址上实际持有量相等。" +
            "一次最多200个地址。“...”表示可以输入多组也可以只输入一组，当为多组时，用英文逗号分隔。", required = true)
    @Valid
    @NotNull(message = "对一个地址对发放数量与回购数量不能为空")
    @NotEmpty(message = "对一个地址对发放数量与回购数量不能为空")
    private List<AbsBuybackTarget> targetAddrAndDisburseQtyAndRedeemQty;

}
