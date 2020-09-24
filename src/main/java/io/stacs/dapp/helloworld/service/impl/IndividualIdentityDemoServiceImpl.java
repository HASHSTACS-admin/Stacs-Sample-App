package io.stacs.dapp.helloworld.service.impl;

import com.alibaba.fastjson.JSON;
import io.stacs.dapp.helloworld.service.AbstractSendSmtMessageService;
import io.stacs.dapp.helloworld.service.SmtDemoService;
import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.DrsSmtMessage;
import io.stacs.dapp.helloworld.vo.demo.DemoBaseRequest;
import io.stacs.dapp.helloworld.vo.demo.IndividualIdentityRequest;
import org.springframework.stereotype.Service;

/**
 * Individual Type Identity Service
 *
 * @author Su Wenbo
 * @since 2020/9/21
 */
@Service("smti-individual-identity-set-1-v1")
public class IndividualIdentityDemoServiceImpl extends AbstractSendSmtMessageService implements SmtDemoService {

    /**
     * Setup permission for address
     *
     * @param request permission request
     * @return drs response
     */
    @Override
    public DrsResponse doDemo(DemoBaseRequest request) {
        IndividualIdentityRequest individualIdentityRequest = (IndividualIdentityRequest) request;
        //Setup message using SMT format
        DrsSmtMessage message = buildBaseMessage(request);
        message.getHeader().setSmtCode("smti-individual-identity-set-1-v1");
        //Message Body
        DrsSmtMessage.SmtBody body = JSON.parseObject(JSON.toJSONString(individualIdentityRequest.getBody()), DrsSmtMessage.SmtBody.class);
        message.setBody(body);
        //Send request
        return doSend(message);
    }

}
