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
 * @Description DRS回调业务处理
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
        //收到无效消息，直接返回成功
        if (messagePO == null) {
            log.info("收到无效回调消息,uuid={},messageId={}", message.getHeader().getUuid(), message.getHeader().getMessageId());
            return DrsResponse.success(null);
        }
        log.info("收到DRS回调消息:{}", message);
        messagePO.setResponseCode(message.getTrailer().getResponseCode());
        messagePO.setResponseMessage(message.getTrailer().getResponseMessage());
        messagePO.setBlockchainTransaction(JSON.toJSONString(message.getTrailer().getBlockchainTransaction()));
        messagePO.setTxs(parseTxs(message.getTrailer().getBlockchainTransaction()));
        //更新body，因为有些信息需要上链成功才能返回
        messagePO.setBody(message.getBody().toJSONString());
        messagePO.setUpdateAt(new Date());
        smtMessageDao.save(messagePO);
        if (DrsRespCode.SUCCESS.getCode().equals(message.getTrailer().getResponseCode()) || DrsRespCode.ACCEPTED.getCode().equals(message.getTrailer().getResponseCode())) {
            log.info("您发送的报文,已上链成功，交易IDs:{}", messagePO.getTxs());
        } else {
            log.info("您发送的报文,已上链失败，错误原因:{}", message.getTrailer().getResponseMessage());
        }
        log.info("DRS回调处理成功");
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
