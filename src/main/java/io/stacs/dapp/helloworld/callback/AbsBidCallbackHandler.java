package io.stacs.dapp.helloworld.callback;

import io.stacs.dapp.helloworld.constant.StatusEnum;
import io.stacs.dapp.helloworld.dao.TradeBidOrderDao;
import io.stacs.dapp.helloworld.dao.po.TradeBidOrder;
import io.stacs.dapp.helloworld.vo.DrsSmtMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

import static io.stacs.dapp.helloworld.callback.SmtCallbackHandler.SUFFIX_CALLBACK;

/**
 * @author Su Wenbo
 * @since 2020/9/21
 */
@Component("smtt-abs-subscription-bid-2-v1" + SUFFIX_CALLBACK)
public class AbsBidCallbackHandler implements SmtCallbackHandler {

    @Autowired
    private TradeBidOrderDao tradeBidOrderDao;

    @Override
    public void handle(DrsSmtMessage message) {
        String uuid = message.getHeader().getUuid();

        //更新表trade bid order
        TradeBidOrder tradeBidOrder = tradeBidOrderDao.findByUuid(uuid);
        if (message.success()) {
            tradeBidOrder.setStatus(StatusEnum.ChainStatus.SUCCESS.getCode());
        } else {
            tradeBidOrder.setStatus(StatusEnum.ChainStatus.FAIL.getCode());
        }
        tradeBidOrder.setUpdateAt(new Date());
        tradeBidOrderDao.save(tradeBidOrder);
    }

}
