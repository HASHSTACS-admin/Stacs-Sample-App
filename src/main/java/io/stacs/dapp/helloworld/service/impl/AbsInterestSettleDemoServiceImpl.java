package io.stacs.dapp.helloworld.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableMap;
import io.stacs.dapp.helloworld.service.AbstractSendSmtMessageService;
import io.stacs.dapp.helloworld.service.SmtDemoService;
import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.DrsSmtMessage;
import io.stacs.dapp.helloworld.vo.demo.AbsInterestSettleRequest;
import io.stacs.dapp.helloworld.vo.demo.DemoBaseRequest;
import io.stacs.dapp.helloworld.vo.drs.AbsInterestSettleSmtBody;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ABS结息服务
 *
 * @author Su Wenbo
 * @since 2020/9/24
 */
@Service("smtt-abs-interestSettle-pay-1-v1")
public class AbsInterestSettleDemoServiceImpl extends AbstractSendSmtMessageService implements SmtDemoService {

    @Override
    public DrsResponse doDemo(DemoBaseRequest request) {
        AbsInterestSettleRequest absInterestSettleRequest = (AbsInterestSettleRequest) request;

        //组装报文数据
        DrsSmtMessage message = buildBaseMessage(request);
        message.getHeader().setSmtCode("smtt-abs-interestSettle-pay-1-v1");

        //报文体
        AbsInterestSettleSmtBody absInterestSettleSmtBody = absInterestSettleRequest.getBody();
        //将前端参数转换为报文所需要的二维数组[[40!c, 24n.8n],...,[40!c, 24n.8n]]
        List<List<? extends Serializable>> targetAddrAndQty = absInterestSettleSmtBody.getTargetAddrAndQty()
                .stream()
                .map(o -> Arrays.asList(o.getTargetAddress(), o.getQty()))
                .collect(Collectors.toList());
        ImmutableMap<String, Object> paramMap = ImmutableMap.of("assetId", absInterestSettleSmtBody.getAssetId(),
                "targetAddrAndQty", targetAddrAndQty);
        DrsSmtMessage.SmtBody body = JSON.parseObject(JSON.toJSONString(paramMap), DrsSmtMessage.SmtBody.class);
        message.setBody(body);

        //发送请求
        return doSend(message);
    }

}
