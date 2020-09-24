package io.stacs.dapp.helloworld.service.impl;

import com.alibaba.fastjson.JSON;
import io.stacs.dapp.helloworld.constant.StatusEnum;
import io.stacs.dapp.helloworld.dao.TradeBidOrderDao;
import io.stacs.dapp.helloworld.dao.TradeOfferOrderDao;
import io.stacs.dapp.helloworld.dao.po.TradeBidOrder;
import io.stacs.dapp.helloworld.dao.po.TradeOfferOrder;
import io.stacs.dapp.helloworld.service.AbstractSendSmtMessageService;
import io.stacs.dapp.helloworld.service.SmtDemoService;
import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.DrsSmtMessage;
import io.stacs.dapp.helloworld.vo.demo.AbsDisputeRequest;
import io.stacs.dapp.helloworld.vo.demo.DemoBaseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * Raising Dispute for ABS
 *
 * @author Su Wenbo
 * @since 2020/9/21
 */
@Service("smtt-abs-subscription-dispute-1-v1")
public class AbsDisputeDemoServiceImpl extends AbstractSendSmtMessageService implements SmtDemoService {

    @Autowired
    private TradeBidOrderDao tradeBidOrderDao;

    @Autowired
    private TradeOfferOrderDao tradeOfferOrderDao;

    /**
     * Raising Dispute for ABS
     *
     * @param request permission request
     * @return drs response
     */
    @Override
    public DrsResponse doDemo(DemoBaseRequest request) {
        AbsDisputeRequest absDisputeRequest = (AbsDisputeRequest) request;

        //Query Bid order
        String messageId = absDisputeRequest.getBody().getMessageId();
        TradeBidOrder tradeBidOrder = tradeBidOrderDao.findByMessageId(messageId);
        //Query offer order
        String sessionId = request.getHeader().getSessionId();
        TradeOfferOrder tradeOfferOrder = tradeOfferOrderDao.findBySessionId(sessionId);
        //verification
        if (Objects.isNull(tradeBidOrder)) {
            return DrsResponse.fail("error", "The order to be canceled does not exist!");
        }
        if (tradeOfferOrder.getPaymentEndTime().before(new Date())) {
            return DrsResponse.fail("error", "current time is greater than paymentDeadline!");
        }
        if (!StatusEnum.BidBizStatus.SUBSCRIBED.getCode().equals(tradeBidOrder.getBizStatus())) {
            return DrsResponse.fail("error", "status is illegal!");
        }

        //Create Request Message
        DrsSmtMessage message = buildBaseMessage(request);
        message.getHeader().setSmtCode("smtt-abs-subscription-dispute-1-v1");
        message.getHeader().setSessionId(request.getHeader().getSessionId());
        //Message Body
        DrsSmtMessage.SmtBody body = JSON.parseObject(JSON.toJSONString(absDisputeRequest.getBody()), DrsSmtMessage.SmtBody.class);
        message.setBody(body);
        //Send API request
        DrsResponse<DrsResponse.SmtResult> drsResponse = doSend(message);
        if (!drsResponse.success()) {
            return drsResponse;
        }
        //Run business logic
        doBusiness(tradeBidOrder);
        return drsResponse;
    }

    private void doBusiness(TradeBidOrder tradeBidOrder) {
        //Save to database
        tradeBidOrder.setStatus(StatusEnum.ChainStatus.PROCESSING.getCode());
        tradeBidOrder.setUpdateAt(new Date());
        tradeBidOrderDao.save(tradeBidOrder);
    }

}
