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
            //取出响应码
            DrsRespCode respCode = DrsRespCode.getByCode(result.getCode());
            //判断DRS受理结果
            if (respCode == DrsRespCode.SUCCESS || respCode == DrsRespCode.ACCEPTED) {
                log.info("报文发送成功");
                message.getHeader().setMessageId(result.getData().getMessageId());
                message.getHeader().setSessionId(result.getData().getSessionId());
                //保存到数据库
                smtMessageDao.save(convert(message));
            } else {
                log.warn("报文发送失败");
            }
            return result;
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            return DrsResponse.fail("error", e.getMessage());
        }


    }

    private SmtMessage convert(DrsSmtMessage message) {
        SmtMessage messagePO = new SmtMessage();
        //header信息
        messagePO.setIdentifierId(message.getHeader().getIdentifierId());
        messagePO.setSessionId(message.getHeader().getSessionId());
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
