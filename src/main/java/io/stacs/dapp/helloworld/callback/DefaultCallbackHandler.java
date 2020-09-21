package io.stacs.dapp.helloworld.callback;

import io.stacs.dapp.helloworld.vo.DrsSmtMessage;
import org.springframework.stereotype.Component;

/**
 * @author Su Wenbo
 * @since 2020/9/21
 */
@Component("defaultCallbackHandler")
public class DefaultCallbackHandler implements SmtCallbackHandler {

    @Override
    public void handle(DrsSmtMessage message) {
    }

}
