package io.stacs.dapp.helloworld.callback;

import io.stacs.dapp.helloworld.constant.StatusEnum;
import io.stacs.dapp.helloworld.dao.AssetOperationDao;
import io.stacs.dapp.helloworld.dao.po.AssetOperation;
import io.stacs.dapp.helloworld.vo.DrsSmtMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

import static io.stacs.dapp.helloworld.callback.SmtCallbackHandler.SUFFIX_CALLBACK;

/**
 * @author Su Wenbo
 * @since 2020/9/21
 */
@Component("smtc-snapshot-snapshot-add-1-v1" + SUFFIX_CALLBACK)
public class AddSnapshotCallbackHandler implements SmtCallbackHandler {

    @Autowired
    private AssetOperationDao assetOperationDao;

    @Override
    public void handle(DrsSmtMessage message) {
        String uuid = message.getHeader().getUuid();

        //Update Asset Operation
        AssetOperation assetOperation = assetOperationDao.findByUuid(uuid);
        if (message.success()) {
            //successful record on blockchain with block height
            assetOperation.setBlockHeight(message.getBody().getString("blockHeight"));
            assetOperation.setStatus(StatusEnum.ChainStatus.SUCCESS.getCode());
        } else {
            assetOperation.setStatus(StatusEnum.ChainStatus.FAIL.getCode());
        }
        assetOperation.setUpdateAt(new Date());
        assetOperationDao.save(assetOperation);
    }

}
