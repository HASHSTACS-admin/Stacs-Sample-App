package io.stacs.dapp.helloworld.vo.demo;

import io.stacs.dapp.helloworld.constant.StatusEnum;
import io.stacs.dapp.helloworld.dao.po.BdFunctionPermissionRelation;
import io.stacs.dapp.helloworld.dao.po.SmtBd;
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
@ApiModel("bd Info")
public class SmtBdDemoVO {

    @ApiModelProperty(value = "SMT Format code")
    private String smtCode;

    @ApiModelProperty(value = "BD id")
    private String bdId;

    @ApiModelProperty(value = "Status:PROCESSING,SUCCESS,FAIL")
    private String status;

    @ApiModelProperty(value = "uuid")
    private String uuid;

    @ApiModelProperty(value = "Merchant Id")
    private String identifierId;

    @ApiModelProperty(value = "message id")
    private String messageId;

    @ApiModelProperty(value = "session id")
    private String sessionId;

    @ApiModelProperty(value = "creation timestamp")
    private Date createAt;

    @ApiModelProperty(value = "update timestamp")
    private Date updateAt;

    /**
     * PO to VO
     */
    public static SmtBdDemoVO parsePO(SmtBd bd) {
        SmtBdDemoVO vo = new SmtBdDemoVO();
        BeanUtils.copyProperties(bd, vo);
        vo.setStatus(StatusEnum.ChainStatus.getByCode(bd.getStatus()).getDesc());
        return vo;
    }
}
