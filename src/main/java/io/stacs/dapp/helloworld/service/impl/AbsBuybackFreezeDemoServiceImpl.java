package io.stacs.dapp.helloworld.service.impl;

import com.alibaba.fastjson.JSON;
import io.stacs.dapp.helloworld.constant.AssetOperationType;
import io.stacs.dapp.helloworld.constant.StatusEnum;
import io.stacs.dapp.helloworld.dao.AssetOperationDao;
import io.stacs.dapp.helloworld.dao.po.AssetOperation;
import io.stacs.dapp.helloworld.service.AbstractSendSmtMessageService;
import io.stacs.dapp.helloworld.service.SmtDemoService;
import io.stacs.dapp.helloworld.utils.CommonUtil;
import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.DrsSmtMessage;
import io.stacs.dapp.helloworld.vo.demo.AbsBuybackFreezeRequest;
import io.stacs.dapp.helloworld.vo.demo.DemoBaseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;

/**
 * ABS Freezing of Assets (before Redemption)
 *
 * @author Su Wenbo
 * @since 2020/9/24
 */
@Service("smtt-abs-buyback-freeze-2-v1")
public class AbsBuybackFreezeDemoServiceImpl extends AbstractSendSmtMessageService implements SmtDemoService {

    @Autowired
    private AssetOperationDao assetOperationDao;

    @Override
    public DrsResponse doDemo(DemoBaseRequest request) {
        AbsBuybackFreezeRequest absBuybackFreezeRequest = (AbsBuybackFreezeRequest) request;

        //Create Request Message
        DrsSmtMessage message = buildBaseMessage(request);
        message.getHeader().setSmtCode("smtt-abs-buyback-freeze-2-v1");
        //Request Body
        DrsSmtMessage.SmtBody body = JSON.parseObject(JSON.toJSONString(absBuybackFreezeRequest.getBody()), DrsSmtMessage.SmtBody.class);
        message.setBody(body);
        //Send Request
        DrsResponse<DrsResponse.SmtResult> drsResponse = doSend(message);
        if (!drsResponse.success()) {
            return drsResponse;
        }
        //Run business logic
        Long operationId = doBusiness(message, drsResponse);
        return DrsResponse.success(Collections.singletonMap("operationId", String.valueOf(operationId)));
    }

    private Long doBusiness(DrsSmtMessage message, DrsResponse<DrsResponse.SmtResult> drsResponse) {
        long operationId = CommonUtil.nextTimeId();
        //save to database
        AssetOperation assetOperation = new AssetOperation();
        assetOperation.setOperationId(operationId);
        assetOperation.setType(AssetOperationType.BUYBACK_FREEZE.getCode());
        assetOperation.setSmtCode(message.getHeader().getSmtCode());
        assetOperation.setAssetId(message.getBody().getString("assetId"));
        assetOperation.setDescription(message.getBody().getString("info"));
        assetOperation.setUuid(message.getHeader().getUuid());
        assetOperation.setMessageId(drsResponse.getData().getMessageId());
        assetOperation.setSessionId(drsResponse.getData().getSessionId());
        assetOperation.setStatus(StatusEnum.ChainStatus.PROCESSING.getCode());
        assetOperation.setCreateAt(new Date());
        assetOperation.setUpdateAt(new Date());
        assetOperationDao.save(assetOperation);
        return operationId;
    }

}
