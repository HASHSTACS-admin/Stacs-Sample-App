package io.stacs.dapp.helloworld.vo;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author HuangShengli
 * @ClassName Kyc
 * @Description Kyc
 * @since 2020/9/21
 */
@Data
@ApiModel(value = "Individual Type KYC Info")
public class IndividualIdentityKyc implements Serializable {

    @ApiModelProperty(value = "nationality, uses ISO 3166-1 alpha-2 standard (official assignment code) to indicate country or residence", required = true)
    @JSONField(name = "Nationality")
    private String Nationality;

    @ApiModelProperty(value = "residency,array format, uses ISO 3166-1 alpha-2 standard (official assignment code) to indicate country or residence", required = true)
    @JSONField(name = "CountryOfResidency")
    private String CountryOfResidency;

    @ApiModelProperty(value = "Investor Type", required = true, allowableValues = "Retail,Accredited")
    @JSONField(name = "InvestorType")
    private String InvestorType;

    @ApiModelProperty(value = "Risk", required = true)
    @JSONField(name = "Risk")
    private Integer Risk;


}
