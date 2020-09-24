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
 * Abs BD handler
 *
 * @author Su Wenbo
 * @since 2020/9/21
 */
@Component("smtbd-abs-abs-issue-1-v1" + SUFFIX_CALLBACK)
@RequiredArgsConstructor
public class AbsBDCallbackHandler implements SmtCallbackHandler {

    private final SmtBdDao smtBdDao;

    private final BdFunctionPermissionRelationDao bdFunctionPermissionRelationDao;

    @Override
    public void handle(DrsSmtMessage message) {
        String uuid = message.getHeader().getUuid();
        DrsSmtMessage.SmtBody body = message.getBody();
        String bdId = body.getString("bdId");

        //Update BD
        SmtBd smtBd = smtBdDao.findByUuid(uuid);
        smtBd.setBdId(bdId);
        if (message.success()) {
            smtBd.setStatus(StatusEnum.ChainStatus.SUCCESS.getCode());
        } else {
            smtBd.setStatus(StatusEnum.ChainStatus.FAIL.getCode());
        }
        smtBd.setUpdateAt(new Date());
        smtBdDao.save(smtBd);

        if (message.success()) {
            //Update BD and Permission relation in database
            List<BdFunctionPermissionRelation> bdFunctionPermissionRelationList = new ArrayList<>();
            Stream.of("buybackPermissionId", "buybackFreezePermissionId", "interestSettlePermissionId", "additionalIssuePermissionId", "tokenFreezePermissionId", "tokenUnfreezePermissionId")
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

}
