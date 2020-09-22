package io.stacs.dapp.helloworld.service.impl;

import com.alibaba.fastjson.JSON;
import io.stacs.dapp.helloworld.service.AbstractSendSmtMessageService;
import io.stacs.dapp.helloworld.service.SmtDemoService;
import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.DrsSmtMessage;
import io.stacs.dapp.helloworld.vo.demo.DemoBaseRequest;
import io.stacs.dapp.helloworld.vo.demo.DigitalCurrencySmtRequest;
import org.springframework.stereotype.Service;

/**
 * @author Huang Shengli
 * @Description 发行数字货币
 * @date 2020-09-13
 */
@Service("smta-dc-dc-issue-1-v1")
public class DigitalCurrencyDemoServiceImpl extends AbstractSendSmtMessageService implements SmtDemoService {


    /**
     * 数字货币发行
     *
     * @param request
     * @return
     */
    @Override
    public DrsResponse doDemo(DemoBaseRequest request) {
        DigitalCurrencySmtRequest dcRequest = (DigitalCurrencySmtRequest) request;
        //组装报文数据
        DrsSmtMessage message = buildBaseMessage(request);
        message.getHeader().setSmtCode("smta-dc-dc-issue-1-v1");
        //报文体
        DrsSmtMessage.SmtBody body = JSON.parseObject(JSON.toJSONString(dcRequest.getBody()), DrsSmtMessage.SmtBody.class);
        message.setBody(body);

        return doSend(message);
    }
}
