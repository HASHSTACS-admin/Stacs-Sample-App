package io.stacs.dapp.helloworld.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * kyc Identity feature for Individuals and Institutions
 *
 * @author Su Wenbo
 * @since 2020/9/22
 */
@Getter
@RequiredArgsConstructor
public enum IdentityType {

    /**
     * Individual Type with value 0 based on SMT format documentation
     */
    INDIVIDUAL((byte) 0, "smti-individual-identity-set-1-v1"),

    /**
     * Institution Type with value 1 based on SMT format documentation
     */
    INSTITUTION((byte) 1, "smti-institution-identity-set-1-v1");

    private final Byte code;

    private final String smtCode;

}
