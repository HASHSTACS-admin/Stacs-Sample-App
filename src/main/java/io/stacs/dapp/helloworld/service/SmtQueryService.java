package io.stacs.dapp.helloworld.service;

import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.demo.BalanceOfRequest;
import io.stacs.dapp.helloworld.vo.demo.QuerySmtResultRequest;

/**
 * @author HuangShengli
 * @ClassName SmtDemoService
 * @Description DRS API Query Service
 * @since 2020/9/12
 */
public interface SmtQueryService {

    /**
     * Get Address
     *
     * @param
     * @return
     */
    DrsResponse getAddress();

    /**
     * Get Balance of address
     *
     * @param request
     * @return
     */
    DrsResponse balanceOf(BalanceOfRequest request);

    /**
     * Get Transaction status using Merchant Id and Uuid
     *
     * @param request
     * @return
     */
    DrsResponse querySmtResult(QuerySmtResultRequest request);
}
