package io.stacs.dapp.helloworld.service.impl;

import io.stacs.dapp.helloworld.constant.StatusEnum;
import io.stacs.dapp.helloworld.dao.TradeOfferOrderDao;
import io.stacs.dapp.helloworld.dao.po.TradeOfferOrder;
import io.stacs.dapp.helloworld.service.AbstractSendSmtMessageService;
import io.stacs.dapp.helloworld.service.SmtDemoService;
import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.DrsSmtMessage;
import io.stacs.dapp.helloworld.vo.demo.DemoBaseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * Settlement of ABS Subscription
 *
 * @author Su Wenbo
 * @since 2020/9/21
 */
@Service("smtt-abs-subscription-settle-2-v1")
public class AbsSettleDemoServiceImpl extends AbstractSendSmtMessageService implements SmtDemoService {

    @Autowired
    private TradeOfferOrderDao tradeOfferOrderDao;

    /**
     * Settlement of ABS Subscription
     *
     * @param request permission request
     * @return drs response
     */
    @Override
    public DrsResponse doDemo(DemoBaseRequest request) {
        //Create Request Message
        DrsSmtMessage message = buildBaseMessage(request);
        message.getHeader().setSmtCode("smtt-abs-subscription-settle-2-v1");
        message.getHeader().setSessionId(request.getHeader().getSessionId());
        TradeOfferOrder order = tradeOfferOrderDao.findBySessionId(request.getHeader().getSessionId());
        if (Objects.isNull(order)) {
            return DrsResponse.fail("error", "offer order is not exists");
        }
        //Check if delayed settlement is supported
        Date settleTime = order.getSettleTime();
        if (Objects.isNull(settleTime)) {
            return DrsResponse.fail("error", "not allowed this operation for this order");
        }
        if (settleTime.before(order.getPaymentEndTime())) {
            return DrsResponse.fail("error", "it's not time to settle up yet");
        }
        if (StatusEnum.OfferBizStatus.REFUND != StatusEnum.OfferBizStatus.getByCode(order.getBizStatus()) ||
                StatusEnum.ChainStatus.SUCCESS != StatusEnum.ChainStatus.getByCode(order.getStatus())) {
            if (StatusEnum.OfferBizStatus.SETTLEMENT == StatusEnum.OfferBizStatus.getByCode(order.getBizStatus()) &&
                    StatusEnum.ChainStatus.FAIL == StatusEnum.ChainStatus.getByCode(order.getStatus()))
                return DrsResponse.fail("error", "not allowed this operation for current status");
        }

        //Message Body
        message.setBody(new DrsSmtMessage.SmtBody());
        //Send API Request
        DrsResponse<DrsResponse.SmtResult> drsResponse = doSend(message);
        if (!drsResponse.success()) {
            return drsResponse;
        }
        //do business
        doBusiness(order);
        return drsResponse;
    }

    private void doBusiness(TradeOfferOrder order) {
        order.setStatus(StatusEnum.ChainStatus.PROCESSING.getCode());
        order.setBizStatus(StatusEnum.OfferBizStatus.OFFER.getCode());
        order.setUpdateAt(new Date());
        tradeOfferOrderDao.save(order);
    }

}
