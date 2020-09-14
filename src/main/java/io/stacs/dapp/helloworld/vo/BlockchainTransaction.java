package io.stacs.dapp.helloworld.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author HuangShengli
 * @ClassName BlockchainTransaction
 * @Description 报文尾中，交易数据结构
 * @since 2020/9/14
 */
@Data
public class BlockchainTransaction implements Serializable {

    private int blockHeight;
    private long blockTime;
    private List<TXEvent> event;
    private Boolean status;
    private String txId;
    private String txType;


}
