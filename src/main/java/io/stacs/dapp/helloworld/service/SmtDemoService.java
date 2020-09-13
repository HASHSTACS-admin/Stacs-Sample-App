package io.stacs.dapp.helloworld.service;

import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.demo.DemoBaseRequest;

/**
 * @author HuangShengli
 * @ClassName SmtDemoService
 * @Description 报文发送服务
 * @since 2020/9/12
 */
public interface SmtDemoService {

    /**
     * 发送报文
     *
     * @param request
     * @return
     */
    DrsResponse doDemo(DemoBaseRequest request);
}
