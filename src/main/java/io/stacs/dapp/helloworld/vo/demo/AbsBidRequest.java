package io.stacs.dapp.helloworld.vo.demo;

import io.stacs.dapp.helloworld.vo.drs.AbsBDSmtBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;

/**
 * @author HuangShengli
 * @ClassName PermissionRequest
 * @Description
 * @since 2020/9/19
 */
@Data
@ApiModel(value = "买方出价认购ABS参数")
public class AbsBidRequest extends DemoBaseRequest {

    @ApiModelProperty(value = "买方出价认购ABS报文体", required = true)
    @Valid
    private AbsBDSmtBody body;

}
