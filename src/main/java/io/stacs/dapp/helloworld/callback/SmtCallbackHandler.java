package io.stacs.dapp.helloworld.callback;

import io.stacs.dapp.helloworld.vo.DrsSmtMessage;

/**
 * @author Su Wenbo
 * @since 2020/9/21
 */
public interface SmtCallbackHandler {

    String SUFFIX_CALLBACK = "_callback";

    /**
     * Callback handler (messages originating from the DRS)
     *
     * @param message the message
     */
    void handle(DrsSmtMessage message);

}
