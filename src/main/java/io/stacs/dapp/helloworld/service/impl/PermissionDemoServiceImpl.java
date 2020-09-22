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
 * 权限创建报文服务
 *
 * @author Su Wenbo
 * @since 2020/9/21
 */
@RequiredArgsConstructor
@Service("smtpm-fix-permission-set-1-v1")
public class PermissionDemoServiceImpl extends AbstractSendSmtMessageService implements SmtDemoService {

    private final SmtPermissionDao smtPermissionDao;

    /**
     * 列表格式的权限创建
     *
     * @param request permission request
     * @return drs response
     */
    @Override
    public DrsResponse doDemo(DemoBaseRequest request) {
        PermissionRequest permissionRequest = (PermissionRequest) request;
        //组装报文数据
        DrsSmtMessage message = buildBaseMessage(request);
        message.getHeader().setSmtCode("smtpm-fix-permission-set-1-v1");
        //报文体
        DrsSmtMessage.SmtBody body = JSON.parseObject(JSON.toJSONString(permissionRequest.getBody()), DrsSmtMessage.SmtBody.class);
        message.setBody(body);
        //发送请求
        DrsResponse<DrsResponse.SmtResult> drsResponse = doSend(message);
        if (!drsResponse.success()) {
            return drsResponse;
        }
        //做额外逻辑
        doBusiness(permissionRequest, message, drsResponse);
        return drsResponse;
    }

    private void doBusiness(PermissionRequest permissionRequest,
                            DrsSmtMessage message,
                            DrsResponse<DrsResponse.SmtResult> smtResultDrsResponse) {
        //组装报文Permission数据，存数据库
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
