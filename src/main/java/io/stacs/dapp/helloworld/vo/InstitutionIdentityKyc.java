package io.stacs.dapp.helloworld.vo;

import com.alibaba.fastjson.annotation.JSONField;
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
@ApiModel(value = "机构KYC信息")
public class InstitutionIdentityKyc implements Serializable {

    @ApiModelProperty(value = "注册国家，采用ISO 3166-1 alpha-2标准（正式分配代码）", required = true)
    @JSONField(name = "CountryOfIncorporation")
    private String CountryOfIncorporation;

    @ApiModelProperty(value = "运行国家", required = true)
    @JSONField(name = "CountryOfOperation")
    private String CountryOfOperation;

}
