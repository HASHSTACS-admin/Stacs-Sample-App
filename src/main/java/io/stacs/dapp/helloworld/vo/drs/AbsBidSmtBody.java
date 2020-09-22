package io.stacs.dapp.helloworld.vo.drs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 买方出价认购ABS参数，创建参数的Body
 *
 * @author Su Wenbo
 * @since 2020/9/21
 */
@Data
public class AbsBidSmtBody implements Serializable {

    @ApiModelProperty(value = "购买数量", required = true)
    @NotNull(message = "购买数量不能为空")
    private BigDecimal quantity;

}
