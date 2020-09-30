package io.stacs.dapp.helloworld.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableMap;
import io.stacs.dapp.helloworld.service.AbstractSendSmtMessageService;
import io.stacs.dapp.helloworld.service.SmtDemoService;
import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.DrsSmtMessage;
import io.stacs.dapp.helloworld.vo.demo.AbsBuybackRequest;
import io.stacs.dapp.helloworld.vo.demo.DemoBaseRequest;
import io.stacs.dapp.helloworld.vo.drs.AbsBuybackSmtBody;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ABS Redemption
 *
 * @author Su Wenbo
 * @since 2020/9/24
 */
@Service("smtt-abs-buyback-buyback-1-v1")
public class AbsBuyBackDemoServiceImpl extends AbstractSendSmtMessageService implements SmtDemoService {

    @Override
    public DrsResponse doDemo(DemoBaseRequest request) {
        AbsBuybackRequest absBuybackRequest = (AbsBuybackRequest) request;

        //Create Request Message
        DrsSmtMessage message = buildBaseMessage(request);
        message.getHeader().setSmtCode("smtt-abs-buyback-buyback-1-v1");

        //Request Body
        AbsBuybackSmtBody absBuybackSmtBody = absBuybackRequest.getBody();
        //convert into an array that looks like[[40!c, 24n.8n, 24n.8n],...,[40!c, 24n.8n, 24n.8n]]
        List<List<? extends Serializable>> targetAddrAndDisburseQtyAndRedeemQty = absBuybackSmtBody.getTargetAddrAndDisburseQtyAndRedeemQty()
                .stream()
                .map(o -> Arrays.asList(o.getTargetAddress(), o.getDisburseQty(), o.getRedeemQty()))
                .collect(Collectors.toList());
        ImmutableMap<String, Object> paramMap = ImmutableMap.of("assetId", absBuybackSmtBody.getAssetId(),
                "targetAddrAndDisburseQtyAndRedeemQty", targetAddrAndDisburseQtyAndRedeemQty);
        DrsSmtMessage.SmtBody body = JSON.parseObject(JSON.toJSONString(paramMap), DrsSmtMessage.SmtBody.class);
        message.setBody(body);

        //Send API Request
        return doSend(message);
    }

}
