package io.stacs.dapp.helloworld.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 对资产操作类型枚举，打快照或者冻结等
 *
 * @author Su Wenbo
 * @since 2020/9/24
 */
@Getter
@RequiredArgsConstructor
public enum AssetOperationType {

    /**
     * 打快照，编号 0
     * 这个操作可以获取到block height
     */
    ADD_SNAPSHOT((byte) 0, "ADD_SNAPSHOT"),

    /**
     * 回购冻结，编号 1
     * 这个操作需要校验冻结的资产id与查询的是否一致
     */
    BUYBACK_FREEZE((byte) 1, "BUYBACK_FREEZE");

    private final Byte code;

    private final String desc;

}
