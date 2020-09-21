package io.stacs.dapp.helloworld.service.impl;

import com.alibaba.fastjson.JSON;
import io.stacs.dapp.helloworld.service.AbstractSendSmtMessageService;
import io.stacs.dapp.helloworld.service.SmtDemoService;
import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.DrsSmtMessage;
import io.stacs.dapp.helloworld.vo.demo.AbsBDRequest;
import io.stacs.dapp.helloworld.vo.demo.DemoBaseRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Huang Shengli
 * @Description 发布ABS的BD
 * @date 2020-09-13
 */
@Service("smtbd-abs-abs-issue-1-v1")
public class AbsBDDemoServiceImpl extends AbstractSendSmtMessageService implements SmtDemoService {


    /**
     * abs的BD发布
     *
     * @param request
     * @return
     */
    @Transactional
    @Override
    public DrsResponse doDemo(DemoBaseRequest request) {
        AbsBDRequest bdRequest = (AbsBDRequest) request;
        //组装报文数据
        DrsSmtMessage message = buildBaseMessage(request);
        message.getHeader().setSmtCode("smtbd-abs-abs-issue-1-v1");
        //报文体
        DrsSmtMessage.SmtBody body = JSON.parseObject(JSON.toJSONString(bdRequest.getBody()), DrsSmtMessage.SmtBody.class);
        message.setBody(body);
        DrsResponse drsResponse = doSend(message);
        if (!drsResponse.success()) {
            return drsResponse;
        }
        //todo 业务处理
        //doBusiness
        //请求DRS
        return drsResponse;
    }

    private void doBusiness(DrsResponse drsResponse) {

    }
}
