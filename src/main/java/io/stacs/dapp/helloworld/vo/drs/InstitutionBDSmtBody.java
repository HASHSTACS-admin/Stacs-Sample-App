package io.stacs.dapp.helloworld.vo.drs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 机构业务类BD报文，创建参数的Body
 *
 * @author Su Wenbo
 * @since 2020/9/21
 */
@Data
public class InstitutionBDSmtBody implements Serializable {

    @ApiModelProperty(value = "给地址设置身份认证使用的权限", required = true)
    @NotNull(message = "permission id 不能为空")
    private String setIdentityPermissionId;

    @ApiModelProperty(value = "冻结地址的权限", required = true)
    @NotNull(message = "permission id 不能为空")
    private String freezeIdentityPermissionId;

    @ApiModelProperty(value = "解冻地址的权限", required = true)
    @NotNull(message = "permission id 不能为空")
    private String unfreezeIdentityPermissionId;

    @ApiModelProperty(value = "做存证的权限", required = true)
    @NotNull(message = "permission id 不能为空")
    private String addAttestationPermissionId;

}
