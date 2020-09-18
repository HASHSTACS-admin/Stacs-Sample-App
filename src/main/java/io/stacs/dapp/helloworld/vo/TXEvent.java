package io.stacs.dapp.helloworld.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author HuangShengli
 * @ClassName TXEvent
 * @Description Smart Contract Event
 * @since 2020/9/14
 */
@Data
public class TXEvent implements Serializable {
    private String assetId;
    private String contractAddress;
    private List<String> data;
    private String type;
}
