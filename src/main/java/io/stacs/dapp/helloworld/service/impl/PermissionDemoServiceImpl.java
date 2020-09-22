package io.stacs.dapp.helloworld.service.impl;

import com.alibaba.fastjson.JSON;
import io.stacs.dapp.helloworld.constant.StatusEnum;
import io.stacs.dapp.helloworld.dao.SmtPermissionDao;
import io.stacs.dapp.helloworld.dao.po.SmtPermission;
import io.stacs.dapp.helloworld.service.AbstractSendSmtMessageService;
import io.stacs.dapp.helloworld.service.SmtDemoService;
import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.DrsSmtMessage;
import io.stacs.dapp.helloworld.vo.demo.DemoBaseRequest;
import io.stacs.dapp.helloworld.vo.demo.PermissionRequest;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Setup permission
 *
 * @author Su Wenbo
 * @since 2020/9/21
 */
@RequiredArgsConstructor
@Service("smtpm-fix-permission-set-1-v1")
public class PermissionDemoServiceImpl extends AbstractSendSmtMessageService implements SmtDemoService {

    private final SmtPermissionDao smtPermissionDao;

    /**
     * Permission setup to the blockchain
     *
     * @param request permission request
     * @return drs response
     */
    @Override
    public DrsResponse doDemo(DemoBaseRequest request) {
        PermissionRequest permissionRequest = (PermissionRequest) request;
        //Setup message using SMT format
        DrsSmtMessage message = buildBaseMessage(request);
        message.getHeader().setSmtCode("smtpm-fix-permission-set-1-v1");
        //Message Body
        DrsSmtMessage.SmtBody body = JSON.parseObject(JSON.toJSONString(permissionRequest.getBody()), DrsSmtMessage.SmtBody.class);
        message.setBody(body);
        //Send request
        DrsResponse<DrsResponse.SmtResult> drsResponse = doSend(message);
        if (!drsResponse.success()) {
            return drsResponse;
        }
        //run business logic
        doBusiness(permissionRequest, message, drsResponse);
        return drsResponse;
    }

    private void doBusiness(PermissionRequest permissionRequest,
                            DrsSmtMessage message,
                            DrsResponse<DrsResponse.SmtResult> smtResultDrsResponse) {
        //save to database
        SmtPermission smtPermission = new SmtPermission();
        smtPermission.setSmtCode(message.getHeader().getSmtCode());
        smtPermission.setStatus(StatusEnum.ChainStatus.PROCESSING.getCode());
        smtPermission.setUuid(message.getHeader().getUuid());
        smtPermission.setIdentifierId(message.getHeader().getIdentifierId());
        smtPermission.setMessageId(smtResultDrsResponse.getData().getMessageId());
        smtPermission.setSessionId(smtResultDrsResponse.getData().getSessionId());
        smtPermission.setModifierAddress(Strings.join(permissionRequest.getBody().getModifierAddress(), ','));
        smtPermission.setAuthorizedAddress(Strings.join(permissionRequest.getBody().getAuthorizedAddress(), ','));
        smtPermission.setCreateAt(new Date());
        smtPermission.setUpdateAt(new Date());
        smtPermissionDao.save(smtPermission);
    }

}
