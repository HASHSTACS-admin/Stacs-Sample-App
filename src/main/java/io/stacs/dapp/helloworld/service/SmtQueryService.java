package io.stacs.dapp.helloworld.service;

import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.demo.AssetHoldersRequest;
import io.stacs.dapp.helloworld.vo.demo.BalanceOfRequest;
import io.stacs.dapp.helloworld.vo.demo.QuerySmtResultRequest;

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

    /**
     * 根据商户号和uuid查询报文结果
     *
     * @param request
     * @return
     */
    DrsResponse querySmtResult(QuerySmtResultRequest request);

    /**
     * 查询资产的所有地址和余额
     *
     * @param request the request
     * @return drs response
     */
    DrsResponse assetHolders(AssetHoldersRequest request);

}
