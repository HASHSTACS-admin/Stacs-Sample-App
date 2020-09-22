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


    @ApiModelProperty(value = "回购使用的permission", required = true)
    @NotNull(message = "permission id 不能为空")
    private String buybackPermissionId;

    @ApiModelProperty(value = "回购冻结使用的permission", required = true)
    @NotNull(message = "permission id 不能为空")
    private String buybackFreezePermissionId;
    @ApiModelProperty(value = "结息使用的permission", required = true)
    @NotNull(message = "permission id 不能为空")
    private String interestSettlePermissionId;
    @ApiModelProperty(value = "增发使用的permission", required = true)
    @NotNull(message = "permission id 不能为空")
    private String additionalIssuePermissionId;
    @ApiModelProperty(value = "资产冻结使用的permission", required = true)
    @NotNull(message = "permission id 不能为空")
    private String tokenFreezePermissionId;
    @ApiModelProperty(value = "资产解冻使用的permission", required = true)
    @NotNull(message = "permission id 不能为空")
    private String tokenUnfreezePermissionId;
}
