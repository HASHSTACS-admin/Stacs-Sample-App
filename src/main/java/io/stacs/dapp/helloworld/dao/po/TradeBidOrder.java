package io.stacs.dapp.helloworld.dao.po;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author HuangShengli
 * @ClassName TradeBidOrder
 * @Description 交易类Bid订单表
 * @since 2020/9/19
 */
@Data
@Entity
@Table(name = "trade_bid_order", indexes = {@Index(columnList = "uuid", unique = true)})
@org.hibernate.annotations.Table(appliesTo = "trade_bid_order", comment = "交易类Bid订单表")
public class TradeBidOrder {
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
    @Column(nullable = false, columnDefinition = "varchar(32) comment '认购资产代码'")
    private String assetId;
    @Column(nullable = false, columnDefinition = "varchar(64) comment '售卖单sessionId'")
    private String offerSessionId;

    @Column(nullable = false, columnDefinition = "varchar(40) comment '购买人地址'")
    private String bidAddress;
    @Column(nullable = false, columnDefinition = "decimal(32,8) comment '购买数量'")
    private BigDecimal quantity;

    @Column(nullable = true, columnDefinition = "decimal(32,8) comment '支付token数量'")
    private BigDecimal paymentAmount;

    @Column(nullable = true, columnDefinition = "varchar(32) comment '支付的token ID'")
    private String paymentCurrencyId;

    @Column(nullable = true, columnDefinition = "varchar(128) comment '额外信息'")
    private String paymentInfo;
    @Column(nullable = false, columnDefinition = "tinyint comment '状态:0-PROCESSING,1-SUCCESS,2-FAIL'")
    private Byte status;
    @Column(nullable = false, columnDefinition = "tinyint comment '状态:0-已认购, 1-已支付, 2-卖方已确认，3-已退款, 4-争议中, 5-已撤销,6-已结算'")
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
