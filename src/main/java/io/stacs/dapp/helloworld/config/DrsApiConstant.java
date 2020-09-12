package io.stacs.dapp.helloworld.config;

/**
 * @author HuangShengli
 * @ClassName DrsApiConstant
 * @Description TODO
 * @since 2020/9/11
 */

public interface DrsApiConstant {

    /**
     * 创建地址接口url
     */
    String CREATE_ADDRESS_URL = "/smt/address/create";
    /**
     *
     */
    String SMT_SEND_URL = "/endpoint";
    /**
     * 查询余额url
     */
    String BALANCE_OF_URL = "/smt/contract/balanceOf";
}
