package io.stacs.dapp.helloworld.service;

import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.demo.DemoBaseRequest;

/**
 * @author HuangShengli
 * @ClassName SmtDemoService
 * @Description SMT Service
 * @since 2020/9/12
 */
public interface SmtDemoService {

    /**
     * Send Request
     *
     * @param request
     * @return
     */
    DrsResponse doDemo(DemoBaseRequest request);
}
