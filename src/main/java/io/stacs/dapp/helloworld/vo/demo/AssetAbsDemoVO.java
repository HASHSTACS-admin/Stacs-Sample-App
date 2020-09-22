package io.stacs.dapp.helloworld.vo.demo;

import io.stacs.dapp.helloworld.constant.StatusEnum;
import io.stacs.dapp.helloworld.dao.po.AssetAbs;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: yezaiyong
 * @create: 2020-09-21 17:09
 **/
@ApiModel("ABS发行信息")
@Data
public class AssetAbsDemoVO {

    @ApiModelProperty(value = "报文CODE")
    private String smtCode;

    @ApiModelProperty(value = "资产代码")
    private String assetId;

    @ApiModelProperty(value = "资产名称")
    private String assetName;

    @ApiModelProperty(value = "债券子类别")
    private String absType;

    @ApiModelProperty(value = "发行人名称")
    private String issuerName;

    @ApiModelProperty(value = "唯一识别码，同一证券在不同国家或体系会有不同的识别码，用以标识该证券")
    private String uniqueIdentifierCode;

    @ApiModelProperty(value = "资产发行后，资产都会在这个地址，相当于资产发行后的首个接收地址")
    private String ownerAddress;

    @ApiModelProperty(value = "本次发行数量")
    private BigDecimal quantity;

    @ApiModelProperty(value = "面值")
    private BigDecimal parValuePerToken;

    @ApiModelProperty(value = "结算币种")
    private String settlementCurrency;

    @ApiModelProperty(value = "日计数约定，一种用来确定两个券息日期之间天数的共识约定,Eg.360/365")
    private Integer dayCountConvention;

    @ApiModelProperty(value = "付息频率,QUARTER:Quarterly, HALF_YEAR:Semi-annually, YEAR:Annually")
    private String couponFrequency;

    @ApiModelProperty(value = "第一次结算日期")
    private Date firstSettlementDate;

    @ApiModelProperty(value = "约定赎回日期,[10!n,...,10!n]数组格式")
    private String callDate;

    @ApiModelProperty(value = "结息和回购所使用的token ID")
    private String disbursementTokenId;

    @ApiModelProperty(value = "KYC:个人类型KYC的黑名单")
    private String individualProhibited;

    @ApiModelProperty(value = "KYC:个人类型KYC的白名单")
    private String individualPermitted;

    @ApiModelProperty(value = "KYC:机构类型KYC的黑名单")
    private String institutionalProhibited;

    @ApiModelProperty(value = "KYC:机构类型KYC的白名单")
    private String institutionalPermitted;

    @ApiModelProperty(value = "本次发行的合约地址，合约地址与assets ID一一对应")
    private String contractAddress;

    @ApiModelProperty(value = "状态:PROCESSING,SUCCESS,FAIL")
    private String status;

    @ApiModelProperty(value = "状态:NORMAL,FROZEN,REDEEM_FROZEN,REDEEM")
    private String bizStatus;

    @ApiModelProperty(value = "uuid")
    private String uuid;

    @ApiModelProperty(value = "商户号")
    private String identifierId;

    @ApiModelProperty(value = "message id")
    private String messageId;

    @ApiModelProperty(value = "session id")
    private String sessionId;

    @ApiModelProperty(value = "备注信息")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    private Date createAt;

    @ApiModelProperty(value = "更新时间")
    private Date updateAt;

    /**
     * PO转VO
     */
    public static AssetAbsDemoVO parsePO(AssetAbs assetAbs) {
        AssetAbsDemoVO vo = new AssetAbsDemoVO();
        BeanUtils.copyProperties(assetAbs, vo);
        vo.setStatus(StatusEnum.ChainStatus.getByCode(assetAbs.getStatus()).getDesc());
        vo.setBizStatus(StatusEnum.BizStatus.getByCode(assetAbs.getBizStatus()).getDesc());
        return vo;
    }
}