package io.stacs.dapp.helloworld.service.impl;

import com.alibaba.fastjson.JSON;
import io.stacs.dapp.helloworld.dao.SmtMessageDao;
import io.stacs.dapp.helloworld.dao.po.SmtMessage;
import io.stacs.dapp.helloworld.service.SmtNotifyService;
import io.stacs.dapp.helloworld.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author HuangShengli
 * @ClassName SmtNotifyService
 * @Description DRS Callback Notification Service Implementation
 * @since 2020/9/12
 */
@Slf4j
@Service
public class SmtNotifyServiceImpl implements SmtNotifyService {
    @Autowired
    SmtMessageDao smtMessageDao;


    @Override
    public DrsResponse handle(DrsSmtMessage message) {

        SmtMessage messagePO = smtMessageDao.findByUuid(message.getHeader().getUuid());
        //If message object is null, return success
        if (messagePO == null) {
            log.info("received null message,uuid={},messageId={}", message.getHeader().getUuid(), message.getHeader().getMessageId());
            return DrsResponse.success(null);
        }
        log.info("received DRS response message:{}", message);
        messagePO.setResponseCode(message.getTrailer().getResponseCode());
        messagePO.setResponseMessage(message.getTrailer().getResponseMessage());
        messagePO.setBlockchainTransaction(JSON.toJSONString(message.getTrailer().getBlockchainTransaction()));
        messagePO.setTxs(parseTxs(message.getTrailer().getBlockchainTransaction()));
        //Update message body as some transactions need to be on the blockchain before it can be returned
        messagePO.setBody(message.getBody().toJSONString());
        messagePO.setUpdateAt(new Date());
        smtMessageDao.save(messagePO);
        if (DrsRespCode.SUCCESS.getCode().equals(message.getTrailer().getResponseCode()) || DrsRespCode.ACCEPTED.getCode().equals(message.getTrailer().getResponseCode())) {
            log.info("The transactions are on the blockchain and are successful, Transaction IDs are:{}", messagePO.getTxs());
        } else {
            log.info("The transactions are on the blockchain but have failed with the error message:{}", message.getTrailer().getResponseMessage());
        }
        log.info("DRS Response is received successfully.");
        return DrsResponse.success(null);
    }

    private String parseTxs(List<BlockchainTransaction> txs) {
        if (CollectionUtils.isEmpty(txs)) {
            return null;
        }
        List<Txid> txids = new ArrayList<>(10);
        txs.forEach(t -> txids.add(new Txid(t.getTxId(), t.getTxType())));
        return JSON.toJSONString(txids);
    }
}
