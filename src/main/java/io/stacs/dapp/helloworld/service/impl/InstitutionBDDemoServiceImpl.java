package io.stacs.dapp.helloworld.service.impl;

import com.alibaba.fastjson.JSON;
import io.stacs.dapp.helloworld.constant.StatusEnum;
import io.stacs.dapp.helloworld.dao.SmtBdDao;
import io.stacs.dapp.helloworld.dao.po.SmtBd;
import io.stacs.dapp.helloworld.service.AbstractSendSmtMessageService;
import io.stacs.dapp.helloworld.service.SmtDemoService;
import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.DrsSmtMessage;
import io.stacs.dapp.helloworld.vo.demo.DemoBaseRequest;
import io.stacs.dapp.helloworld.vo.demo.InstitutionBDRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Setup BD for Institution Identity Type
 *
 * @author Su Wenbo
 * @since 2020/9/21
 */
@RequiredArgsConstructor
@Service("smtbd-institution-business-special-1-v1")
public class InstitutionBDDemoServiceImpl extends AbstractSendSmtMessageService implements SmtDemoService {

    private final SmtBdDao smtBdDao;

    @Override
    public DrsResponse doDemo(DemoBaseRequest request) {
        InstitutionBDRequest institutionBDRequest = (InstitutionBDRequest) request;
        //Setup message using SMT format
        DrsSmtMessage message = buildBaseMessage(request);
        message.getHeader().setSmtCode("smtbd-institution-business-special-1-v1");
        //Message Body
        DrsSmtMessage.SmtBody body = JSON.parseObject(JSON.toJSONString(institutionBDRequest.getBody()), DrsSmtMessage.SmtBody.class);
        message.setBody(body);
        //Send Request
        DrsResponse<DrsResponse.SmtResult> drsResponse = doSend(message);
        if (!drsResponse.success()) {
            return drsResponse;
        }
        //Run business logic
        doBusiness(message, drsResponse);
        return drsResponse;
    }

    private void doBusiness(DrsSmtMessage message,
                            DrsResponse<DrsResponse.SmtResult> smtResultDrsResponse) {
        //Save to database
        SmtBd smtBd = new SmtBd();
        smtBd.setSmtCode(message.getHeader().getSmtCode());
        smtBd.setStatus(StatusEnum.ChainStatus.PROCESSING.getCode());
        smtBd.setUuid(message.getHeader().getUuid());
        smtBd.setIdentifierId(message.getHeader().getIdentifierId());
        smtBd.setMessageId(smtResultDrsResponse.getData().getMessageId());
        smtBd.setSessionId(smtResultDrsResponse.getData().getSessionId());
        smtBd.setCreateAt(new Date());
        smtBd.setUpdateAt(new Date());
        smtBdDao.save(smtBd);
    }

}
