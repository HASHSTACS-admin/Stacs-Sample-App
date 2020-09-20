package io.stacs.dapp.helloworld.vo.drs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author HuangShengli
 * @since 2020/9/19
 */
@Data
public class PermissionSmtBody implements Serializable {


    @ApiModelProperty(value = "对此permission拥有修改权限的地址，只能填写地址[addr1,addr2,..]格式", required = true)
    @NotBlank(message = "地址列表不能为空")
    private String modifierAddress;

    @ApiModelProperty(value = "拥有permission权限的地址，只能填写地址[addr1,addr2,..]格式", required = true)
    @NotBlank(message = "地址列表不能为空")
    private String authorizedAddress;
}
