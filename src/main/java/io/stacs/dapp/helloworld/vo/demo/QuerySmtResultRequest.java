package io.stacs.dapp.helloworld.vo.demo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Huang Shengli
 * @date 2020-09-15
 */
@Data
@ApiModel(value = "根据商户号和UUID查询报文结果参数")
public class QuerySmtResultRequest implements Serializable {

    /**
     * uuid
     */
    @ApiModelProperty(value = "uuid，可以在数据库中查询得到", required = true)
    @NotBlank(message = "uuid不能为空")
    private String uuid;
    @ApiModelProperty(value = "商户号,示例中直接取配置的商户号，输入无效")
    private String identifierId;

}
