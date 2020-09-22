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
@ApiModel(value = "query wallet balance")
public class BalanceOfRequest implements Serializable {


    @ApiModelProperty(value = "asset id is required", required = true)
    @NotBlank(message = "asset id cannot be blank")
    private String assetId;

    @ApiModelProperty(value = "address is required", required = true)
    @NotBlank(message = "address cannot be blank")
    private String address;
}
