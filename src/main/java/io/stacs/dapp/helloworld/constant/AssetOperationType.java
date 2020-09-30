package io.stacs.dapp.helloworld.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum for Asset Operation Types such as freeze and snapshot
 *
 * @author Su Wenbo
 * @since 2020/9/24
 */
@Getter
@RequiredArgsConstructor
public enum AssetOperationType {

    /**
     * Snapshot, value is 0
     * this retrieves a block height from the DRS
     */
    ADD_SNAPSHOT((byte) 0, "ADD_SNAPSHOT"),

    /**
     * Freezing of tokens before redemption, value is 1
     * this requires verification of asset id on the chain
     */
    BUYBACK_FREEZE((byte) 1, "BUYBACK_FREEZE");

    private final Byte code;

    private final String desc;

}
