package io.stacs.dapp.helloworld.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Su Wenbo
 * @since 2020/9/22
 */
@Getter
@RequiredArgsConstructor
public enum IdentityType {

    /**
     * 个人，编号0
     */
    INDIVIDUAL((byte) 0, "smti-individual-identity-set-1-v1"),

    /**
     * 机构，编号 1
     */
    INSTITUTION((byte) 1, "smti-institution-identity-set-1-v1");

    private final Byte code;

    private final String smtCode;

}
