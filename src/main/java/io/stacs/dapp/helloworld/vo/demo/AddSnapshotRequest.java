package io.stacs.dapp.helloworld.vo.demo;

import io.stacs.dapp.helloworld.vo.drs.AddSnapshotSmtBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;

/**
 * @author Su Wenbo
 * @since 2020/9/24
 */
@Data
@ApiModel(value = "打快照参数")
public class AddSnapshotRequest extends DemoBaseRequest {

    @ApiModelProperty(value = "打快照报文体", required = true)
    @Valid
    private AddSnapshotSmtBody body;

}
