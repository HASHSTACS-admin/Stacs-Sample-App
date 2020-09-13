package io.stacs.dapp.helloworld.vo.demo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author HuangShengli
 * @ClassName BalanceOfRequest
 * @Description
 * @since 2020/9/12
 */
@Data
@ApiModel(value = "余额查询参数")
public class BalanceOfRequest implements Serializable {


    @ApiModelProperty(value = "需要查询的资产ID", required = true)
    @NotBlank(message = "资产ID不能为空")
    private String assetId;

    @ApiModelProperty(value = "需要查询的地址", required = true)
    @NotBlank(message = "查询地址不能为空")
    private String address;
}
