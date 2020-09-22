package io.stacs.dapp.helloworld.vo.demo;

import io.stacs.dapp.helloworld.vo.drs.AbsSmtBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;

/**
 * @author HuangShengli
 * @ClassName AbsCreateRequest
 * @Description
 * @since 2020/9/19
 */
@Data
@ApiModel(value = "ABS创建参数")
public class AbsCreateRequest extends DemoBaseRequest {


    @ApiModelProperty(value = "创建ABS资产报文体", required = true)
    @Valid
    private AbsSmtBody body;
}
