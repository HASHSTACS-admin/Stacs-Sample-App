package io.stacs.dapp.helloworld.vo.drs;

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
public class PermissionSmtBody implements Serializable {

    @ApiModelProperty(value = "address with authority to modify this permission, allowed format is in the form of [addr1,addr2,...]", required = true)
    @NotNull(message = "modiferAddress cannot be null")
    @NotEmpty(message = "modiferAddress cannot be empty")
    private List<String> modifierAddress;

    @ApiModelProperty(value = "address with permission, allowed format is in the form of [addr1,addr2,...]", required = true)
    @NotNull(message = "authorizedAddress cannot be null")
    @NotEmpty(message = "authorizedAddress cannot be empty")
    private List<String> authorizedAddress;
}
