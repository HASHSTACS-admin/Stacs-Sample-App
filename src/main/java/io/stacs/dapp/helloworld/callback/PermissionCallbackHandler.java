package io.stacs.dapp.helloworld.callback;

import io.stacs.dapp.helloworld.constant.StatusEnum;
import io.stacs.dapp.helloworld.dao.SmtPermissionDao;
import io.stacs.dapp.helloworld.dao.po.SmtPermission;
import io.stacs.dapp.helloworld.vo.DrsSmtMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

import static io.stacs.dapp.helloworld.callback.SmtCallbackHandler.SUFFIX_CALLBACK;

/**
 * @author Su Wenbo
 * @since 2020/9/21
 */
@Component("smtpm-fix-permission-set-1-v1" + SUFFIX_CALLBACK)
@RequiredArgsConstructor
public class PermissionCallbackHandler implements SmtCallbackHandler {

    private final SmtPermissionDao smtPermissionDao;

    @Override
    public void handle(DrsSmtMessage message) {
        String uuid = message.getHeader().getUuid();
        DrsSmtMessage.SmtBody body = message.getBody();
        String permissionId = body.getString("permissionId");

        //更新表Smt Permission
        SmtPermission smtPermission = smtPermissionDao.findByUuid(uuid);
        smtPermission.setPermissionId(permissionId);
        if (message.success()) {
            smtPermission.setStatus(StatusEnum.ChainStatus.SUCCESS.getCode());
        } else {
            smtPermission.setStatus(StatusEnum.ChainStatus.FAIL.getCode());
        }
        smtPermission.setUpdateAt(new Date());
        smtPermissionDao.save(smtPermission);
    }

}
