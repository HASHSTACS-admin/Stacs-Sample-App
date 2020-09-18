package io.stacs.dapp.helloworld.service.impl;

import com.alibaba.fastjson.JSON;
import io.stacs.dapp.helloworld.service.AbstractSendSmtMessageService;
import io.stacs.dapp.helloworld.service.SmtDemoService;
import io.stacs.dapp.helloworld.utils.UUIDUtil;
import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.DrsSmtMessage;
import io.stacs.dapp.helloworld.vo.demo.DemoBaseRequest;
import io.stacs.dapp.helloworld.vo.demo.DigitalCurrencySmtRequest;
import org.springframework.stereotype.Service;

/**
 * @author Huang Shengli
 * @Description Digital Currency Issuance - SMT Code Example
 * @date 2020-09-13
 */
@Service("smta-dc-dc-issue-1-v1")
public class DigitalCurrencyDemoServiceImpl extends AbstractSendSmtMessageService implements SmtDemoService {


    /**
     * Issuance of Digital Currency
     *
     * @param request
     * @return
     */
    @Override
    public DrsResponse doDemo(DemoBaseRequest request) {
        DigitalCurrencySmtRequest dcRequest = (DigitalCurrencySmtRequest) request;
        //Composing the SMT Message for sending via HTTP API
        //1. Setup of the Request Header
        DrsSmtMessage.SmtHeader header = DrsSmtMessage.SmtHeader.builder().
                identifierId(drsConfig.getMyIdentifierId())
                .messageSenderAddress(dcRequest.getHeader().getMessageSenderAddress())
                .smtCode("smta-dc-dc-issue-1-v1")
                //unique identifier to be created by the developer
                .uuid(UUIDUtil.uuid())
                .build();
        //Setup of the Request Body 
        DrsSmtMessage.SmtBody body = JSON.parseObject(JSON.toJSONString(dcRequest.getBody()), DrsSmtMessage.SmtBody.class);
        //Setup of the Request trailer field
        DrsSmtMessage.SmtTrailer trailer = null;
        if (null != dcRequest.getTrailer()) {
            trailer = DrsSmtMessage.SmtTrailer
                    .builder()
                    .authenticationTrailer(dcRequest.getTrailer().getAuthenticationTrailer())
                    .build();
        }

        DrsSmtMessage message = DrsSmtMessage.builder()
                .header(header)
                .body(body)
                .trailer(trailer)
                .build();

        return doSend(message);
    }
}
