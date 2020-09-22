package io.stacs.dapp.helloworld.vo.drs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author HuangShengli
 * @since 2020/9/11
 */
@Data
public class DigitalCurrencySmtBody implements Serializable {

    /**
     * Asset ID
     */
    @ApiModelProperty(value = "Asset ID", required = true)
    @NotBlank(message = "asset id cannot be blank")
    private String assetId;
    /**
     * asset name
     */
    @ApiModelProperty(value = "asset name", required = true)
    @NotBlank(message = "asset name cannot be blank")
    private String assetName;
    /**
     * BD Id, this demo app uses the default 'SystemBD'
     */
    @ApiModelProperty(value = "BD Id", notes = "default value, BD:SystemBD", allowableValues = "SystemBD")
    private String bdId = "SystemBD";
    /**
     * Asset Owner Address
     */
    @ApiModelProperty(value = "asset owner address", required = true)
    @NotBlank(message = "asset owner address cannot be blank")
    private String ownerAddress;
    /**
     * Quantity
     */
    @ApiModelProperty(value = "issued quantity", required = true)
    @NotNull(message = "issued quantity cannot be null")
    private BigDecimal quantity;
    /**
     * Contract Address, maps to the asset Id in a 1-1 relationship
     */
    @ApiModelProperty(value = "contract address, sent by DRS callback API", hidden = true, readOnly = true)
    private String contractAddress;

    /**
     * underlying currency
     */
    @ApiModelProperty(value = "underlying currency", required = true, allowableValues = "USD,GBP,CNY,SGD")
    @NotBlank(message = "underlying currency cannot be blank")
    private String underlyingCurrency;

    /**
     * KYC Individual Type Prohibited List
     */
    @ApiModelProperty(value = "KYC Individual Type Prohibited List", hidden = true, notes = "refer to SMT format documentation for more info")
    private String individualProhibited;
    /**
     * KYC Individual Type Permitted List
     */
    @ApiModelProperty(value = "KYC Individual Type Permitted List", hidden = true, notes = "refer to SMT format documentation for more info")
    private String individualPermitted;
    /**
     * KYC Institution Type Prohibited List
     */
    @ApiModelProperty(value = "KYC Institution Type Prohibited List", hidden = true, notes = "refer to SMT format documentation for more info")
    private String institutionalProhibited;

    /**
     * KYC Institution Type Permitted List
     */
    @ApiModelProperty(value = "KYC Institution Type Permitted List", hidden = true, notes = "refer to SMT format documentation for more info")
    private String institutionalPermitted;
}
