package io.stacs.dapp.helloworld.service.impl;

import com.alibaba.fastjson.JSON;
import io.stacs.dapp.helloworld.constant.StatusEnum;
import io.stacs.dapp.helloworld.dao.TradeBidOrderDao;
import io.stacs.dapp.helloworld.dao.TradeOfferOrderDao;
import io.stacs.dapp.helloworld.dao.po.TradeBidOrder;
import io.stacs.dapp.helloworld.dao.po.TradeOfferOrder;
import io.stacs.dapp.helloworld.service.AbstractSendSmtMessageService;
import io.stacs.dapp.helloworld.service.SmtDemoService;
import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.DrsSmtMessage;
import io.stacs.dapp.helloworld.vo.demo.AbsBidRequest;
import io.stacs.dapp.helloworld.vo.demo.DemoBaseRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * Bid order for ABS
 *
 * @author Su Wenbo
 * @since 2020/9/21
 */
@Slf4j
@Service("smtt-abs-subscription-bid-2-v1")
public class AbsBidDemoServiceImpl extends AbstractSendSmtMessageService implements SmtDemoService {

    @Autowired
    private TradeOfferOrderDao tradeOfferOrderDao;

    @Autowired
    private TradeBidOrderDao tradeBidOrderDao;

    /**
     * bid order to purchase ABS
     *
     * @param request permission request
     * @return drs response
     */
    @Override
    public DrsResponse doDemo(DemoBaseRequest request) {
        AbsBidRequest absBidRequest = (AbsBidRequest) request;
        String sessionId = request.getHeader().getSessionId();
        //query offer
        TradeOfferOrder tradeOfferOrder = tradeOfferOrderDao.findBySessionId(sessionId);
        //verification
        if (Objects.isNull(tradeOfferOrder)) {
            return DrsResponse.fail("error", "Trade offer order doesn't exist!");
        }
        if (!StatusEnum.OfferBizStatus.OFFER.getCode().equals(tradeOfferOrder.getBizStatus())) {
            return DrsResponse.fail("error", "current state does not support this operation!");
        }
        if (tradeOfferOrder.getOrderStartTime().after(new Date())) {
            return DrsResponse.fail("error", "not before order start date!");
        }
        if (tradeOfferOrder.getOrderEndTime().before(new Date())) {
            return DrsResponse.fail("error", "not after order end date!");
        }
        if (tradeOfferOrder.getPaymentEndTime().before(new Date())) {
            return DrsResponse.fail("error", "current time is greater than paymentDeadline!");
        }
        if (tradeOfferOrder.getQuantity().compareTo(BigDecimal.ZERO) <= 0) {
            return DrsResponse.fail("error", "amount is illegal!");
        }
        BigDecimal quantity = absBidRequest.getBody().getQuantity();
        if (quantity.compareTo(BigDecimal.ZERO) <= 0) {
            return DrsResponse.fail("error", "payAmount is illegal!");
        }
        if (tradeOfferOrder.getQuantity().compareTo(quantity) < 0) {
            return DrsResponse.fail("error", "remain is not enough!");
        }
        if (Objects.nonNull(tradeOfferOrder.getLotSize())
                && BigDecimal.ZERO.compareTo(tradeOfferOrder.getLotSize()) != 0) {
            BigDecimal number = quantity.divide(tradeOfferOrder.getLotSize(), 4, BigDecimal.ROUND_HALF_UP);
            if (new BigDecimal(number.intValue()).compareTo(number) != 0) {
                return DrsResponse.fail("error", "amount not an integer multiple!");
            }
        }

        //Setup Message
        DrsSmtMessage message = buildBaseMessage(request);
        message.getHeader().setSmtCode("smtt-abs-subscription-bid-2-v1");
        message.getHeader().setSessionId(sessionId);
        //Message Body
        DrsSmtMessage.SmtBody body = JSON.parseObject(JSON.toJSONString(absBidRequest.getBody()), DrsSmtMessage.SmtBody.class);
        message.setBody(body);
        //Send API request
        DrsResponse<DrsResponse.SmtResult> drsResponse = doSend(message);
        if (!drsResponse.success()) {
            return drsResponse;
        }
        //Run business logic
        doBusiness(message, drsResponse, tradeOfferOrder.getAssetId());
        return drsResponse;
    }

    private void doBusiness(DrsSmtMessage message,
                            DrsResponse<DrsResponse.SmtResult> smtResultDrsResponse,
                            String assetId) {
        //save to database
        TradeBidOrder tradeBidOrder = new TradeBidOrder();
        tradeBidOrder.setSmtCode(message.getHeader().getSmtCode());
        tradeBidOrder.setAssetId(assetId);
        tradeBidOrder.setOfferSessionId(message.getHeader().getSessionId());
        tradeBidOrder.setBidAddress(message.getHeader().getMessageSenderAddress());
        tradeBidOrder.setQuantity(message.getBody().getBigDecimal("quantity"));
        tradeBidOrder.setStatus(StatusEnum.ChainStatus.PROCESSING.getCode());
        tradeBidOrder.setBizStatus(StatusEnum.BidBizStatus.SUBSCRIBED.getCode());
        tradeBidOrder.setUuid(message.getHeader().getUuid());
        tradeBidOrder.setIdentifierId(message.getHeader().getIdentifierId());
        tradeBidOrder.setMessageId(smtResultDrsResponse.getData().getMessageId());
        tradeBidOrder.setSessionId(smtResultDrsResponse.getData().getSessionId());
        tradeBidOrder.setCreateAt(new Date());
        tradeBidOrder.setUpdateAt(new Date());
        tradeBidOrderDao.save(tradeBidOrder);
    }

}
