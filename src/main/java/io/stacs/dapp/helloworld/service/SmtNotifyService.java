package io.stacs.dapp.helloworld.service;

import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.DrsSmtMessage;

/**
 * @author HuangShengli
 * @ClassName SmtNotifyService
 * @Description DRS回调业务处理
 * @since 2020/9/12
 */
public interface SmtNotifyService {

    /**
     * 回调处理
     *
     * @param message
     * @return
     */
    DrsResponse handle(DrsSmtMessage message);
}
