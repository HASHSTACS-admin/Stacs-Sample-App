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
import io.stacs.dapp.helloworld.vo.demo.AddSnapshotRequest;
import io.stacs.dapp.helloworld.vo.demo.DemoBaseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;

/**
 * 打区块链快照服务
 *
 * @author Su Wenbo
 * @since 2020/9/24
 */
@Service("smtc-snapshot-snapshot-add-1-v1")
public class AddSnapshotDemoServiceImpl extends AbstractSendSmtMessageService implements SmtDemoService {

    @Autowired
    private AssetOperationDao assetOperationDao;

    @Override
    public DrsResponse doDemo(DemoBaseRequest request) {
        AddSnapshotRequest snapshotRequest = (AddSnapshotRequest) request;

        //组装报文数据
        DrsSmtMessage message = buildBaseMessage(request);
        message.getHeader().setSmtCode("smtc-snapshot-snapshot-add-1-v1");
        //报文体
        DrsSmtMessage.SmtBody body = JSON.parseObject(JSON.toJSONString(snapshotRequest.getBody()), DrsSmtMessage.SmtBody.class);
        message.setBody(body);
        //发送请求
        DrsResponse<DrsResponse.SmtResult> drsResponse = doSend(message);
        if (!drsResponse.success()) {
            return drsResponse;
        }
        //做额外逻辑
        Long operationId = doBusiness(message, drsResponse);
        return DrsResponse.success(Collections.singletonMap("operationId", operationId));
    }

    private Long doBusiness(DrsSmtMessage message, DrsResponse<DrsResponse.SmtResult> drsResponse) {
        long operationId = CommonUtil.nextTimeId();
        //组装asset operation，存数据库
        AssetOperation assetOperation = new AssetOperation();
        assetOperation.setOperationId(operationId);
        assetOperation.setType(AssetOperationType.ADD_SNAPSHOT.getCode());
        assetOperation.setSmtCode(message.getHeader().getSmtCode());
        assetOperation.setDescription(message.getBody().getString("description"));
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
