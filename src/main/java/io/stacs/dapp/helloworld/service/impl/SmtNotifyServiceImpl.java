package io.stacs.dapp.helloworld.service.impl;

import com.alibaba.fastjson.JSON;
import io.stacs.dapp.helloworld.callback.SmtCallbackHandler;
import io.stacs.dapp.helloworld.dao.SmtMessageDao;
import io.stacs.dapp.helloworld.dao.po.SmtMessage;
import io.stacs.dapp.helloworld.service.SmtNotifyService;
import io.stacs.dapp.helloworld.vo.BlockchainTransaction;
import io.stacs.dapp.helloworld.vo.DrsRespCode;
import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.DrsSmtMessage;
import io.stacs.dapp.helloworld.vo.Txid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author HuangShengli
 * @ClassName SmtNotifyService
 * @Description DRS Callback API Service
 * @since 2020/9/12
 */
@Slf4j
@Service
public class SmtNotifyServiceImpl implements SmtNotifyService {

    @Autowired
    SmtMessageDao smtMessageDao;

    @Autowired
    Map<String, SmtCallbackHandler> smtCallbackHandlerMap;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DrsResponse handle(DrsSmtMessage message) {

        SmtMessage messagePO = smtMessageDao.findByUuid(message.getHeader().getUuid());
        //Received empty message, return success
        if (messagePO == null) {
            log.info("Received empty callback message,uuid={},messageId={}", message.getHeader().getUuid(), message.getHeader().getMessageId());
            return DrsResponse.success(null);
        }
        log.info("Received DRS callback message:{}", message);
        messagePO.setResponseCode(message.getTrailer().getResponseCode());
        messagePO.setResponseMessage(message.getTrailer().getResponseMessage());
        messagePO.setBlockchainTransaction(JSON.toJSONString(message.getTrailer().getBlockchainTransaction()));
        messagePO.setTxs(parseTxs(message.getTrailer().getBlockchainTransaction()));
        //Modify message body
        messagePO.setBody(message.getBody().toJSONString());
        messagePO.setUpdateAt(new Date());
        smtMessageDao.save(messagePO);
        if (DrsRespCode.SUCCESS.getCode().equals(message.getTrailer().getResponseCode()) || DrsRespCode.ACCEPTED.getCode().equals(message.getTrailer().getResponseCode())) {
            log.info("transaction is on the chain and is successful, transaction ids:{}", messagePO.getTxs());
        } else {
            log.info("transaction is on the chain but has failed, error message:{}", message.getTrailer().getResponseMessage());
        }
        //Handle the DRS callback
        SmtCallbackHandler smtCallbackHandler = smtCallbackHandlerMap.get(message.getHeader().getSmtCode() + SmtCallbackHandler.SUFFIX_CALLBACK);
        if (Objects.nonNull(smtCallbackHandler)) {
            smtCallbackHandler.handle(message);
        }
        log.info("DRS callback handler was successful.");
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
