package io.stacs.dapp.helloworld.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author HuangShengli
 * @ClassName Txid
 * @Description transaction ids are returned in this format [{txid:xxx,txType:xxx}]
 * @since 2020/5/11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Txid implements Serializable {
    /**
     * tx id
     */
    private String txId;

    /**
     * tx type
     */
    private String txType;

}
