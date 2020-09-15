package io.stacs.dapp.helloworld.constant;

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
     * 所有报文发送的统一url
     */
    String SMT_SEND_URL = "/endpoint";
    /**
     * 查询余额url
     */
    String BALANCE_OF_URL = "/smt/contract/balanceOf";

    /**
     * 根据商户号和UUID查询报文结果
     */
    String QUERY_SMT_RESULT = "/smt/message/getByIdentifierIdAndUuid";
}
