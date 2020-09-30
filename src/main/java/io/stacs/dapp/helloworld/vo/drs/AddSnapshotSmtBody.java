package io.stacs.dapp.helloworld.vo.drs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Snapshot, create Request Body
 *
 * @author Su Wenbo
 * @since 2020/9/21
 */
@Data
public class AddSnapshotSmtBody implements Serializable {

    @ApiModelProperty(value = "description of snapshot", required = true)
    @NotBlank(message = "Description cannot be blank")
    private String description;

}
