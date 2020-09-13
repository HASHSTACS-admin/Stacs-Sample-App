package io.stacs.dapp.helloworld.service;

import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.demo.BalanceOfRequest;

/**
 * @author HuangShengli
 * @ClassName SmtDemoService
 * @Description DRS 查询API
 * @since 2020/9/12
 */
public interface SmtQueryService {

    /**
     * 获取地址
     *
     * @param
     * @return
     */
    DrsResponse getAddress();

    /**
     * 余额查询
     *
     * @param request
     * @return
     */
    DrsResponse balanceOf(BalanceOfRequest request);
}
