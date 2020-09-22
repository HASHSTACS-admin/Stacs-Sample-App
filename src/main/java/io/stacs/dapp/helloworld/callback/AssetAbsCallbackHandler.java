package io.stacs.dapp.helloworld.callback;

import io.stacs.dapp.helloworld.constant.StatusEnum;
import io.stacs.dapp.helloworld.dao.AssetAbsDao;
import io.stacs.dapp.helloworld.dao.po.AssetAbs;
import io.stacs.dapp.helloworld.vo.DrsSmtMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

import static io.stacs.dapp.helloworld.callback.SmtCallbackHandler.SUFFIX_CALLBACK;

/**
 * Abs issue handler
 *
 * @author Su Wenbo
 * @since 2020/9/21
 */
@Component("smta-abs-corporation-issue-1-v1" + SUFFIX_CALLBACK)
@RequiredArgsConstructor
public class AssetAbsCallbackHandler implements SmtCallbackHandler {

    private final AssetAbsDao assetAbsDao;


    @Override
    public void handle(DrsSmtMessage message) {
        String uuid = message.getHeader().getUuid();
        DrsSmtMessage.SmtBody body = message.getBody();
        String contractAddress = body.getString("contractAddress");

        //更新表Smt BD
        AssetAbs abs = assetAbsDao.findByUuid(uuid);
        abs.setContractAddress(contractAddress);
        if (message.success()) {
            abs.setStatus(StatusEnum.ChainStatus.SUCCESS.getCode());
        } else {
            abs.setStatus(StatusEnum.ChainStatus.FAIL.getCode());
        }
        abs.setUpdateAt(new Date());
        assetAbsDao.save(abs);
    }

}
