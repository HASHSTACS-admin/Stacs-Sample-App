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
@ApiModel(value = "Institution Type KYC Info")
public class InstitutionIdentityKyc implements Serializable {

    @ApiModelProperty(value = "country of incorporation, uses ISO 3166-1 alpha-2 standard (official assignment code) to indicate country or residence", required = true)
    @JSONField(name = "CountryOfIncorporation")
    private String CountryOfIncorporation;

    @ApiModelProperty(value = "country of operation", required = true)
    @JSONField(name = "CountryOfOperation")
    private String CountryOfOperation;

}
