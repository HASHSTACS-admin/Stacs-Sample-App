package io.stacs.dapp.helloworld.vo.smt;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author HuangShengli
 * @since 2020/9/11
 */
@Builder
@Data
public class DigitalCurrencyBody implements Serializable {
    /**
     * 资产ID
     */
    private String assetId;
    /**
     * 资产名称
     */
    private String assetName;
    /**
     * BD id
     */
    private String bdId;
    /**
     * 资产拥有者
     */
    private String ownerAddress;
    /**
     * 数量
     */
    private BigDecimal quantity;
    /**
     * 合约地址
     */
    private String contractAddress;

    /**
     * 标记币种
     */
    private String underlyingCurrency;
}
