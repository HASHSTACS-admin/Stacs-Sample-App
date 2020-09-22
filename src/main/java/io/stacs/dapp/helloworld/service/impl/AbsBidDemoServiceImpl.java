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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 买方出价认购ABS报文服务
 *
 * @author Su Wenbo
 * @since 2020/9/21
 */
@RequiredArgsConstructor
@Service("smtt-abs-subscription-bid-2-v1")
public class AbsBidDemoServiceImpl extends AbstractSendSmtMessageService implements SmtDemoService {

    private final TradeOfferOrderDao tradeOfferOrderDao;

    private final TradeBidOrderDao tradeBidOrderDao;

    /**
     * 买方出价认购ABS
     *
     * @param request permission request
     * @return drs response
     */
    @Override
    public DrsResponse doDemo(DemoBaseRequest request) {
        AbsBidRequest absBidRequest = (AbsBidRequest) request;
        //组装报文数据
        DrsSmtMessage message = buildBaseMessage(request);
        message.getHeader().setSmtCode("smtt-abs-subscription-bid-2-v1");
        message.getHeader().setSessionId(request.getHeader().getSessionId());
        //报文体
        DrsSmtMessage.SmtBody body = JSON.parseObject(JSON.toJSONString(absBidRequest.getBody()), DrsSmtMessage.SmtBody.class);
        message.setBody(body);
        //发送请求
        DrsResponse<DrsResponse.SmtResult> drsResponse = doSend(message);
        if (!drsResponse.success()) {
            return drsResponse;
        }
        //做额外逻辑
        doBusiness(message, drsResponse);
        return drsResponse;
    }

    private void doBusiness(DrsSmtMessage message,
                            DrsResponse<DrsResponse.SmtResult> smtResultDrsResponse) {
        //查询offer单
        TradeOfferOrder tradeOfferOrder = tradeOfferOrderDao.findBySessionId(message.getHeader().getSessionId());

        //组装trade bid order，存数据库
        TradeBidOrder tradeBidOrder = new TradeBidOrder();
        tradeBidOrder.setSmtCode(message.getHeader().getSmtCode());
        tradeBidOrder.setAssetId(tradeOfferOrder.getAssetId());
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
