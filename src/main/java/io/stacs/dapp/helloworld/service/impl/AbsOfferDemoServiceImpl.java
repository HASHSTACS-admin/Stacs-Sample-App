package io.stacs.dapp.helloworld.service.impl;

import com.alibaba.fastjson.JSON;
import io.stacs.dapp.helloworld.constant.StatusEnum;
import io.stacs.dapp.helloworld.dao.TradeOfferOrderDao;
import io.stacs.dapp.helloworld.dao.po.TradeOfferOrder;
import io.stacs.dapp.helloworld.service.AbstractSendSmtMessageService;
import io.stacs.dapp.helloworld.service.SmtDemoService;
import io.stacs.dapp.helloworld.utils.CommonUtil;
import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.DrsSmtMessage;
import io.stacs.dapp.helloworld.vo.demo.AbsOfferRequest;
import io.stacs.dapp.helloworld.vo.demo.DemoBaseRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author Huang Shengli
 * @Description 发布ABS offer单
 * @date 2020-09-21
 */
@Service("smtt-abs-subscription-offer-3-v1")
public class AbsOfferDemoServiceImpl extends AbstractSendSmtMessageService implements SmtDemoService {

    @Autowired
    private TradeOfferOrderDao tradeOfferOrderDao;

    /**
     * abs的BD发布
     *
     * @param request
     * @return
     */
    @Transactional
    @Override
    public DrsResponse doDemo(DemoBaseRequest request) {
        AbsOfferRequest offerRequest = (AbsOfferRequest) request;
        //组装报文数据
        DrsSmtMessage message = buildBaseMessage(request);
        message.getHeader().setSmtCode("smtt-abs-subscription-offer-3-v1");
        //报文体
        DrsSmtMessage.SmtBody body = JSON.parseObject(JSON.toJSONString(offerRequest.getBody()), DrsSmtMessage.SmtBody.class);
        //特殊处理日期格式
        body.put("orderStartTime", CommonUtil.getSmtDateTime(offerRequest.getBody().getOrderStartTime()));
        body.put("orderEndTime", CommonUtil.getSmtDateTime(offerRequest.getBody().getOrderEndTime()));
        body.put("paymentEndTime", CommonUtil.getSmtDateTime(offerRequest.getBody().getPaymentEndTime()));

        if (null != offerRequest.getBody().getSettleTime()) {
            body.put("settleTime", CommonUtil.getSmtDateTime(offerRequest.getBody().getSettleTime()));
        }

        message.setBody(body);
        //发起DRS请求
        DrsResponse<DrsResponse.SmtResult> drsResponse = doSend(message);
        if (!drsResponse.success()) {
            return drsResponse;
        }
        //doBusiness
        doBusiness(drsResponse, offerRequest, message.getHeader());
        //请求DRS
        return drsResponse;
    }

    /**
     * 业务处理
     *
     * @param drsResponse
     * @param offerRequest
     */
    private void doBusiness(DrsResponse<DrsResponse.SmtResult> drsResponse, AbsOfferRequest offerRequest, DrsSmtMessage.SmtHeader header) {
        //保存到bd表
        TradeOfferOrder order = new TradeOfferOrder();
        //业务数据
        BeanUtils.copyProperties(offerRequest.getBody(), order);
        //通用信息
        order.setCreateAt(new Date());
        order.setUpdateAt(new Date());
        order.setIdentifierId(header.getIdentifierId());
        order.setMessageId(drsResponse.getData().getMessageId());
        order.setSessionId(drsResponse.getData().getSessionId());
        order.setSmtCode("smtt-abs-subscription-offer-3-v1");
        order.setStatus(StatusEnum.ChainStatus.PROCESSING.getCode());
        order.setBizStatus(StatusEnum.OfferBizStatus.OFFER.getCode());
        order.setUuid(header.getUuid());
        order.setOfferAddress(offerRequest.getHeader().getMessageSenderAddress());
        //save
        tradeOfferOrderDao.save(order);
    }
}
