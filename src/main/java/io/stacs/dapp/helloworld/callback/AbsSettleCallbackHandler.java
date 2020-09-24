package io.stacs.dapp.helloworld.callback;

import io.stacs.dapp.helloworld.constant.StatusEnum;
import io.stacs.dapp.helloworld.dao.TradeBidOrderDao;
import io.stacs.dapp.helloworld.dao.TradeOfferOrderDao;
import io.stacs.dapp.helloworld.dao.po.TradeBidOrder;
import io.stacs.dapp.helloworld.dao.po.TradeOfferOrder;
import io.stacs.dapp.helloworld.vo.DrsSmtMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

import static io.stacs.dapp.helloworld.callback.SmtCallbackHandler.SUFFIX_CALLBACK;

/**
 * Abs order offer handler
 *
 * @author Su Wenbo
 * @since 2020/9/21
 */
@Component("smtt-abs-subscription-settle-2-v1" + SUFFIX_CALLBACK)
public class AbsSettleCallbackHandler implements SmtCallbackHandler {

    @Autowired
    private TradeOfferOrderDao tradeOfferOrderDao;
    @Autowired
    private TradeBidOrderDao tradeBidOrderDao;


    @Override
    public void handle(DrsSmtMessage message) {
        String sessionId = message.getHeader().getSessionId();
        DrsSmtMessage.SmtBody body = message.getBody();

        //update offer
        TradeOfferOrder order = tradeOfferOrderDao.findBySessionId(sessionId);
        if (message.success()) {
            order.setStatus(StatusEnum.ChainStatus.SUCCESS.getCode());
        } else {
            order.setStatus(StatusEnum.ChainStatus.FAIL.getCode());
        }
        order.setUpdateAt(new Date());
        tradeOfferOrderDao.save(order);
        if (!message.success()) {
            return;
        }
        List<TradeBidOrder> bidOrders = tradeBidOrderDao.findByOfferSessionId(sessionId);
        bidOrders.stream().filter(bid -> StatusEnum.BidBizStatus.CONFIRMED == StatusEnum.BidBizStatus.getByCode(bid.getBizStatus()))
                .forEach(bid -> {
                    bid.setBizStatus(StatusEnum.BidBizStatus.SETTLED.getCode());
                    bid.setStatus(StatusEnum.ChainStatus.SUCCESS.getCode());
                    bid.setUpdateAt(new Date());
                    tradeBidOrderDao.save(bid);
                });

    }

}
