package io.stacs.dapp.helloworld.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author HuangShengli
 * @ClassName Kyc
 * @Description Kyc Parameters
 * @since 2020/9/21
 */
@Data
@ApiModel(value = "KYC Parameters")
public class Kyc implements Serializable {
    @ApiModelProperty(value = "nationality, based on ISO 3166-1 alpha-2 standard", required = true)
    private String Nationality;
    @ApiModelProperty(value = "residency, based on ISO 3166-1 alpha-2 standard", required = true)
    private String CountryOfResidency;
    @ApiModelProperty(value = "investor type", required = true, allowableValues = "Retail,Accredited")
    private String InvestorType;
    @ApiModelProperty(value = "risk level", required = true)
    private Integer HighestRisk;


}
