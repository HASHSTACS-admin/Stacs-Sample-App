package io.stacs.dapp.helloworld.vo.drs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Institution Type Message Body for BD
 *
 * @author Su Wenbo
 * @since 2020/9/21
 */
@Data
public class InstitutionBDSmtBody implements Serializable {

    @ApiModelProperty(value = "set permission for address for identity", required = true)
    @NotNull(message = "permission id cannot be null")
    private String setIdentityPermissionId;

    @ApiModelProperty(value = "permission for address for freeze function", required = true)
    @NotNull(message = "permission id cannot be null")
    private String freezeIdentityPermissionId;

    @ApiModelProperty(value = "permission for address for unfreeze function", required = true)
    @NotNull(message = "permission id cannot be null")
    private String unfreezeIdentityPermissionId;

    @ApiModelProperty(value = "permission for address to append data to the chain", required = true)
    @NotNull(message = "permission id cannot be null")
    private String addAttestationPermissionId;

}
