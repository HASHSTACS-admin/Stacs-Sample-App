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
     * BD id，demo使用SystemBD
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
     * 本次发行的合约地址，合约地址与assets ID一一对应
     */
    private String contractAddress;

    /**
     * 标记币种
     */
    private String underlyingCurrency;

    /**
     * KYC 个人限制
     */
    private String individualProhibited;
    /**
     * KYC 个人允许
     */
    private String individualPermitted;
    /**
     * KYC 机构限制
     */
    private String institutionalProhibited;

    /**
     * KYC 机构允许
     */
    private String institutionalPermitted;
}
