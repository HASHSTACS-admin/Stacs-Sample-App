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
@ApiModel(value = "Snapshot")
public class AddSnapshotRequest extends DemoBaseRequest {

    @ApiModelProperty(value = "Snapshot", required = true)
    @Valid
    private AddSnapshotSmtBody body;

}
