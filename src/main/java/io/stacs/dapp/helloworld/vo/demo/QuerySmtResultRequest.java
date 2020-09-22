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
@ApiModel(value = "Query transaction results using Merchant Id and UUID")
public class QuerySmtResultRequest implements Serializable {

    /**
     * uuid
     */
    @ApiModelProperty(value = "uuid", required = true)
    @NotBlank(message = "uuid cannot be blank.")
    private String uuid;
    @ApiModelProperty(value = "Merchant Id, retrieved from config and cannot be input by default")
    private String identifierId;

}
