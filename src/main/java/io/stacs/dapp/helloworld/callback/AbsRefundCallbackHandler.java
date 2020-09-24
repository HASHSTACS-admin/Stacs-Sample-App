package io.stacs.dapp.helloworld.callback;

import io.stacs.dapp.helloworld.constant.StatusEnum;
import io.stacs.dapp.helloworld.dao.TradeBidOrderDao;
import io.stacs.dapp.helloworld.dao.TradeOfferOrderDao;
import io.stacs.dapp.helloworld.dao.po.TradeBidOrder;
import io.stacs.dapp.helloworld.dao.po.TradeOfferOrder;
import io.stacs.dapp.helloworld.vo.DrsSmtMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

import static io.stacs.dapp.helloworld.callback.SmtCallbackHandler.SUFFIX_CALLBACK;

/**
 * @author Su Wenbo
 * @since 2020/9/21
 */
@Slf4j
@Component("smtt-abs-subscription-refund-2-v1" + SUFFIX_CALLBACK)
public class AbsRefundCallbackHandler implements SmtCallbackHandler {

    @Autowired
    private TradeOfferOrderDao tradeOfferOrderDao;

    @Autowired
    private TradeBidOrderDao tradeBidOrderDao;

    @Override
    public void handle(DrsSmtMessage message) {
        String sessionId = message.getHeader().getSessionId();

        //update trade bid order
        TradeOfferOrder tradeOfferOrder = tradeOfferOrderDao.findBySessionId(sessionId);
        List<TradeBidOrder> tradeBidOrders = tradeBidOrderDao.findByOfferSessionId(sessionId);
        if (message.success()) {
            tradeBidOrders.stream()
                    .filter(o -> StatusEnum.BidBizStatus.SUBSCRIBED.getCode().equals(o.getBizStatus()))
                    .forEach(o -> {
                        o.setBizStatus(StatusEnum.BidBizStatus.REFUND.getCode());
                        o.setUpdateAt(new Date());
                    });
            tradeOfferOrder.setBizStatus(StatusEnum.OfferBizStatus.REFUND.getCode());
            tradeOfferOrder.setStatus(StatusEnum.ChainStatus.SUCCESS.getCode());
        } else {
            tradeOfferOrder.setStatus(StatusEnum.ChainStatus.FAIL.getCode());
        }
        tradeOfferOrder.setUpdateAt(new Date());
        tradeOfferOrderDao.save(tradeOfferOrder);
        tradeBidOrderDao.saveAll(tradeBidOrders);
    }

}
