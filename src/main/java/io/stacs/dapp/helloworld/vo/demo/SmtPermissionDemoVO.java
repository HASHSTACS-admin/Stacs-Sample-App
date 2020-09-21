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

    @ApiModelProperty(value = "报文CODE")
    private String smtCode;

    @ApiModelProperty(value = "状态:PROCESSING,SUCCESS,FAIL")
    private String status;

    @ApiModelProperty(value = "uuid")
    private String uuid;

    @ApiModelProperty(value = "商户号")
    private String identifierId;

    @ApiModelProperty(value = "message id")
    private String messageId;

    @ApiModelProperty(value = "message id")
    private String sessionId;

    @ApiModelProperty(value = "对此permission拥有修改权限的地址，只能填写地址,[40!c,.,40!c]数组json格式字符串")
    private String modifierAddress;

    @ApiModelProperty(value = "拥有permission权限的地址，只能填写地址,[40!c,.,40!c]数组json格式字符串")
    private String authorizedAddress;

    @ApiModelProperty(value = "创建时间")
    private Date createAt;

    @ApiModelProperty(value = "更新时间")
    private Date updateAt;

    /**
     * PO转VO
     */
    public static SmtPermissionDemoVO parsePO(SmtPermission permission) {
        SmtPermissionDemoVO vo = new SmtPermissionDemoVO();
        BeanUtils.copyProperties(permission, vo);
        vo.setStatus(StatusEnum.ChainStatus.getByCode(permission.getStatus()).getDesc());
        return vo;
    }
}
