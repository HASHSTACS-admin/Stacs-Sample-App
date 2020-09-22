package io.stacs.dapp.helloworld.dao.po;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author HuangShengli
 * @ClassName TradeOfferOrder
 * @Description 交易类Offer订单表
 * @since 2020/9/19
 */
@Data
@Entity
@Table(name = "trade_offer_order", indexes = {@Index(columnList = "uuid", unique = true)})
@org.hibernate.annotations.Table(appliesTo = "trade_offer_order", comment = "交易类Offer订单表")
public class TradeOfferOrder {
    /**
     * 主键id
     * 自增长
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, columnDefinition = "bigint(20)  comment '主键ID'")
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(128) comment '报文CODE'")
    private String smtCode;
    @Column(nullable = false, columnDefinition = "varchar(32) comment '资产代码'")
    private String assetId;
    @Column(nullable = false, columnDefinition = "varchar(40) comment '售卖人地址'")
    private String offerAddress;
    @Column(nullable = false, columnDefinition = "decimal(32,8) comment '本次售卖数量'")
    private BigDecimal quantity;
    @Column(nullable = false, columnDefinition = "decimal(32,8) comment '单价'")
    private BigDecimal unitPrice;
    @Column(nullable = true, columnDefinition = "varchar(32) comment '接受的付款token，必须是已发行的token'")
    private String paymentCurrencyId;
    @Column(nullable = true, columnDefinition = "char(3) comment '接受的付款法币'")
    private String paymentCurrency;
    @Column(nullable = true, columnDefinition = "varchar(40) comment '对手方地址，该地址会传入合约'")
    private String countPartyAddress;
    @Column(nullable = false, columnDefinition = "decimal(32,8) comment '单个地址能购买的总量，必须大于等于minSizePerTrad，小于等于卖出数量'")
    private BigDecimal maxSizePerAddress;
    @Column(nullable = false, columnDefinition = "decimal(32,8) comment '单次最低购买量'")
    private BigDecimal minSizePerTrade;
    @Column(nullable = true, columnDefinition = "decimal(32,8) comment '倍数，小于等于卖出数量，只能以此数字的整倍数进行购买'")
    private BigDecimal lotSize;
    @Column(nullable = false, columnDefinition = "datetime comment '买家可开始下单的时间'")
    private Date orderStartTime;
    @Column(nullable = false, columnDefinition = "datetime comment '买家下单截止时间，unix时间戳格式，必须大于等于orderStartTime'")
    private Date orderEndTime;
    @Column(nullable = false, columnDefinition = "datetime comment '买家付款截止时间，unix时间戳格式，必须大于等于orderEndTime'")
    private Date paymentEndTime;
    @Column(nullable = true, columnDefinition = "datetime comment '结算时间，不填写则在条件满足后立即自动结算，填写后则在结算时间到达以及交易条件满足后，需要一方（通常是买方）来发起结算，必须大于orderEndTime'")
    private Date settleTime;
    @Column(nullable = true, columnDefinition = "varchar(128) comment '发起认购时想要告知的额外信息'")
    private String subscriptionInfo;
    @Column(nullable = true, columnDefinition = "varchar(40) comment '本次发行的合约地址，合约地址与assets ID一一对应'")
    private String contractAddress;
    @Column(nullable = false, columnDefinition = "tinyint comment '状态:0-PROCESSING,1-SUCCESS,2-FAIL'")
    private Byte status;
    @Column(nullable = false, columnDefinition = "tinyint comment '状态:0-发布,1-退款,2-结算,3-结束'")
    private Byte bizStatus;
    @Column(nullable = false, columnDefinition = "varchar(128) comment 'uuid'")
    private String uuid;
    @Column(nullable = false, columnDefinition = "varchar(20) comment '商户号'")
    private String identifierId;
    @Column(nullable = true, columnDefinition = "varchar(255) comment 'message id，由DRS返回'")
    private String messageId;
    @Column(nullable = true, columnDefinition = "varchar(255) comment 'session id，由DRS返回'")
    private String sessionId;
    @Column(nullable = true, columnDefinition = "varchar(255) comment '备注信息'")
    private String remark;
    @Column(nullable = false, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP comment '创建时间'")
    private Date createAt;
    @Column(nullable = false, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间'")
    private Date updateAt;
}
