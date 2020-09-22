package io.stacs.dapp.helloworld.dao.po;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author HuangShengli
 * @ClassName AssetAbs
 * @Description BD and Permission Relations for Asset Backed Securities
 * @since 2020/9/19
 */
@Data
@Entity
@Table(name = "asset_abs", indexes = {@Index(columnList = "uuid", unique = true)})
@org.hibernate.annotations.Table(appliesTo = "asset_abs", comment = "abs")
public class AssetAbs {
    /**
     * unique Id
     * auto-increment
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, columnDefinition = "bigint(20)  comment 'uniqueId'")
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(128) comment 'SMT code'")
    private String smtCode;
    @Column(nullable = false, columnDefinition = "varchar(32) comment 'unique asset id'")
    private String assetId;
    @Column(nullable = false, columnDefinition = "varchar(64) comment 'asset name'")
    private String assetName;
    @Column(columnDefinition = "varchar(16) comment 'ABS type'")
    private String absType;
    @Column(columnDefinition = "varchar(32) comment 'issuer name'")
    private String issuerName;
    @Column(columnDefinition = "varchar(192) comment 'unique id for the ABS'")
    private String uniqueIdentifierCode;
    @Column(nullable = false, columnDefinition = "varchar(40) comment 'address for which assets will be held after issuance'")
    private String ownerAddress;
    @Column(nullable = false, columnDefinition = "decimal(32,8) comment 'quantity of securities'")
    private BigDecimal quantity;
    @Column(nullable = true, columnDefinition = "decimal(32,8) comment 'par value per token'")
    private BigDecimal parValuePerToken;
    @Column(nullable = true, columnDefinition = "varchar(32) comment 'currency for settlement'")
    private String settlementCurrency;
    @Column(nullable = true, columnDefinition = "int(3) comment 'day count convention for calculating interest, for example 360 or 365'")
    private Integer dayCountConvention;
    @Column(nullable = true, columnDefinition = "varchar(16) comment 'coupon payment frequency, supported types include QUARTER:Quarterly, HALF_YEAR:semi-annually and YEAR:annually'")
    private String couponFrequency;
    @Column(nullable = true, columnDefinition = "datetime comment 'first settlement date'")
    private Date firstSettlementDate;
    @Column(nullable = true, columnDefinition = "longtext comment 'redemption date, in the format of [10!n,...,10!n]'")
    private String callDate;
    @Column(nullable = false, columnDefinition = "varchar(32) comment 'token id used for coupon payment and redemption'")
    private String disbursementTokenId;
    @Column(nullable = true, columnDefinition = "longtext comment 'KYC: Individual Type, prohibited KYC profiles'")
    private String individualProhibited;
    @Column(nullable = true, columnDefinition = "longtext comment 'KYC: Individual Type, permitted KYC profiles'")
    private String individualPermitted;
    @Column(nullable = true, columnDefinition = "longtext comment 'KYC: Institution Type, prohibited KYC profiles'")
    private String institutionalProhibited;
    @Column(nullable = true, columnDefinition = "longtext comment 'KYC: Institution Type, permitted KYC profiles'")
    private String institutionalPermitted;
    @Column(nullable = true, columnDefinition = "varchar(40) comment 'contract address that corresponds to a 1-1 mapping with the asset id'")
    private String contractAddress;
    @Column(nullable = false, columnDefinition = "tinyint comment 'Status: 0-PROCESSING, 1-SUCCESS, 2-FAIL'")
    private Byte status;
    @Column(nullable = false, columnDefinition = "tinyint comment 'Status: 0-Normal, 1-Asset Frozen, 2-Asset Redemption Disabled,3-Redeemed'")
    private Byte bizStatus;
    @Column(nullable = false, columnDefinition = "varchar(128) comment 'uuid'")
    private String uuid;
    @Column(nullable = false, columnDefinition = "varchar(20) comment 'merchantId'")
    private String identifierId;
    @Column(nullable = true, columnDefinition = "varchar(255) comment 'message id sent by the DRS callback API'")
    private String messageId;
    @Column(nullable = true, columnDefinition = "varchar(255) comment 'session id sent by the DRS callback API'")
    private String sessionId;
    @Column(nullable = true, columnDefinition = "varchar(255) comment 'remark'")
    private String remark;
    @Column(nullable = false, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP comment 'creation timestamp'")
    private Date createAt;
    @Column(nullable = false, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment 'update timestamp'")
    private Date updateAt;
}
