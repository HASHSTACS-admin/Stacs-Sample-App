package io.stacs.dapp.helloworld.service;

import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.DrsSmtMessage;

/**
 * @author HuangShengli
 * @ClassName SmtNotifyService
 * @Description DRS Callback Notification Service
 * @since 2020/9/12
 */
public interface SmtNotifyService {

    /**
     * Callback Response Handler
     *
     * @param message
     * @return
     */
    DrsResponse handle(DrsSmtMessage message);
}
