package io.stacs.dapp.helloworld.service;

import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.DrsSmtData;
import org.springframework.stereotype.Service;

/**
 * @author HuangShengli
 * @ClassName SmtNotifyService
 * @Description DRS回调业务处理
 * @since 2020/9/12
 */
@Service
public class SmtNotifyService {

    public DrsResponse handle(DrsSmtData smtData) {

        return DrsResponse.success(null);
    }
}