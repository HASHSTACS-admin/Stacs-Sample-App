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
@ApiModel("ABS Properties")
@Data
public class AssetAbsDemoVO {

    @ApiModelProperty(value = "SMT Code")
    private String smtCode;

    @ApiModelProperty(value = "Asset Id")
    private String assetId;

    @ApiModelProperty(value = "Asset Name")
    private String assetName;

    @ApiModelProperty(value = "ABS Type")
    private String absType;

    @ApiModelProperty(value = "Issuer Name")
    private String issuerName;

    @ApiModelProperty(value = "Unique Id for the asset")
    private String uniqueIdentifierCode;

    @ApiModelProperty(value = "Address that contains the asset after issuance")
    private String ownerAddress;

    @ApiModelProperty(value = "Quantity of asset")
    private BigDecimal quantity;

    @ApiModelProperty(value = "par value per token")
    private BigDecimal parValuePerToken;

    @ApiModelProperty(value = "Settlement Currency")
    private String settlementCurrency;

    @ApiModelProperty(value = "day count convention for calculating payments, e.g. 360/365")
    private Integer dayCountConvention;

    @ApiModelProperty(value = "frequency of coupon payments, Enum Optionsa are QUARTER:Quarterly, HALF_YEAR:Semi-annually, YEAR:Annually")
    private String couponFrequency;

    @ApiModelProperty(value = "First settlement date")
    private Date firstSettlementDate;

    @ApiModelProperty(value = "redemption date, accepted format is [10!n,...,10!n]")
    private String callDate;

    @ApiModelProperty(value = "token id used for payments and redemption")
    private String disbursementTokenId;

    @ApiModelProperty(value = "KYC: prohibited list for Individual Type")
    private String individualProhibited;

    @ApiModelProperty(value = "KYC permitted list for Individual Type")
    private String individualPermitted;

    @ApiModelProperty(value = "KYC: prohibited list for Institution Type")
    private String institutionalProhibited;

    @ApiModelProperty(value = "KYC: permitted list for Institution Type")
    private String institutionalPermitted;

    @ApiModelProperty(value = "Smart Contract address, is mapped 1 to 1 with the assets id")
    private String contractAddress;

    @ApiModelProperty(value = "Status:PROCESSING,SUCCESS,FAIL")
    private String status;

    @ApiModelProperty(value = "Contract Status:NORMAL,FROZEN,REDEEM_FROZEN,REDEEM")
    private String bizStatus;

    @ApiModelProperty(value = "uuid")
    private String uuid;

    @ApiModelProperty(value = "Merchant Id")
    private String identifierId;

    @ApiModelProperty(value = "message id")
    private String messageId;

    @ApiModelProperty(value = "session id")
    private String sessionId;

    @ApiModelProperty(value = "remark")
    private String remark;

    @ApiModelProperty(value = "creation timestamp")
    private Date createAt;

    @ApiModelProperty(value = "update timestamp")
    private Date updateAt;

    /**
     * PO to VO
     */
    public static AssetAbsDemoVO parsePO(AssetAbs assetAbs) {
        AssetAbsDemoVO vo = new AssetAbsDemoVO();
        BeanUtils.copyProperties(assetAbs, vo);
        vo.setStatus(StatusEnum.ChainStatus.getByCode(assetAbs.getStatus()).getDesc());
        vo.setBizStatus(StatusEnum.BizStatus.getByCode(assetAbs.getBizStatus()).getDesc());
        return vo;
    }
}