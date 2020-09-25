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
 * ABS结息冻结，创建参数的Body
 *
 * @author Su Wenbo
 * @since 2020/9/21
 */
@Data
public class AbsInterestSettleSmtBody implements Serializable {

    @ApiModelProperty(value = "结息的资产ID", required = true)
    @NotBlank(message = "结息的资产ID不能为空")
    private String assetId;

    @ApiModelProperty(value = "付息的目标地址和数量，一次最多200个地址。“...”表示可以输入多组也可以只输入一组，当为多组时，用英文逗号分隔。", required = true)
    @Valid
    @NotNull(message = "付息的目标地址和数量不能为空")
    @NotEmpty(message = "付息的目标地址和数量不能为空")
    private List<AbsInterestSettleTarget> targetAddrAndQty;

}
