package io.stacs.dapp.helloworld.vo.drs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * ABS Freeze for Redemption, construct Request Body
 *
 * @author Su Wenbo
 * @since 2020/9/21
 */
@Data
public class AbsBuybackFreezeSmtBody implements Serializable {

    @ApiModelProperty(value = "Freeze for Redemption Asset ID", required = true)
    @NotBlank(message = "asset id cannot be blank")
    private String assetId;

    @ApiModelProperty(value = "additional info")
    private String info;

}
