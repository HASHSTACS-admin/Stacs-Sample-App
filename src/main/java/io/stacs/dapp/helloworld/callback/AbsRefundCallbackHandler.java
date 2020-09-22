package io.stacs.dapp.helloworld.callback;

import io.stacs.dapp.helloworld.dao.TradeBidOrderDao;
import io.stacs.dapp.helloworld.vo.DrsSmtMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static io.stacs.dapp.helloworld.callback.SmtCallbackHandler.SUFFIX_CALLBACK;

/**
 * @author Su Wenbo
 * @since 2020/9/21
 */
@Slf4j
@Component("smtt-abs-subscription-refund-2-v1" + SUFFIX_CALLBACK)
@RequiredArgsConstructor
public class AbsRefundCallbackHandler implements SmtCallbackHandler {

    private final TradeBidOrderDao tradeBidOrderDao;

    @Override
    public void handle(DrsSmtMessage message) {
    }

}
