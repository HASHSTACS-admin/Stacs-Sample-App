package io.stacs.dapp.helloworld.vo.drs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 打快照参数，创建参数的Body
 *
 * @author Su Wenbo
 * @since 2020/9/21
 */
@Data
public class AddSnapshotSmtBody implements Serializable {

    @ApiModelProperty(value = "对本次快照操作进行备注或描述", required = true)
    @NotBlank(message = "对本次快照操作的描述不能为空")
    private String description;

}
