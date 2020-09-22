package io.stacs.dapp.helloworld.dao.po;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author HuangShengli
 * @ClassName AssetAbs
 * @Description BD方法和权限的关系表
 * @since 2020/9/19
 */
@Data
@Entity
@Table(name = "asset_abs", indexes = {@Index(columnList = "uuid", unique = true)})
@org.hibernate.annotations.Table(appliesTo = "asset_abs", comment = "abs表")
public class AssetAbs {
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
    @Column(nullable = false, columnDefinition = "varchar(32) comment '资产代码,全局唯一'")
    private String assetId;
    @Column(nullable = false, columnDefinition = "varchar(64) comment '资产名称'")
    private String assetName;
    @Column(columnDefinition = "varchar(16) comment '债券子类别'")
    private String absType;
    @Column(columnDefinition = "varchar(32) comment '发行人名称，即该债券的所有人'")
    private String issuerName;
    @Column(columnDefinition = "varchar(192) comment '唯一识别码，同一证券在不同国家或体系会有不同的识别码，用以标识该证券'")
    private String uniqueIdentifierCode;
    @Column(nullable = false, columnDefinition = "varchar(40) comment '资产发行后，资产都会在这个地址，相当于资产发行后的首个接收地址'")
    private String ownerAddress;
    @Column(nullable = false, columnDefinition = "decimal(32,8) comment '本次发行数量'")
    private BigDecimal quantity;
    @Column(nullable = true, columnDefinition = "decimal(32,8) comment '面值'")
    private BigDecimal parValuePerToken;
    @Column(nullable = true, columnDefinition = "varchar(32) comment '结算币种，用来认购证券的币种，仅作展示'")
    private String settlementCurrency;
    @Column(nullable = true, columnDefinition = "int(3) comment '日计数约定，一种用来确定两个券息日期之间天数的共识约定,Eg.360/365'")
    private Integer dayCountConvention;
    @Column(nullable = true, columnDefinition = "varchar(16) comment '付息频率,QUARTER:Quarterly, HALF_YEAR:Semi-annually, YEAR:Annually'")
    private String couponFrequency;
    @Column(nullable = true, columnDefinition = "datetime comment '第一次结算日期'")
    private Date firstSettlementDate;
    @Column(nullable = true, columnDefinition = "longtext comment '约定赎回日期,[10!n,...,10!n]数组格式'")
    private String callDate;
    @Column(nullable = false, columnDefinition = "varchar(32) comment '结息和回购所使用的token ID'")
    private String disbursementTokenId;
    @Column(nullable = true, columnDefinition = "longtext comment 'KYC:个人类型KYC的黑名单'")
    private String individualProhibited;
    @Column(nullable = true, columnDefinition = "longtext comment 'KYC:个人类型KYC的白名单'")
    private String individualPermitted;
    @Column(nullable = true, columnDefinition = "longtext comment 'KYC:机构类型KYC的黑名单'")
    private String institutionalProhibited;
    @Column(nullable = true, columnDefinition = "longtext comment 'KYC:机构类型KYC的白名单'")
    private String institutionalPermitted;
    @Column(nullable = true, columnDefinition = "varchar(40) comment '本次发行的合约地址，合约地址与assets ID一一对应'")
    private String contractAddress;
    @Column(nullable = false, columnDefinition = "tinyint comment '状态:0-PROCESSING,1-SUCCESS,2-FAIL'")
    private Byte status;
    @Column(nullable = false, columnDefinition = "tinyint comment '状态:0-正常,1-资产冻结,2-回购冻结,3-回购'")
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
