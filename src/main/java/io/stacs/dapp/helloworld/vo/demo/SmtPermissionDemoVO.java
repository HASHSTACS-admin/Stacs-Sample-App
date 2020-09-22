package io.stacs.dapp.helloworld.vo.demo;

import io.stacs.dapp.helloworld.constant.StatusEnum;
import io.stacs.dapp.helloworld.dao.po.SmtBd;
import io.stacs.dapp.helloworld.dao.po.SmtPermission;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.Date;

/**
 * bd response
 */
@Data
@ApiModel("bd function info")
public class SmtPermissionDemoVO {

    @ApiModelProperty(value = "permission id")
    private String permissionId;

    @ApiModelProperty(value = "SMT format code")
    private String smtCode;

    @ApiModelProperty(value = "Status:PROCESSING,SUCCESS,FAIL")
    private String status;

    @ApiModelProperty(value = "uuid")
    private String uuid;

    @ApiModelProperty(value = "Merchant Id")
    private String identifierId;

    @ApiModelProperty(value = "message id")
    private String messageId;

    @ApiModelProperty(value = "message id")
    private String sessionId;

    @ApiModelProperty(value = "address that has authority to modify this permission, accepted format is [40!c...,40!c] in json format")
    private String modifierAddress;

    @ApiModelProperty(value = "address that this permission applies to, accepted format is [40!c...,40!c] in json format")
    private String authorizedAddress;

    @ApiModelProperty(value = "creation timestamp")
    private Date createAt;

    @ApiModelProperty(value = "update timestamp")
    private Date updateAt;

    /**
     * PO to VO
     */
    public static SmtPermissionDemoVO parsePO(SmtPermission permission) {
        SmtPermissionDemoVO vo = new SmtPermissionDemoVO();
        BeanUtils.copyProperties(permission, vo);
        vo.setStatus(StatusEnum.ChainStatus.getByCode(permission.getStatus()).getDesc());
        return vo;
    }
}
