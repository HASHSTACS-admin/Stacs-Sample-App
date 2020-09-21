package io.stacs.dapp.helloworld.vo.demo;

import io.stacs.dapp.helloworld.vo.drs.InstitutionBDSmtBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;

/**
 * 机构业务类BD报文，创建参数
 *
 * @author Su Wenbo
 * @since 2020/9/21
 */
@Data
@ApiModel(value = "机构业务类BD创建参数")
public class InstitutionBDRequest extends DemoBaseRequest {

    @ApiModelProperty(value = "机构业务类BD报文体", required = true)
    @Valid
    private InstitutionBDSmtBody body;

}
