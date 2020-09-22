package io.stacs.dapp.helloworld.service.impl;

import com.alibaba.fastjson.JSON;
import io.stacs.dapp.helloworld.service.AbstractSendSmtMessageService;
import io.stacs.dapp.helloworld.service.SmtDemoService;
import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.DrsSmtMessage;
import io.stacs.dapp.helloworld.vo.demo.DemoBaseRequest;
import io.stacs.dapp.helloworld.vo.demo.TransferSmtRequest;
import org.springframework.stereotype.Service;

/**
 * @author Huang Shengli
 * @Description 转账类
 * @date 2020-09-13
 */
@Service("transfer")
public class TransferDemoServiceImpl extends AbstractSendSmtMessageService implements SmtDemoService {


    /**
     * 数字货币发行
     *
     * @param request
     * @return
     */
    @Override
    public DrsResponse doDemo(DemoBaseRequest request) {
        TransferSmtRequest transferRequest = (TransferSmtRequest) request;
        //组装报文数据
        DrsSmtMessage message = buildBaseMessage(request);
        //报文体
        DrsSmtMessage.SmtBody body = JSON.parseObject(JSON.toJSONString(transferRequest.getBody()), DrsSmtMessage.SmtBody.class);
        message.setBody(body);

        return doSend(message);
    }
}
