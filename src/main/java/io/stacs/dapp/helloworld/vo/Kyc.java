package io.stacs.dapp.helloworld.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author HuangShengli
 * @ClassName Kyc
 * @Description Kyc 信息
 * @since 2020/9/21
 */
@Data
@ApiModel(value = "KYC信息")
public class Kyc implements Serializable {
    @ApiModelProperty(value = "国籍，采用ISO 3166-1 alpha-2标准（正式分配代码）", required = true)
    private String Nationality;
    @ApiModelProperty(value = "居住国，采用ISO 3166-1 alpha-2标准（正式分配代码）", required = true)
    private String CountryOfResidency;
    @ApiModelProperty(value = "投资者类型", required = true, allowableValues = "Retail,Accredited")
    private String InvestorType;
    @ApiModelProperty(value = "风险等级", required = true)
    private Integer HighestRisk;


}
