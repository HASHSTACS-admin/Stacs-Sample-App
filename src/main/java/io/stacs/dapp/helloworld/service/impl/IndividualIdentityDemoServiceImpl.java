package io.stacs.dapp.helloworld.service.impl;

import com.alibaba.fastjson.JSON;
import io.stacs.dapp.helloworld.service.AbstractSendSmtMessageService;
import io.stacs.dapp.helloworld.service.SmtDemoService;
import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.DrsSmtMessage;
import io.stacs.dapp.helloworld.vo.demo.DemoBaseRequest;
import io.stacs.dapp.helloworld.vo.demo.IndividualIdentityRequest;
import org.springframework.stereotype.Service;

/**
 * 设置地址身份信息的报文服务
 *
 * @author Su Wenbo
 * @since 2020/9/21
 */
@Service("smti-individual-identity-set-1-v1")
public class IndividualIdentityDemoServiceImpl extends AbstractSendSmtMessageService implements SmtDemoService {

    /**
     * 设置地址身份信息
     *
     * @param request permission request
     * @return drs response
     */
    @Override
    public DrsResponse doDemo(DemoBaseRequest request) {
        IndividualIdentityRequest individualIdentityRequest = (IndividualIdentityRequest) request;
        //组装报文数据
        DrsSmtMessage message = buildBaseMessage(request);
        //报文体
        DrsSmtMessage.SmtBody body = JSON.parseObject(JSON.toJSONString(individualIdentityRequest.getBody()), DrsSmtMessage.SmtBody.class);
        message.setBody(body);
        //发送请求
        return doSend(message);
    }

}
