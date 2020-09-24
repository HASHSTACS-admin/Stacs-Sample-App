package io.stacs.dapp.helloworld.vo.drs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author HuangShengli
 * @since 2020/9/19
 */
@Data
public class AbsBDSmtBody implements Serializable {


    @ApiModelProperty(value = "permission for redemption", required = true)
    @NotNull(message = "permission id cannot be null")
    private String buybackPermissionId;

    @ApiModelProperty(value = "permission verified for redemption freeze function, default permission value:DEFAULT", required = true)
    @NotNull(message = "permission id cannot be null")
    private String buybackFreezePermissionId;
    @ApiModelProperty(value = "permission verified for payment distribution, default permission value:DEFAULT", required = true)
    @NotNull(message = "permission id cannot be null")
    private String interestSettlePermissionId;
    @ApiModelProperty(value = "permission verified for additional issuance, default permission value:DEFAULT", required = true)
    @NotNull(message = "permission id cannot be null")
    private String additionalIssuePermissionId;
    @ApiModelProperty(value = "permission verified for asset freeze function, default permission value:DEFAULT", required = true)
    @NotNull(message = "permission id cannot be null")
    private String tokenFreezePermissionId;
    @ApiModelProperty(value = "permission verified for asset unfreeze function, default permission value:DEFAULT", required = true)
    @NotNull(message = "permission id cannot be null")
    private String tokenUnfreezePermissionId;
}
