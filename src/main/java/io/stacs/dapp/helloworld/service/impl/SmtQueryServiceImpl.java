package io.stacs.dapp.helloworld.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import io.stacs.dapp.helloworld.config.DrsConfig;
import io.stacs.dapp.helloworld.httpclient.DrsClient;
import io.stacs.dapp.helloworld.service.SmtQueryService;
import io.stacs.dapp.helloworld.utils.UUIDUtil;
import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.DrsSmtMessage;
import io.stacs.dapp.helloworld.vo.demo.BalanceOfRequest;
import io.stacs.dapp.helloworld.vo.demo.QuerySmtResultRequest;
import io.stacs.dapp.helloworld.vo.drs.AddressCreateRequest;
import io.stacs.dapp.helloworld.vo.drs.BalanceQueryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author Huang Shengli
 * @date 2020-09-13
 */

@Service
public class SmtQueryServiceImpl implements SmtQueryService {

    @Autowired
    private DrsConfig drsConfig;

    /**
     * 获取地址
     *
     * @return
     */
    @Override
    public DrsResponse getAddress() {

        AddressCreateRequest createRequest = AddressCreateRequest.builder()
                //生成uuid
                .uuid(UUIDUtil.uuid())
                //设置商户号
                .identifierId(drsConfig.getMyIdentifierId())
                .build();
        JSONObject response = DrsClient.post(drsConfig.getCreateAddressUrl(), createRequest);

        return JSONObject.parseObject(response.toJSONString(), new TypeReference<DrsResponse<String>>() {
        });
    }

    /**
     * 余额查询
     *
     * @param request
     * @return
     */
    @Override
    public DrsResponse balanceOf(BalanceOfRequest request) {
        BalanceQueryRequest drsBalanceQueryRequest = BalanceQueryRequest.builder()
                //查询地址
                .address(request.getAddress())
                //查询的资产ID
                .assetId(request.getAssetId())
                //商户号
                .identifierId(drsConfig.getMyIdentifierId())
                .build();
        JSONObject response = DrsClient.post(drsConfig.getBalanceOfUrl(), drsBalanceQueryRequest);
        return JSONObject.parseObject(response.toJSONString(), new TypeReference<DrsResponse<BigDecimal>>() {
        });
    }

    /**
     * 根据商户号和uuid查询
     *
     * @param request
     * @return
     */
    @Override
    public DrsResponse querySmtResult(QuerySmtResultRequest request) {
        JSONObject response = DrsClient.post(drsConfig.getBalanceOfUrl(), request);
        return JSONObject.parseObject(response.toJSONString(), new TypeReference<DrsResponse<DrsSmtMessage>>() {
        });
    }
}
