package io.stacs.dapp.helloworld.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import io.stacs.dapp.helloworld.config.DrsConfig;
import io.stacs.dapp.helloworld.dao.SmtMessageDao;
import io.stacs.dapp.helloworld.dao.po.SmtMessage;
import io.stacs.dapp.helloworld.httpclient.DrsClient;
import io.stacs.dapp.helloworld.vo.DrsRespCode;
import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.DrsSmtMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author HuangShengli
 * @ClassName AbstractSendSmtMessageService
 * @Description SMT Message Service
 * @since 2020/9/12
 */
@Slf4j
public abstract class AbstractSendSmtMessageService {

    @Autowired
    SmtMessageDao smtMessageDao;
    @Autowired
    protected DrsConfig drsConfig;

    /**
     *Send HTTP API Request (with SMT)
     *
     * @param message
     * @return
     */
    protected DrsResponse<DrsResponse.SmtResult> doSend(DrsSmtMessage message) {

        try {
            //http request message format
            log.info("Starting sending of HTTP API Request,smtCode={}，uuid={}", message.getHeader().getSmtCode(), message.getHeader().getUuid());
            JSONObject response = DrsClient.post(drsConfig.getSmtSendUrl(), message);
            //Retrieve HTTP Response
            DrsResponse<DrsResponse.SmtResult> result = JSONObject.parseObject(response.toJSONString(), new TypeReference<DrsResponse<DrsResponse.SmtResult>>() {
            });
            //Retrieve Status Code from Response
            DrsRespCode respCode = DrsRespCode.getByCode(result.getCode());
            //Check if Status is successful
            if (respCode == DrsRespCode.SUCCESS || respCode == DrsRespCode.ACCEPTED) {
                log.info("Request was sent successful");
                message.getHeader().setMessageId(result.getData().getMessageId());
                message.getHeader().setSessionId(result.getData().getSessionId());
                //Save to database
                smtMessageDao.save(convert(message));
            } else {
                log.warn("Response failed to send.");
            }
            return result;
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            return DrsResponse.fail("error", e.getMessage());
        }


    }

    private SmtMessage convert(DrsSmtMessage message) {
        SmtMessage messagePO = new SmtMessage();
        messagePO.setCreateAt(new Date());
        messagePO.setUpdateAt(new Date());
        //setup message header
        messagePO.setIdentifierId(message.getHeader().getIdentifierId());
        messagePO.setSessionId(message.getHeader().getSessionId());
        messagePO.setMessageId(message.getHeader().getMessageId());
        messagePO.setUuid(message.getHeader().getUuid());
        messagePO.setMessageSenderAddress(message.getHeader().getMessageSenderAddress());
        messagePO.setSmtCode(message.getHeader().getSmtCode());
        messagePO.setVersion(message.getHeader().getVersion());
        //body
        messagePO.setBody(message.getBody().toJSONString());
        //trailer
        messagePO.setAuthenticationTrailer(message.getTrailer() == null ? null : message.getTrailer().getAuthenticationTrailer());

        return messagePO;
    }
}
