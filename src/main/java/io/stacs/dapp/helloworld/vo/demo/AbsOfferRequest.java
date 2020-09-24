package io.stacs.dapp.helloworld.vo.demo;

import io.stacs.dapp.helloworld.vo.drs.AbsOfferSmtBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;

/**
 * @author HuangShengli
 * @ClassName AbsOfferRequest
 * @Description
 * @since 2020/9/19
 */
@Data
@ApiModel(value = "ABS offer单参数")
public class AbsOfferRequest extends DemoBaseRequest {


    @ApiModelProperty(value = "ABS offer单报文体", required = true)
    @Valid
    private AbsOfferSmtBody body;
}
