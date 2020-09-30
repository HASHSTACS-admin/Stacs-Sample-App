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
@ApiModel(value = "Query Asset Holders and their Balance")
public class AssetHoldersRequest implements Serializable {

    @ApiModelProperty(value = "Asset Id, uses SMTA Format", required = true)
    @NotBlank(message = "Asset Id cannot be blank")
    private String assetId;

    @ApiModelProperty(value = "operation Id from callback API of a snapshot or freeze")
    private Long operationId;

}
