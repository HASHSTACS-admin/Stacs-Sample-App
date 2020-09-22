package io.stacs.dapp.helloworld.service.impl;

import com.alibaba.fastjson.JSON;
import io.stacs.dapp.helloworld.service.AbstractSendSmtMessageService;
import io.stacs.dapp.helloworld.service.SmtDemoService;
import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.DrsSmtMessage;
import io.stacs.dapp.helloworld.vo.demo.DemoBaseRequest;
import io.stacs.dapp.helloworld.vo.demo.IdentityRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Identity Setup using SMT format
 *
 * @author Su Wenbo
 * @since 2020/9/21
 */
@RequiredArgsConstructor
@Service("smti-individual-identity-set-1-v1")
public class IdentityDemoServiceImpl extends AbstractSendSmtMessageService implements SmtDemoService {

    /**
     * Setup Identity to send to the chain
     *
     * @param request permission request
     * @return drs response
     */
    @Override
    public DrsResponse doDemo(DemoBaseRequest request) {
        IdentityRequest identityRequest = (IdentityRequest) request;
        //Setup message using SMT format
        DrsSmtMessage message = buildBaseMessage(request);
        message.getHeader().setSmtCode(identityRequest.getIdentityType().getSmtCode());
        //Message Body
        DrsSmtMessage.SmtBody body = JSON.parseObject(JSON.toJSONString(identityRequest.getBody()), DrsSmtMessage.SmtBody.class);
        message.setBody(body);
        //Send request
        return doSend(message);
    }

}
