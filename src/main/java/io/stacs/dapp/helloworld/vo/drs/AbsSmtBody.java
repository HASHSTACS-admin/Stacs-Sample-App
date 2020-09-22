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
 * @Description ABS Issuance Parameters
 * @since 2020/9/19
 */
@Data
public class AbsSmtBody implements Serializable {

    @ApiModelProperty(value = "unique asset id", required = true)
    @NotBlank(message = "asset id cannot be blank")
    private String assetId;
    @ApiModelProperty(value = "asset name", required = true)
    @NotBlank(message = "asset name cannot be blank.")
    private String assetName;
    @ApiModelProperty(value = "ABS type")
    private String absType;
    @ApiModelProperty(value = "issuer name")
    private String issuerName;
    @ApiModelProperty(value = "unique identifier code for the asset")
    private String uniqueIdentifierCode;
    @ApiModelProperty(value = "Owner address, assets after issuance will all reside in this address", required = true)
    @NotBlank(message = "owner address cannot be blank.")
    private String ownerAddress;
    @ApiModelProperty(value = "asset quantity", required = true)
    @NotNull(message = "asset quantity cannot be null.")
    private BigDecimal quantity;
    @ApiModelProperty(value = "par value per token")
    private String parValuePerToken;
    @ApiModelProperty(value = "settlement currency")
    private String settlementCurrency;
    @ApiModelProperty(value = "day count convention, used in calculating interest or coupon payments, for example 360/365")
    private Integer dayCountConvention;
    @ApiModelProperty(value = "Coupon Payment frequency, QUARTER:Quarterly, HALF_YEAR:Semi-annually, YEAR:Annually", allowableValues = "QUARTER,HALF_YEAR,YEAR")
    private String couponFrequency;
    @ApiModelProperty(value = "First settlement date")
    private Date firstSettlementDate;
    @ApiModelProperty(value = "redemption date")
    private List<Date> callDate;
    @ApiModelProperty(value = "token id for coupon payment and redemption", required = true)
    @NotBlank(message = "token ID cannot be blank.")
    private String disbursementTokenId;
    @ApiModelProperty(value = "KYC:prohibited list for Individual Type")
    private List<Kyc> individualProhibited;
    @ApiModelProperty(value = "KYC:permitted list for Individual Type")
    private List<Kyc> individualPermitted;
    @ApiModelProperty(value = "KYC:prohibited list for Institution Type")
    private List<Kyc> institutionalProhibited;

    @ApiModelProperty(value = "KYC:permitted list for Institution Type")
    private List<Kyc> institutionalPermitted;
}
