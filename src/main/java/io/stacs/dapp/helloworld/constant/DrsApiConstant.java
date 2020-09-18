package io.stacs.dapp.helloworld.constant;

/**
 * @author HuangShengli
 * @ClassName DrsApiConstant
 * @Description DRS API Constants
 * @since 2020/9/11
 */

public interface DrsApiConstant {

    /**
     * Create Wallet Address URL
     */
    String CREATE_ADDRESS_URL = "/smt/address/create";
    /**
     * Invoke Smart Contract Functions (with SMT) URL 
     */
    String SMT_SEND_URL = "/endpoint";
    /**
     * Query Balance of Wallet Address URL
     */
    String BALANCE_OF_URL = "/smt/contract/balanceOf";

    /**
     * Query Blockchain Transaction Results
     */
    String QUERY_SMT_RESULT = "/smt/message/getByIdentifierIdAndUuid";
}
