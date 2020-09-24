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
import io.stacs.dapp.helloworld.vo.demo.AbsCancelRequest;
import io.stacs.dapp.helloworld.vo.demo.DemoBaseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * 买方主动撤单ABS报文服务
 *
 * @author Su Wenbo
 * @since 2020/9/21
 */
@Service("smtt-abs-subscription-cancel-1-v1")
public class AbsCancelDemoServiceImpl extends AbstractSendSmtMessageService implements SmtDemoService {

    @Autowired
    private TradeBidOrderDao tradeBidOrderDao;

    @Autowired
    private TradeOfferOrderDao tradeOfferOrderDao;

    /**
     * 买方主动撤单ABS
     *
     * @param request permission request
     * @return drs response
     */
    @Override
    public DrsResponse doDemo(DemoBaseRequest request) {
        AbsCancelRequest absCancelRequest = (AbsCancelRequest) request;
        //查询bid单子
        String messageId = absCancelRequest.getBody().getMessageId();
        TradeBidOrder tradeBidOrder = tradeBidOrderDao.findByMessageId(messageId);
        //查询offer单
        String sessionId = request.getHeader().getSessionId();
        TradeOfferOrder tradeOfferOrder = tradeOfferOrderDao.findBySessionId(sessionId);
        //校验
        if (Objects.isNull(tradeBidOrder)) {
            return DrsResponse.fail("error", "The order to be canceled does not exist!");
        }
        if (!(StatusEnum.BidBizStatus.SUBSCRIBED.getCode().equals(tradeBidOrder.getBizStatus()) &&
                StatusEnum.OfferBizStatus.OFFER.getCode().equals(tradeOfferOrder.getBizStatus()))) {
            return DrsResponse.fail("error", "current state does not support this operation!");
        }

        //组装报文数据
        DrsSmtMessage message = buildBaseMessage(request);
        message.getHeader().setSmtCode("smtt-abs-subscription-cancel-1-v1");
        message.getHeader().setSessionId(request.getHeader().getSessionId());
        //报文体
        DrsSmtMessage.SmtBody body = JSON.parseObject(JSON.toJSONString(absCancelRequest.getBody()), DrsSmtMessage.SmtBody.class);
        message.setBody(body);
        //发送请求
        DrsResponse<DrsResponse.SmtResult> drsResponse = doSend(message);
        if (!drsResponse.success()) {
            return drsResponse;
        }
        //做额外逻辑
        doBusiness(tradeBidOrder);
        return drsResponse;
    }

    private void doBusiness(TradeBidOrder tradeBidOrder) {
        //更新状态，存数据库
        tradeBidOrder.setStatus(StatusEnum.ChainStatus.PROCESSING.getCode());
        tradeBidOrder.setUpdateAt(new Date());
        tradeBidOrderDao.save(tradeBidOrder);
    }

}
