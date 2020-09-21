package io.stacs.dapp.helloworld.callback;

import io.stacs.dapp.helloworld.dao.SmtPermissionDao;
import io.stacs.dapp.helloworld.dao.po.SmtPermission;
import io.stacs.dapp.helloworld.vo.DrsSmtMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Su Wenbo
 * @since 2020/9/21
 */
@Component("permissionCallbackHandler")
@RequiredArgsConstructor
public class PermissionCallbackHandler implements SmtCallbackHandler {

    private final SmtPermissionDao smtPermissionDao;

    @Override
    public void handle(DrsSmtMessage message) {
        String uuid = message.getHeader().getUuid();
        DrsSmtMessage.SmtBody body = message.getBody();
        String permissionId = body.getString("permissionId");

        SmtPermission smtPermission = smtPermissionDao.findByUuid(uuid);
        smtPermission.setPermissionId(permissionId);
        if (message.success()) {
            smtPermission.setStatus((byte) 1);
        } else {
            smtPermission.setStatus((byte) 2);
        }
        smtPermission.setMessageId(message.getHeader().getMessageId());
        smtPermission.setSessionId(message.getHeader().getSessionId());
        smtPermission.setUpdateAt(new Date());
        smtPermissionDao.save(smtPermission);
    }

}
