package io.stacs.dapp.helloworld.vo.demo;

import io.stacs.dapp.helloworld.constant.StatusEnum;
import io.stacs.dapp.helloworld.dao.po.AssetAbs;
import io.stacs.dapp.helloworld.dao.po.BdFunctionPermissionRelation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.Date;

/**
 * @author HuangShengli
 * @ClassName BdFunctionPermissionRelation
 * @Description BD方法和权限的关系表
 * @since 2020/9/18
 */
@Data
@ApiModel("bd function info")
public class BdFunctionPermissionRelationDemoVO {


    @ApiModelProperty(value = "BD id")
    private String bdId;

    @ApiModelProperty(value = "permission id")
    private String permissionId;

    @ApiModelProperty(value = "报文中描述的")
    private String functionName;

    @ApiModelProperty(value = "创建时间")
    private Date createAt;

    @ApiModelProperty(value = "更新时间")
    private Date updateAt;


    /**
     * PO转VO
     */
    public static BdFunctionPermissionRelationDemoVO parsePO(BdFunctionPermissionRelation relation) {
        BdFunctionPermissionRelationDemoVO vo = new BdFunctionPermissionRelationDemoVO();
        BeanUtils.copyProperties(relation, vo);
        return vo;
    }
}
