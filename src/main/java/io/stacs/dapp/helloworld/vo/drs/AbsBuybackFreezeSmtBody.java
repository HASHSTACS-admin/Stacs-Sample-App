package io.stacs.dapp.helloworld.vo.drs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * ABS回购冻结，创建参数的Body
 *
 * @author Su Wenbo
 * @since 2020/9/21
 */
@Data
public class AbsBuybackFreezeSmtBody implements Serializable {

    @ApiModelProperty(value = "赎回冻结的资产ID", required = true)
    @NotBlank(message = "赎回冻结的资产ID不能为空")
    private String assetId;

    @ApiModelProperty(value = "附带信息")
    private String info;

}
