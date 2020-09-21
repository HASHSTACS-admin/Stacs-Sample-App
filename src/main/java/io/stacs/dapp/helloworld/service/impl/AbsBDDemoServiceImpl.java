package io.stacs.dapp.helloworld.service.impl;

import com.alibaba.fastjson.JSON;
import io.stacs.dapp.helloworld.service.AbstractSendSmtMessageService;
import io.stacs.dapp.helloworld.service.SmtDemoService;
import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.DrsSmtMessage;
import io.stacs.dapp.helloworld.vo.demo.AbsBDRequest;
import io.stacs.dapp.helloworld.vo.demo.DemoBaseRequest;
import org.springframework.stereotype.Service;

/**
 * @author Huang Shengli
 * @Description 发布ABS的BD
 * @date 2020-09-13
 */
@Service("smtbd-abs-abs-issue-1-v1")
public class AbsBDDemoServiceImpl extends AbstractSendSmtMessageService implements SmtDemoService {


    /**
     * 数字货币发行
     *
     * @param request
     * @return
     */
    @Override
    public DrsResponse doDemo(DemoBaseRequest request) {
        AbsBDRequest bdRequest = (AbsBDRequest) request;
        //组装报文数据
        DrsSmtMessage message = buildBaseMessage(request);
        message.getHeader().setSmtCode("smtbd-abs-abs-issue-1-v1");
        //报文体
        DrsSmtMessage.SmtBody body = JSON.parseObject(JSON.toJSONString(bdRequest.getBody()), DrsSmtMessage.SmtBody.class);
        message.setBody(body);

        //请求DRS
        return doSend(message);
    }
}
