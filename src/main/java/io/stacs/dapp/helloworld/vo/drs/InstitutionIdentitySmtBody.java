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

    @ApiModelProperty(value = "target address, requires address to have Identity on the chain", required = true)
    @NotNull(message = "target address cannot be null")
    private String targetAddress;

    @ApiModelProperty(value = "array format, uses ISO 3166-1 alpha-2 standard (official assignment code) to indicate country or residence", required = true)
    @NotNull(message = "Identity cannot be null")
    @NotEmpty(message = "Identity cannot be empty")
    private List<InstitutionIdentityKyc> identity;

    @ApiModelProperty(value = "BD ID used by identity", required = true)
    @NotNull(message = "BD ID cannot be null")
    private String bdId;

}
