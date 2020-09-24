package io.stacs.dapp.helloworld.service.impl;

import io.stacs.dapp.helloworld.constant.StatusEnum;
import io.stacs.dapp.helloworld.dao.TradeOfferOrderDao;
import io.stacs.dapp.helloworld.dao.po.TradeOfferOrder;
import io.stacs.dapp.helloworld.service.AbstractSendSmtMessageService;
import io.stacs.dapp.helloworld.service.SmtDemoService;
import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.DrsSmtMessage;
import io.stacs.dapp.helloworld.vo.demo.DemoBaseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * 买/卖方发起退款ABS报文服务
 *
 * @author Su Wenbo
 * @since 2020/9/21
 */
@RequiredArgsConstructor
@Service("smtt-abs-subscription-refund-2-v1")
public class AbsRefundDemoServiceImpl extends AbstractSendSmtMessageService implements SmtDemoService {

    @Autowired
    private TradeOfferOrderDao tradeOfferOrderDao;

    /**
     * 买/卖方发起退款ABS
     *
     * @param request permission request
     * @return drs response
     */
    @Override
    public DrsResponse doDemo(DemoBaseRequest request) {
        //查询offer单
        String sessionId = request.getHeader().getSessionId();
        TradeOfferOrder tradeOfferOrder = tradeOfferOrderDao.findBySessionId(sessionId);
        if (Objects.isNull(tradeOfferOrder)) {
            return DrsResponse.fail("error", "Trade offer order doesn't exist!");
        }
        if (tradeOfferOrder.getPaymentEndTime().after(new Date())) {
            return DrsResponse.fail("error", "Only after the due date of payment!");
        }
        if (!StatusEnum.OfferBizStatus.OFFER.getCode().equals(tradeOfferOrder.getBizStatus())) {
            return DrsResponse.fail("error", "The current state does not support refund!");
        }

        //组装报文数据
        DrsSmtMessage message = buildBaseMessage(request);
        message.getHeader().setSmtCode("smtt-abs-subscription-refund-2-v1");
        message.getHeader().setSessionId(request.getHeader().getSessionId());
        //发送请求
        DrsResponse<DrsResponse.SmtResult> drsResponse = doSend(message);
        if (!drsResponse.success()) {
            return drsResponse;
        }
        //做额外逻辑
        doBusiness(tradeOfferOrder);
        return drsResponse;
    }

    private void doBusiness(TradeOfferOrder tradeOfferOrder) {
        //更新状态，存数据库
        tradeOfferOrder.setStatus(StatusEnum.ChainStatus.PROCESSING.getCode());
        tradeOfferOrder.setUpdateAt(new Date());
        tradeOfferOrderDao.save(tradeOfferOrder);
    }

}
