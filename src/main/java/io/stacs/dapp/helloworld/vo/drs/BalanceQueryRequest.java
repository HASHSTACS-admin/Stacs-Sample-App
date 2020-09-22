package io.stacs.dapp.helloworld.vo.drs;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;

/**
 * @author Huang Shengli
 * @date 2020-03-14
 */
@Data
@Builder
public class BalanceQueryRequest implements Serializable {

    /**
     * Merchant Id
     */
    @NonNull
    private String identifierId;
    /**
     * assetId
     */
    @NonNull
    private String assetId;

    /**
     * target address for query
     */
    @NonNull
    private String address;
}
