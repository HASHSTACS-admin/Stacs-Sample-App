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
import java.util.Objects;

import static io.stacs.dapp.helloworld.callback.SmtCallbackHandler.SUFFIX_CALLBACK;

/**
 * @author Su Wenbo
 * @since 2020/9/21
 */
@Slf4j
@Component("smtt-abs-subscription-confirm-1-v1" + SUFFIX_CALLBACK)
public class AbsConfirmCallbackHandler implements SmtCallbackHandler {

    @Autowired
    private TradeBidOrderDao tradeBidOrderDao;

    @Autowired
    private TradeOfferOrderDao tradeOfferOrderDao;

    @Override
    public void handle(DrsSmtMessage message) {
        DrsSmtMessage.SmtBody body = message.getBody();
        String messageId = body.getString("messageId");

        //更新表trade bid order
        TradeBidOrder tradeBidOrder = tradeBidOrderDao.findByMessageId(messageId);
        if (Objects.isNull(tradeBidOrder)) {
            log.warn("Cannot find trade bid order by message id.");
            return;
        }
        if (message.success()) {
            tradeBidOrder.setStatus(StatusEnum.ChainStatus.SUCCESS.getCode());
            tradeBidOrder.setBizStatus(StatusEnum.BidBizStatus.CONFIRMED.getCode());
            //查询trade offer order， 是否延迟结算
            String sessionId = message.getHeader().getSessionId();
            TradeOfferOrder tradeOfferOrder = tradeOfferOrderDao.findBySessionId(sessionId);
            if (tradeOfferOrder != null &&
                    (tradeOfferOrder.getSettleTime() == null || tradeOfferOrder.getSettleTime().before(new Date()))) {
                tradeBidOrder.setBizStatus(StatusEnum.BidBizStatus.SETTLED.getCode());
            }
        } else {
            tradeBidOrder.setStatus(StatusEnum.ChainStatus.FAIL.getCode());
        }
        tradeBidOrder.setUpdateAt(new Date());
        tradeBidOrderDao.save(tradeBidOrder);
    }

}
