package io.stacs.dapp.helloworld.callback;

import io.stacs.dapp.helloworld.constant.StatusEnum;
import io.stacs.dapp.helloworld.dao.TradeBidOrderDao;
import io.stacs.dapp.helloworld.dao.po.TradeBidOrder;
import io.stacs.dapp.helloworld.vo.DrsSmtMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequiredArgsConstructor
public class AbsConfirmCallbackHandler implements SmtCallbackHandler {

    private final TradeBidOrderDao tradeBidOrderDao;

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
            tradeBidOrder.setBizStatus(StatusEnum.BidBizStatus.CONFIRMED.getCode());
        }
        tradeBidOrder.setUpdateAt(new Date());
        tradeBidOrderDao.save(tradeBidOrder);
    }

}
