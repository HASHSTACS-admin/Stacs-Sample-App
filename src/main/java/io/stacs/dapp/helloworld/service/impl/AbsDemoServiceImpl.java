package io.stacs.dapp.helloworld.service.impl;

import com.alibaba.fastjson.JSON;
import io.stacs.dapp.helloworld.constant.StatusEnum;
import io.stacs.dapp.helloworld.dao.AssetAbsDao;
import io.stacs.dapp.helloworld.dao.po.AssetAbs;
import io.stacs.dapp.helloworld.service.AbstractSendSmtMessageService;
import io.stacs.dapp.helloworld.service.SmtDemoService;
import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.DrsSmtMessage;
import io.stacs.dapp.helloworld.vo.demo.AbsCreateRequest;
import io.stacs.dapp.helloworld.vo.demo.DemoBaseRequest;
import io.stacs.dapp.helloworld.vo.drs.AbsSmtBody;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Huang Shengli
 * @Description Issue Asset Backed Securities (ABS)
 * @date 2020-09-21
 */
@Service("smta-abs-corporation-issue-1-v1")
public class AbsDemoServiceImpl extends AbstractSendSmtMessageService implements SmtDemoService {

    @Autowired
    private AssetAbsDao assetAbsDao;

    /**
     * issue BD for ABS asset
     *
     * @param request
     * @return
     */
    @Transactional
    @Override
    public DrsResponse doDemo(DemoBaseRequest request) {
        AbsCreateRequest absRequest = (AbsCreateRequest) request;
        //Setup message using SMT format
        DrsSmtMessage message = buildBaseMessage(request);
        message.getHeader().setSmtCode("smta-abs-corporation-issue-1-v1");
        //Message Body
        DrsSmtMessage.SmtBody body = JSON.parseObject(JSON.toJSONString(absRequest.getBody()), DrsSmtMessage.SmtBody.class);
        //Requires 2 unique timestamps
        if (!CollectionUtils.isEmpty(absRequest.getBody().getCallDate())) {
            List<Long> calldate = absRequest.getBody().getCallDate().stream().map(x -> x.getTime() / 1000).collect(Collectors.toList());
            body.put("callDate", calldate);
        }
        if (null != absRequest.getBody().getFirstSettlementDate()) {
            body.put("firstSettlementDate", absRequest.getBody().getFirstSettlementDate().getTime() / 1000);
        }
        message.setBody(body);
        //Send request to DRS
        DrsResponse<DrsResponse.SmtResult> drsResponse = doSend(message);
        if (!drsResponse.success()) {
            return drsResponse;
        }
        //doBusiness
        doBusiness(drsResponse, absRequest, message.getHeader());
        //returned response from DRS
        return drsResponse;
    }

    /**
     * Run Business logic
     *
     * @param drsResponse
     */
    private void doBusiness(DrsResponse<DrsResponse.SmtResult> drsResponse, AbsCreateRequest absRequest, DrsSmtMessage.SmtHeader header) {
        //save to database
        AssetAbs abs = new AssetAbs();
        BeanUtils.copyProperties(absRequest.getBody(), abs);
        abs.setCreateAt(new Date());
        abs.setUpdateAt(new Date());
        abs.setIdentifierId(header.getIdentifierId());
        abs.setMessageId(drsResponse.getData().getMessageId());
        abs.setSessionId(drsResponse.getData().getSessionId());
        abs.setSmtCode("smta-abs-corporation-issue-1-v1");
        abs.setStatus(StatusEnum.ChainStatus.PROCESSING.getCode());
        abs.setUuid(header.getUuid());
        //entity data state
        AbsSmtBody absBody = absRequest.getBody();

        abs.setBizStatus(StatusEnum.BizStatus.NORMAL.getCode());
        abs.setStatus(StatusEnum.ChainStatus.PROCESSING.getCode());
        if (!CollectionUtils.isEmpty(absBody.getCallDate())) {
            List<Long> calldate = absBody.getCallDate().stream().map(x -> x.getTime() / 1000).collect(Collectors.toList());
            abs.setCallDate(JSON.toJSONString(calldate));
        }
        if (!CollectionUtils.isEmpty(absBody.getIndividualPermitted())) {
            abs.setIndividualPermitted(JSON.toJSONString(absBody.getIndividualPermitted()));
        }
        if (!CollectionUtils.isEmpty(absBody.getIndividualProhibited())) {
            abs.setIndividualPermitted(JSON.toJSONString(absBody.getIndividualProhibited()));
        }
        if (!CollectionUtils.isEmpty(absBody.getInstitutionalPermitted())) {
            abs.setIndividualPermitted(JSON.toJSONString(absBody.getInstitutionalPermitted()));
        }
        if (!CollectionUtils.isEmpty(absBody.getInstitutionalProhibited())) {
            abs.setIndividualPermitted(JSON.toJSONString(absBody.getInstitutionalProhibited()));
        }

        //save
        assetAbsDao.save(abs);
    }
}
