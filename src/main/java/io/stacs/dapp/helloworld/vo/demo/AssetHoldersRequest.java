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
@ApiModel(value = "查询资产的Holders参数")
public class AssetHoldersRequest implements Serializable {

    @ApiModelProperty(value = "资产ID，使用SMTA报文发行资产后，可得到对应的资产ID", required = true)
    @NotBlank(message = "资产ID不能为空")
    private String assetId;

    @ApiModelProperty(value = "打快照或者冻结回购返回的ID")
    private Long operationId;

}
