package io.stacs.dapp.helloworld.vo.smt;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author HuangShengli
 * @ClassName DigitalCurrencySimpleRequest
 * @Description 为了方便用户体验，只需输入极简的参数:资产ID和资产名称，其他报文参数由系统默认填充
 * @since 2020/9/12
 */
@Data
@ApiModel(value = "发行数字货币简要参数", description = "为了快速体验，发行数字货币的参数极简，系统为报文的其他参数做了默认填充，完整报文请参考...")
public class DigitalCurrencySimpleRequest implements Serializable {
    /**
     * 资产ID
     */
    @ApiModelProperty(value = "资产ID", required = true)
    private String assetId;
    /**
     * 资产名称
     */
    @ApiModelProperty(value = "资产名称", required = true)
    private String assetName;
}
