package io.stacs.dapp.helloworld.callback;

import io.stacs.dapp.helloworld.vo.DrsSmtMessage;

/**
 * @author Su Wenbo
 * @since 2020/9/21
 */
public interface SmtCallbackHandler {

    /**
     * 回调时的特殊处理逻辑, DRS回调时调用
     *
     * @param message the message
     */
    void handle(DrsSmtMessage message);

}
