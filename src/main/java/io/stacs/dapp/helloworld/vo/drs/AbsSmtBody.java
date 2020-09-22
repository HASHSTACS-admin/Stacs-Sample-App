package io.stacs.dapp.helloworld.vo.drs;

import io.stacs.dapp.helloworld.vo.Kyc;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author HuangShengli
 * @ClassName AssetAbs
 * @Description 发行ABS参数
 * @since 2020/9/19
 */
@Data
public class AbsSmtBody implements Serializable {

    @ApiModelProperty(value = "资产代码,全局唯一", required = true)
    @NotBlank(message = "资产代码不能为空")
    private String assetId;
    @ApiModelProperty(value = "资产名称", required = true)
    @NotBlank(message = "资产名称不能为空")
    private String assetName;
    @ApiModelProperty(value = "债券子类别")
    private String absType;
    @ApiModelProperty(value = "发行人名称，即该债券的所有人")
    private String issuerName;
    @ApiModelProperty(value = "唯一识别码，同一证券在不同国家或体系会有不同的识别码，用以标识该证券")
    private String uniqueIdentifierCode;
    @ApiModelProperty(value = "资产发行后，资产都会在这个地址，相当于资产发行后的首个接收地址", required = true)
    @NotBlank(message = "owner地址不能为空")
    private String ownerAddress;
    @ApiModelProperty(value = "本次发行数量", required = true)
    @NotNull(message = "发行数量不能为空")
    private BigDecimal quantity;
    @ApiModelProperty(value = "面值")
    private String parValuePerToken;
    @ApiModelProperty(value = "结算币种，用来认购证券的币种，仅作展示")
    private String settlementCurrency;
    @ApiModelProperty(value = "日计数约定，一种用来确定两个券息日期之间天数的共识约定,Eg.360/365")
    private Integer dayCountConvention;
    @ApiModelProperty(value = "付息频率,QUARTER:Quarterly, HALF_YEAR:Semi-annually, YEAR:Annually", allowableValues = "QUARTER,HALF_YEAR,YEAR")
    private String couponFrequency;
    @ApiModelProperty(value = "第一次结算日期")
    private Date firstSettlementDate;
    @ApiModelProperty(value = "约定赎回日期,数组格式")
    private List<Date> callDate;
    @ApiModelProperty(value = "结息和回购所使用的token ID", required = true)
    @NotBlank(message = "token ID 不能为空")
    private String disbursementTokenId;
    @ApiModelProperty(value = "KYC:个人类型KYC的黑名单(参照报文格式)")
    private List<Kyc> individualProhibited;
    @ApiModelProperty(value = "KYC:个人类型KYC的白名单(参照报文格式)")
    private List<Kyc> individualPermitted;
    @ApiModelProperty(value = "KYC:机构类型KYC的黑名单(参照报文格式)")
    private List<Kyc> institutionalProhibited;

    @ApiModelProperty(value = "KYC:机构类型KYC的白名单(参照报文格式)")
    private List<Kyc> institutionalPermitted;
}
