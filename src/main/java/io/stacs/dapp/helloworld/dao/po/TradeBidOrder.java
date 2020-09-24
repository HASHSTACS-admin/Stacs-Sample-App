package io.stacs.dapp.helloworld.dao.po;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author HuangShengli
 * @ClassName TradeBidOrder
 * @Description Trade Bid Order
 * @since 2020/9/19
 */
@Data
@Entity
@Table(name = "trade_bid_order", indexes = {@Index(columnList = "uuid", unique = true)})
@org.hibernate.annotations.Table(appliesTo = "trade_bid_order", comment = "trade bid order")
public class TradeBidOrder {
    /**
     * primary id
     * auto increment
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, columnDefinition = "bigint(20)  comment 'primary id'")
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(128) comment 'SMT format code'")
    private String smtCode;
    @Column(nullable = false, columnDefinition = "varchar(32) comment 'asset id'")
    private String assetId;
    @Column(nullable = false, columnDefinition = "varchar(64) comment 'offer session id'")
    private String offerSessionId;

    @Column(nullable = false, columnDefinition = "varchar(40) comment 'bid address'")
    private String bidAddress;
    @Column(nullable = false, columnDefinition = "decimal(32,8) comment 'quantity'")
    private BigDecimal quantity;

    @Column(nullable = true, columnDefinition = "decimal(32,8) comment 'payment amount'")
    private BigDecimal paymentAmount;

    @Column(nullable = true, columnDefinition = "varchar(32) comment 'payment currency (token id)'")
    private String paymentCurrencyId;

    @Column(nullable = true, columnDefinition = "varchar(128) comment 'payment info'")
    private String paymentInfo;
    @Column(nullable = false, columnDefinition = "tinyint comment 'state:0-PROCESSING,1-SUCCESS,2-FAIL'")
    private Byte status;
    @Column(nullable = false, columnDefinition = "tinyint comment 'state:0-purchased, 1-paid, 2-seller confirmed，3-refunded, 4-dispute in progress, 5-rescinded,6-settled'")
    private Byte bizStatus;
    @Column(nullable = false, columnDefinition = "varchar(128) comment 'uuid'")
    private String uuid;
    @Column(nullable = false, columnDefinition = "varchar(20) comment 'merchant id'")
    private String identifierId;
    @Column(nullable = true, columnDefinition = "varchar(255) comment 'message id, sent via DRS callback API'")
    private String messageId;
    @Column(nullable = true, columnDefinition = "varchar(255) comment 'session id, sent via DRS callback API'")
    private String sessionId;
    @Column(nullable = true, columnDefinition = "varchar(255) comment 'remark'")
    private String remark;
    @Column(nullable = false, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP comment 'creation timestamp'")
    private Date createAt;
    @Column(nullable = false, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment 'update timestamp'")
    private Date updateAt;
}
