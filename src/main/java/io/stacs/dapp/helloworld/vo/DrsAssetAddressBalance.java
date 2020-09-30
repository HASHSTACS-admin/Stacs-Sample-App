package io.stacs.dapp.helloworld.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Su Wenbo
 * @since 2020/9/25
 */
@Data
public class DrsAssetAddressBalance implements Serializable {

    private String address;

    private BigDecimal balance;

}
