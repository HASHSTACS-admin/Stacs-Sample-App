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
     * Get Address
     *
     * @return
     */
    @Override
    public DrsResponse getAddress() {

        AddressCreateRequest createRequest = AddressCreateRequest.builder()
                //Create unique uuid
                .uuid(UUIDUtil.uuid())
                //Set merchant id
                .identifierId(drsConfig.getMyIdentifierId())
                .build();
        JSONObject response = DrsClient.post(drsConfig.getCreateAddressUrl(), createRequest);

        return JSONObject.parseObject(response.toJSONString(), new TypeReference<DrsResponse<String>>() {
        });
    }

    /**
     * Query balance of an address
     *
     * @param request
     * @return
     */
    @Override
    public DrsResponse balanceOf(BalanceOfRequest request) {
        BalanceQueryRequest drsBalanceQueryRequest = BalanceQueryRequest.builder()
                //Set address
                .address(request.getAddress())
                //set digital asset id
                .assetId(request.getAssetId())
                //set merchant id
                .identifierId(drsConfig.getMyIdentifierId())
                .build();
        JSONObject response = DrsClient.post(drsConfig.getBalanceOfUrl(), drsBalanceQueryRequest);
        return JSONObject.parseObject(response.toJSONString(), new TypeReference<DrsResponse<BigDecimal>>() {
        });
    }

    /**
     * Query Transaction status using Merchant Id and Uuid
     *
     * @param request
     * @return
     */
    @Override
    public DrsResponse querySmtResult(QuerySmtResultRequest request) {
        JSONObject response = DrsClient.post(drsConfig.getQuerySmtByUuidUrl(), request);
        return JSONObject.parseObject(response.toJSONString(), new TypeReference<DrsResponse<DrsSmtMessage>>() {
        });
    }
}
