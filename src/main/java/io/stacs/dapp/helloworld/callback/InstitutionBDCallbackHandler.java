package io.stacs.dapp.helloworld.callback;

import io.stacs.dapp.helloworld.constant.StatusEnum;
import io.stacs.dapp.helloworld.dao.BdFunctionPermissionRelationDao;
import io.stacs.dapp.helloworld.dao.SmtBdDao;
import io.stacs.dapp.helloworld.dao.po.BdFunctionPermissionRelation;
import io.stacs.dapp.helloworld.dao.po.SmtBd;
import io.stacs.dapp.helloworld.vo.DrsSmtMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static io.stacs.dapp.helloworld.callback.SmtCallbackHandler.SUFFIX_CALLBACK;

/**
 * @author Su Wenbo
 * @since 2020/9/21
 */
@Component("smtbd-institution-business-special-1-v1" + SUFFIX_CALLBACK)
@RequiredArgsConstructor
public class InstitutionBDCallbackHandler implements SmtCallbackHandler {

    private final SmtBdDao smtBdDao;

    private final BdFunctionPermissionRelationDao bdFunctionPermissionRelationDao;

    @Override
    public void handle(DrsSmtMessage message) {
        String uuid = message.getHeader().getUuid();
        DrsSmtMessage.SmtBody body = message.getBody();
        String bdId = body.getString("bdId");

        //更新表Smt BD
        SmtBd smtBd = smtBdDao.findByUuid(uuid);
        smtBd.setBdId(bdId);
        if (message.success()) {
            smtBd.setStatus(StatusEnum.ChainStatus.SUCCESS.getCode());
        } else {
            smtBd.setStatus(StatusEnum.ChainStatus.FAIL.getCode());
        }
        smtBd.setUpdateAt(new Date());
        smtBdDao.save(smtBd);

        //插入数据到Bd Function Permission Relation
        List<BdFunctionPermissionRelation> bdFunctionPermissionRelationList = new ArrayList<>();
        Stream.of("freezeIdentityPermissionId", "addAttestationPermissionId", "unfreezeIdentityPermissionId", "setIdentityPermissionId")
                .filter(o -> Objects.nonNull(body.get(o)))
                .forEach(o -> {
                    BdFunctionPermissionRelation bdFunctionPermissionRelation = new BdFunctionPermissionRelation();
                    bdFunctionPermissionRelation.setBdId(bdId);
                    bdFunctionPermissionRelation.setPermissionId(body.get(o).toString());
                    bdFunctionPermissionRelation.setFunctionName(o);
                    bdFunctionPermissionRelation.setCreateAt(new Date());
                    bdFunctionPermissionRelation.setUpdateAt(new Date());
                    bdFunctionPermissionRelationList.add(bdFunctionPermissionRelation);
                });
        bdFunctionPermissionRelationDao.saveAll(bdFunctionPermissionRelationList);
    }

}
