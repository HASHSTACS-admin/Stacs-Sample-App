package io.stacs.dapp.helloworld.vo.drs;

import io.stacs.dapp.helloworld.vo.InstitutionIdentityKyc;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author HuangShengli
 * @since 2020/9/19
 */
@Data
public class InstitutionIdentitySmtBody implements Serializable {

    @ApiModelProperty(value = "目标地址，即需要被添加身份信息的地址", required = true)
    @NotNull(message = "目标地址不能为空")
    private String targetAddress;

    @ApiModelProperty(value = "数组格式，采用ISO 3166-1 alpha-2标准（正式分配代码）表示国家或居留地", required = true)
    @NotNull(message = "Identity不能为空")
    @NotEmpty(message = "Identity不能为空")
    private List<InstitutionIdentityKyc> identity;

    @ApiModelProperty(value = "添加身份信息使用的bdId", required = true)
    @NotNull(message = "BD ID不能为空")
    private String bdId;

}
