package io.stacs.dapp.helloworld.dao.po;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author HuangShengli
 * @ClassName TradeOfferOrder
 * @Description Trade Offer Order
 * @since 2020/9/19
 */
@Data
@Entity
@Table(name = "trade_offer_order", indexes = {@Index(columnList = "uuid", unique = true)})
@org.hibernate.annotations.Table(appliesTo = "trade_offer_order", comment = "Trade Offer Order")
public class TradeOfferOrder {
    /**
     * primary id
     * auto increment
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, columnDefinition = "bigint(20)  comment 'primary id'")
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(128) comment 'SMT Format code'")
    private String smtCode;
    @Column(nullable = false, columnDefinition = "varchar(32) comment 'asset id'")
    private String assetId;
    @Column(nullable = false, columnDefinition = "varchar(40) comment 'offer address'")
    private String offerAddress;
    @Column(nullable = false, columnDefinition = "decimal(32,8) comment 'quantity'")
    private BigDecimal quantity;
    @Column(nullable = false, columnDefinition = "decimal(32,8) comment 'unit price'")
    private BigDecimal unitPrice;
    @Column(nullable = true, columnDefinition = "varchar(32) comment 'payment currency (token id), digital currency has to be issued prior to this order'")
    private String paymentCurrencyId;
    @Column(nullable = true, columnDefinition = "char(3) comment 'payment currency'")
    private String paymentCurrency;
    @Column(nullable = true, columnDefinition = "varchar(40) comment 'counterparty address'")
    private String countPartyAddress;
    @Column(nullable = false, columnDefinition = "decimal(32,8) comment 'max quantity that can be purchased by a single address, must be >= minSizePerTrade and <= quantity sold'")
    private BigDecimal maxSizePerAddress;
    @Column(nullable = false, columnDefinition = "decimal(32,8) comment 'min size per trade'")
    private BigDecimal minSizePerTrade;
    @Column(nullable = true, columnDefinition = "decimal(32,8) comment 'lot size'")
    private BigDecimal lotSize;
    @Column(nullable = false, columnDefinition = "datetime comment 'order start time'")
    private Date orderStartTime;
    @Column(nullable = false, columnDefinition = "datetime comment 'order end time, in UNIX timestamp format, must be >= orderStartTime'")
    private Date orderEndTime;
    @Column(nullable = false, columnDefinition = "datetime comment 'payment end time, in UNIX timestamp format, must >= orderEndTime'")
    private Date paymentEndTime;
    @Column(nullable = true, columnDefinition = "datetime comment 'if this is blank, settlement will occur immediately after conditions are met. Else, 1 party, usually the buyer, is required to initiate settlement after the settlement time and transaction conditions are fulfilled. This must be >= orderEndTime'")
    private Date settleTime;
    @Column(nullable = true, columnDefinition = "varchar(128) comment 'additional information for subscription'")
    private String subscriptionInfo;
    @Column(nullable = true, columnDefinition = "varchar(40) comment 'contract address, 1-1 relation mapping to the asset id'")
    private String contractAddress;
    @Column(nullable = false, columnDefinition = "tinyint comment 'status:0-PROCESSING,1-SUCCESS,2-FAIL'")
    private Byte status;
    @Column(nullable = false, columnDefinition = "tinyint comment 'status:0-release,1-refund,2-settlement,3-end'")
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
