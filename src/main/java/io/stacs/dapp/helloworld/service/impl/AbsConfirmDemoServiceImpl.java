package io.stacs.dapp.helloworld.service.impl;

import com.alibaba.fastjson.JSON;
import io.stacs.dapp.helloworld.service.AbstractSendSmtMessageService;
import io.stacs.dapp.helloworld.service.SmtDemoService;
import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.DrsSmtMessage;
import io.stacs.dapp.helloworld.vo.demo.AbsBidRequest;
import io.stacs.dapp.helloworld.vo.demo.DemoBaseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 卖方确认收款ABS报文服务
 *
 * @author Su Wenbo
 * @since 2020/9/21
 */
@RequiredArgsConstructor
@Service("smtt-abs-subscription-confirm-1-v1")
public class AbsConfirmDemoServiceImpl extends AbstractSendSmtMessageService implements SmtDemoService {

    /**
     * 卖方确认收款ABS
     *
     * @param request permission request
     * @return drs response
     */
    @Override
    public DrsResponse doDemo(DemoBaseRequest request) {
        AbsBidRequest absBidRequest = (AbsBidRequest) request;
        //组装报文数据
        DrsSmtMessage message = buildBaseMessage(request);
        message.getHeader().setSmtCode("smtt-abs-subscription-confirm-1-v1");
        message.getHeader().setSessionId(request.getHeader().getSessionId());
        //报文体
        DrsSmtMessage.SmtBody body = JSON.parseObject(JSON.toJSONString(absBidRequest.getBody()), DrsSmtMessage.SmtBody.class);
        message.setBody(body);
        //发送请求
        return doSend(message);
    }

}
