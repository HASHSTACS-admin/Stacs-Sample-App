package io.stacs.dapp.helloworld.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import io.stacs.dapp.helloworld.config.DrsConfig;
import io.stacs.dapp.helloworld.dao.SmtMessageDao;
import io.stacs.dapp.helloworld.dao.po.SmtMessage;
import io.stacs.dapp.helloworld.httpclient.DrsClient;
import io.stacs.dapp.helloworld.utils.UUIDUtil;
import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.DrsSmtMessage;
import io.stacs.dapp.helloworld.vo.demo.DemoBaseRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author HuangShengli
 * @ClassName AbstractSendSmtMessageService
 * @Description SMT Format Message service
 * @since 2020/9/12
 */
@Slf4j
public abstract class AbstractSendSmtMessageService {

    @Autowired
    SmtMessageDao smtMessageDao;
    @Autowired
    protected DrsConfig drsConfig;

    /**
     * base impl for message Header and Trailer
     *
     * @param request
     * @return
     */
    protected DrsSmtMessage buildBaseMessage(DemoBaseRequest request) {
        //Message Header
        DrsSmtMessage.SmtHeader header = DrsSmtMessage.SmtHeader.builder().
                identifierId(drsConfig.getMyIdentifierId())
                .messageSenderAddress(request.getHeader().getMessageSenderAddress())
                .smtCode(request.getHeader().getSmtCode())
                //UUID has to be unique and created here
                .uuid(UUIDUtil.uuid())
                .build();
        //Message Trailer
        DrsSmtMessage.SmtTrailer trailer = null;
        if (null != request.getTrailer()) {
            trailer = DrsSmtMessage.SmtTrailer
                    .builder()
                    .authenticationTrailer(request.getTrailer().getAuthenticationTrailer())
                    .build();
        }

        DrsSmtMessage message = DrsSmtMessage.builder()
                .header(header)
                .trailer(trailer)
                .build();
        return message;
    }

    /**
     * Send API Request and save to database
     *
     * @param message
     * @return
     */
    protected DrsResponse<DrsResponse.SmtResult> doSend(DrsSmtMessage message) {

        try {
            //send HTTP API request
            log.info("Start to send API request,smtCode={}，uuid={}", message.getHeader().getSmtCode(), message.getHeader().getUuid());
            JSONObject response = DrsClient.post(drsConfig.getSmtSendUrl(), message);
            //Retrieve the synchronous response from the DRS
            DrsResponse<DrsResponse.SmtResult> result = JSONObject.parseObject(response.toJSONString(), new TypeReference<DrsResponse<DrsResponse.SmtResult>>() {
            });
            //Check if API request was received successfully by the DRS
            if (result.success()) {
                log.info("API Request was sent successfully.");
                message.getHeader().setMessageId(result.getData().getMessageId());
                message.getHeader().setSessionId(result.getData().getSessionId());
                //Save to database
                smtMessageDao.save(convert(message));
            } else {
                log.warn("API request was not sent successfully.:{},{}", result.getCode(), result.getMessage());
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
        //header message
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
