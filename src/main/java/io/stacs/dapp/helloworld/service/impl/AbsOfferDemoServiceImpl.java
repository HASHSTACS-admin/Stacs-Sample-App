package io.stacs.dapp.helloworld.service.impl;

import com.alibaba.fastjson.JSON;
import io.stacs.dapp.helloworld.constant.StatusEnum;
import io.stacs.dapp.helloworld.dao.TradeOfferOrderDao;
import io.stacs.dapp.helloworld.dao.po.TradeOfferOrder;
import io.stacs.dapp.helloworld.service.AbstractSendSmtMessageService;
import io.stacs.dapp.helloworld.service.SmtDemoService;
import io.stacs.dapp.helloworld.utils.CommonUtil;
import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.DrsSmtMessage;
import io.stacs.dapp.helloworld.vo.demo.AbsOfferRequest;
import io.stacs.dapp.helloworld.vo.demo.DemoBaseRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author Huang Shengli
 * @Description Issue ABS Offer Order
 * @date 2020-09-21
 */
@Service("smtt-abs-subscription-offer-3-v1")
public class AbsOfferDemoServiceImpl extends AbstractSendSmtMessageService implements SmtDemoService {

    @Autowired
    private TradeOfferOrderDao tradeOfferOrderDao;

    /**
     * Issue ABS Offer Order
     *
     * @param request
     * @return
     */
    @Transactional
    @Override
    public DrsResponse doDemo(DemoBaseRequest request) {
        AbsOfferRequest offerRequest = (AbsOfferRequest) request;
        //Create Request Message
        DrsSmtMessage message = buildBaseMessage(request);
        message.getHeader().setSmtCode("smtt-abs-subscription-offer-3-v1");
        //Message Body
        DrsSmtMessage.SmtBody body = JSON.parseObject(JSON.toJSONString(offerRequest.getBody()), DrsSmtMessage.SmtBody.class);
        //process timestamp parameters
        body.put("orderStartTime", CommonUtil.getSmtDateTime(offerRequest.getBody().getOrderStartTime()));
        body.put("orderEndTime", CommonUtil.getSmtDateTime(offerRequest.getBody().getOrderEndTime()));
        body.put("paymentEndTime", CommonUtil.getSmtDateTime(offerRequest.getBody().getPaymentEndTime()));

        if (null != offerRequest.getBody().getSettleTime()) {
            body.put("settleTime", CommonUtil.getSmtDateTime(offerRequest.getBody().getSettleTime()));
        }

        message.setBody(body);
        //Send API Request
        DrsResponse<DrsResponse.SmtResult> drsResponse = doSend(message);
        if (!drsResponse.success()) {
            return drsResponse;
        }
        //doBusiness
        doBusiness(drsResponse, offerRequest, message.getHeader());
        //Returned response from DRS
        return drsResponse;
    }

    /**
     * Run business logic
     *
     * @param drsResponse
     * @param offerRequest
     */
    private void doBusiness(DrsResponse<DrsResponse.SmtResult> drsResponse, AbsOfferRequest offerRequest, DrsSmtMessage.SmtHeader header) {
        //save
        TradeOfferOrder order = new TradeOfferOrder();
        //save data state
        BeanUtils.copyProperties(offerRequest.getBody(), order);
        //save to database
        order.setCreateAt(new Date());
        order.setUpdateAt(new Date());
        order.setIdentifierId(header.getIdentifierId());
        order.setMessageId(drsResponse.getData().getMessageId());
        order.setSessionId(drsResponse.getData().getSessionId());
        order.setSmtCode("smtt-abs-subscription-offer-3-v1");
        order.setStatus(StatusEnum.ChainStatus.PROCESSING.getCode());
        order.setBizStatus(StatusEnum.OfferBizStatus.OFFER.getCode());
        order.setUuid(header.getUuid());
        order.setOfferAddress(offerRequest.getHeader().getMessageSenderAddress());
        //save
        tradeOfferOrderDao.save(order);
    }
}
