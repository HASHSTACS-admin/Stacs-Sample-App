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
 * @Description 发布ABS的BD
 * @date 2020-09-13
 */
@Service("smtbd-abs-abs-issue-1-v1")
public class AbsBDDemoServiceImpl extends AbstractSendSmtMessageService implements SmtDemoService {

    @Autowired
    private SmtBdDao smtBdDao;

    /**
     * abs的BD发布
     *
     * @param request
     * @return
     */
    @Transactional
    @Override
    public DrsResponse doDemo(DemoBaseRequest request) {
        AbsBDRequest bdRequest = (AbsBDRequest) request;
        //组装报文数据
        DrsSmtMessage message = buildBaseMessage(request);
        message.getHeader().setSmtCode("smtbd-abs-abs-issue-1-v1");
        //报文体
        DrsSmtMessage.SmtBody body = JSON.parseObject(JSON.toJSONString(bdRequest.getBody()), DrsSmtMessage.SmtBody.class);
        message.setBody(body);
        //发起DRS请求
        DrsResponse<DrsResponse.SmtResult> drsResponse = doSend(message);
        if (!drsResponse.success()) {
            return drsResponse;
        }
        //doBusiness
        doBusiness(drsResponse, bdRequest);
        //请求DRS
        return drsResponse;
    }

    /**
     * 业务处理
     *
     * @param drsResponse
     */
    private void doBusiness(DrsResponse<DrsResponse.SmtResult> drsResponse, AbsBDRequest bdRequest) {
        //保存到bd表
        SmtBd absBd = new SmtBd();
        absBd.setCreateAt(new Date());
        absBd.setUpdateAt(new Date());
        absBd.setIdentifierId(bdRequest.getHeader().getIdentifierId());
        absBd.setMessageId(drsResponse.getData().getMessageId());
        absBd.setSessionId(drsResponse.getData().getSessionId());
        absBd.setSmtCode("smtbd-abs-abs-issue-1-v1");
        absBd.setStatus(StatusEnum.ChainStatus.PROCESSING.getCode());
        absBd.setUuid(bdRequest.getHeader().getUuid());
        //save
        smtBdDao.save(absBd);
    }
}
