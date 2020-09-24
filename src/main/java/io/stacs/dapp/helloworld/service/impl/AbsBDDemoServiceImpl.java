package io.stacs.dapp.helloworld.service.impl;

import com.alibaba.fastjson.JSON;
import io.stacs.dapp.helloworld.constant.StatusEnum;
import io.stacs.dapp.helloworld.dao.SmtBdDao;
import io.stacs.dapp.helloworld.dao.po.SmtBd;
import io.stacs.dapp.helloworld.service.AbstractSendSmtMessageService;
import io.stacs.dapp.helloworld.service.SmtDemoService;
import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.DrsSmtMessage;
import io.stacs.dapp.helloworld.vo.demo.AbsBDRequest;
import io.stacs.dapp.helloworld.vo.demo.DemoBaseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author Huang Shengli
 * @Description Issue BD for an ABS asset
 * @date 2020-09-13
 */
@Service("smtbd-abs-abs-issue-1-v1")
public class AbsBDDemoServiceImpl extends AbstractSendSmtMessageService implements SmtDemoService {

    @Autowired
    private SmtBdDao smtBdDao;

    /**
     * BD Request for an ABS
     *
     * @param request
     * @return
     */
    @Transactional
    @Override
    public DrsResponse doDemo(DemoBaseRequest request) {
        AbsBDRequest bdRequest = (AbsBDRequest) request;
        //Setup Message using SMT format
        DrsSmtMessage message = buildBaseMessage(request);
        bdRequest.getHeader().setUuid(message.getHeader().getUuid());
        message.getHeader().setSmtCode("smtbd-abs-abs-issue-1-v1");
        //Message Body
        DrsSmtMessage.SmtBody body = JSON.parseObject(JSON.toJSONString(bdRequest.getBody()), DrsSmtMessage.SmtBody.class);
        message.setBody(body);
        //Send request to DRS
        DrsResponse<DrsResponse.SmtResult> drsResponse = doSend(message);
        if (!drsResponse.success()) {
            return drsResponse;
        }
        //doBusiness
        doBusiness(drsResponse, bdRequest, message.getHeader());
        //returned response from DRS
        return drsResponse;
    }

    /**
     * Run business logic
     *
     * @param drsResponse
     */
    private void doBusiness(DrsResponse<DrsResponse.SmtResult> drsResponse, AbsBDRequest bdRequest, DrsSmtMessage.SmtHeader header) {
        //Save to database
        SmtBd absBd = new SmtBd();
        absBd.setCreateAt(new Date());
        absBd.setUpdateAt(new Date());
        absBd.setIdentifierId(header.getIdentifierId());
        absBd.setMessageId(drsResponse.getData().getMessageId());
        absBd.setSessionId(drsResponse.getData().getSessionId());
        absBd.setSmtCode("smtbd-abs-abs-issue-1-v1");
        absBd.setStatus(StatusEnum.ChainStatus.PROCESSING.getCode());
        absBd.setUuid(header.getUuid());
        //save
        smtBdDao.save(absBd);
    }
}
