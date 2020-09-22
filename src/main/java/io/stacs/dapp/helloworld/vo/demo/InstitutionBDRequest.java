package io.stacs.dapp.helloworld.vo.demo;

import io.stacs.dapp.helloworld.vo.drs.InstitutionBDSmtBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;

/**
 * Institution Type, BD parameters
 *
 * @author Su Wenbo
 * @since 2020/9/21
 */
@Data
@ApiModel(value = "Institution Type BD parameters")
public class InstitutionBDRequest extends DemoBaseRequest {

    @ApiModelProperty(value = "Institution Type BD parameters", required = true)
    @Valid
    private InstitutionBDSmtBody body;

}
