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
     * 资产ID
     */
    @ApiModelProperty(value = "资产ID", required = true)
    @NotBlank(message = "资产ID不能为空")
    private String assetId;
    /**
     * 资产名称
     */
    @ApiModelProperty(value = "资产名称", required = true)
    @NotBlank(message = "资产ID不能为空")
    private String assetName;
    /**
     * BD id，demo使用SystemBD
     */
    @ApiModelProperty(value = "BD Id", notes = "使用系统BD:SystemBD", allowableValues = "SystemBD")
    private String bdId = "SystemBD";
    /**
     * 资产拥有者
     */
    @ApiModelProperty(value = "发行资产的持有者地址,可以和提交地址一致", required = true)
    @NotBlank(message = "持有人地址不能为空")
    private String ownerAddress;
    /**
     * 数量
     */
    @ApiModelProperty(value = "发行数量", required = true)
    @NotNull(message = "发行数量不能为空")
    private BigDecimal quantity;
    /**
     * 本次发行的合约地址，合约地址与assets ID一一对应
     */
    @ApiModelProperty(value = "合约地址(发行成功后DRS返回)", hidden = true, readOnly = true)
    private String contractAddress;

    /**
     * 标记币种
     */
    @ApiModelProperty(value = "标记币种", required = true, allowableValues = "USD,GBP,CNY,SGD")
    @NotBlank(message = "标记币种不能为空")
    private String underlyingCurrency;

    /**
     * KYC 个人限制
     */
    @ApiModelProperty(value = "KYC个人限制表达式,如果已了解规则再打开展示", hidden = true, notes = "数据格式较复杂，请参照报文文档")
    private String individualProhibited;
    /**
     * KYC 个人允许
     */
    @ApiModelProperty(value = "KYC个人允许表达式,如果已了解规则再打开展示", hidden = true, notes = "数据格式较复杂，请参照报文文档")
    private String individualPermitted;
    /**
     * KYC 机构限制
     */
    @ApiModelProperty(value = "KYC机构限制表达式,如果已了解规则再打开展示", hidden = true, notes = "数据格式较复杂，请参照报文文档")
    private String institutionalProhibited;

    /**
     * KYC 机构允许
     */
    @ApiModelProperty(value = "KYC机构允许表达式,如果已了解规则再打开展示", hidden = true, notes = "数据格式较复杂，请参照报文文档")
    private String institutionalPermitted;
}
