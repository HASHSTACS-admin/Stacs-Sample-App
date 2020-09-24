package io.stacs.dapp.helloworld.callback;

import io.stacs.dapp.helloworld.constant.StatusEnum;
import io.stacs.dapp.helloworld.dao.TradeOfferOrderDao;
import io.stacs.dapp.helloworld.dao.po.TradeOfferOrder;
import io.stacs.dapp.helloworld.vo.DrsSmtMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

import static io.stacs.dapp.helloworld.callback.SmtCallbackHandler.SUFFIX_CALLBACK;

/**
 * Abs order offer handler
 *
 * @author Su Wenbo
 * @since 2020/9/21
 */
@Component("smtt-abs-subscription-offer-3-v1" + SUFFIX_CALLBACK)
public class AbsOfferCallbackHandler implements SmtCallbackHandler {

    @Autowired
    private TradeOfferOrderDao tradeOfferOrderDao;


    @Override
    public void handle(DrsSmtMessage message) {
        String uuid = message.getHeader().getUuid();
        DrsSmtMessage.SmtBody body = message.getBody();
        String contractAddress = body.getString("contractAddress");

        //update offer
        TradeOfferOrder order = tradeOfferOrderDao.findByUuid(uuid);
        order.setContractAddress(contractAddress);
        if (message.success()) {
            order.setStatus(StatusEnum.ChainStatus.SUCCESS.getCode());
        } else {
            order.setStatus(StatusEnum.ChainStatus.FAIL.getCode());
        }
        order.setUpdateAt(new Date());
        tradeOfferOrderDao.save(order);
    }

}
