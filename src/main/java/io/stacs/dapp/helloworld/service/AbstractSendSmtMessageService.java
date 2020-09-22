package io.stacs.dapp.helloworld.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import io.stacs.dapp.helloworld.config.DrsConfig;
import io.stacs.dapp.helloworld.dao.SmtMessageDao;
import io.stacs.dapp.helloworld.dao.po.SmtMessage;
import io.stacs.dapp.helloworld.httpclient.DrsClient;
import io.stacs.dapp.helloworld.utils.CommonUtil;
import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.DrsSmtMessage;
import io.stacs.dapp.helloworld.vo.demo.DemoBaseRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author HuangShengli
 * @ClassName AbstractSendSmtMessageService
 * @Description 报文发送服务
 * @since 2020/9/12
 */
@Slf4j
public abstract class AbstractSendSmtMessageService {

    @Autowired
    SmtMessageDao smtMessageDao;
    @Autowired
    protected DrsConfig drsConfig;

    /**
     * 统一处理报文头和报文尾
     *
     * @param request
     * @return
     */
    protected DrsSmtMessage buildBaseMessage(DemoBaseRequest request) {
        //报文头
        DrsSmtMessage.SmtHeader header = DrsSmtMessage.SmtHeader.builder().
                identifierId(drsConfig.getMyIdentifierId())
                .messageSenderAddress(request.getHeader().getMessageSenderAddress())
                .smtCode(request.getHeader().getSmtCode())
                //uuid由商户生成
                .uuid(CommonUtil.uuid())
                .build();
        //报文尾
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
     * 发送报文并保存到报文记录表
     *
     * @param message
     * @return
     */
    protected DrsResponse<DrsResponse.SmtResult> doSend(DrsSmtMessage message) {

        try {
            //http 发送报文
            log.info("发送报文开始,smtCode={}，uuid={}", message.getHeader().getSmtCode(), message.getHeader().getUuid());
            JSONObject response = DrsClient.post(drsConfig.getSmtSendUrl(), message);
            //反序列化为DrsResponse对象
            DrsResponse<DrsResponse.SmtResult> result = JSONObject.parseObject(response.toJSONString(), new TypeReference<DrsResponse<DrsResponse.SmtResult>>() {
            });
            //判断DRS受理结果
            if (result.success()) {
                log.info("报文发送成功");
                message.getHeader().setMessageId(result.getData().getMessageId());
                message.getHeader().setSessionId(result.getData().getSessionId());
                //保存到数据库
                smtMessageDao.save(convert(message));
            } else {
                log.warn("报文发送失败:{},{}", result.getCode(), result.getMessage());
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
        //header信息
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
