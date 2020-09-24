package io.stacs.dapp.helloworld.service.impl;

import com.alibaba.fastjson.JSON;
import io.stacs.dapp.helloworld.service.AbstractSendSmtMessageService;
import io.stacs.dapp.helloworld.service.SmtDemoService;
import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.DrsSmtMessage;
import io.stacs.dapp.helloworld.vo.demo.DemoBaseRequest;
import io.stacs.dapp.helloworld.vo.demo.InstitutionIdentityRequest;
import org.springframework.stereotype.Service;

/**
 * Create Identity for Institution Type
 *
 * @author Su Wenbo
 * @since 2020/9/21
 */
@Service("smti-institution-identity-set-1-v1")
public class InstitutionIdentityDemoServiceImpl extends AbstractSendSmtMessageService implements SmtDemoService {

    /**
     * Create Identity for Institution Type
     *
     * @param request permission request
     * @return drs response
     */
    @Override
    public DrsResponse doDemo(DemoBaseRequest request) {
        InstitutionIdentityRequest institutionIdentityRequest = (InstitutionIdentityRequest) request;
        //Create Request Message
        DrsSmtMessage message = buildBaseMessage(request);
        message.getHeader().setSmtCode("smti-institution-identity-set-1-v1");
        //Message Body
        DrsSmtMessage.SmtBody body = JSON.parseObject(JSON.toJSONString(institutionIdentityRequest.getBody()), DrsSmtMessage.SmtBody.class);
        message.setBody(body);
        //Send API Request
        return doSend(message);
    }

}
