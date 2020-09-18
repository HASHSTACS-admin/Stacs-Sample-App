package io.stacs.dapp.helloworld.service.impl;

import com.alibaba.fastjson.JSON;
import io.stacs.dapp.helloworld.service.AbstractSendSmtMessageService;
import io.stacs.dapp.helloworld.service.SmtDemoService;
import io.stacs.dapp.helloworld.utils.UUIDUtil;
import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.DrsSmtMessage;
import io.stacs.dapp.helloworld.vo.demo.DemoBaseRequest;
import io.stacs.dapp.helloworld.vo.demo.TransferSmtRequest;
import org.springframework.stereotype.Service;

/**
 * @author Huang Shengli
 * @Description Transfer of Digital Assets
 * @date 2020-09-13
 */
@Service("transfer")
public class TransferDemoServiceImpl extends AbstractSendSmtMessageService implements SmtDemoService {


    /**
     * Transfer Digital Asset
     *
     * @param request
     * @return
     */
    @Override
    public DrsResponse doDemo(DemoBaseRequest request) {
        TransferSmtRequest transferRequest = (TransferSmtRequest) request;

        //1. Response header
        DrsSmtMessage.SmtHeader header = DrsSmtMessage.SmtHeader.builder().
                identifierId(drsConfig.getMyIdentifierId())
                .messageSenderAddress(transferRequest.getHeader().getMessageSenderAddress())
                .smtCode(transferRequest.getHeader().getSmtCode())
                //unique uuid creation
                .uuid(UUIDUtil.uuid())
                .build();
        //Response body
        DrsSmtMessage.SmtBody body = JSON.parseObject(JSON.toJSONString(transferRequest.getBody()), DrsSmtMessage.SmtBody.class);
        //Response trailer
        DrsSmtMessage.SmtTrailer trailer = null;
        if (null != transferRequest.getTrailer()) {
            trailer = DrsSmtMessage.SmtTrailer
                    .builder()
                    .authenticationTrailer(transferRequest.getTrailer().getAuthenticationTrailer())
                    .build();
        }
        //Assemble the header, body, trailer into the message to be sent as a HTTP API request
        DrsSmtMessage message = DrsSmtMessage.builder()
                .header(header)
                .body(body)
                .trailer(trailer)
                .build();

        return doSend(message);
    }
}
