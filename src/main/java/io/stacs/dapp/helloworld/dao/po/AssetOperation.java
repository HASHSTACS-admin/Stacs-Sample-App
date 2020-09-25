package io.stacs.dapp.helloworld.dao.po;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.Date;

/**
 * 对资产操作，打快照或者冻结等，记录是否完成
 *
 * @author Su Wenbo
 * @since 2020/9/23
 */
@Data
@Entity
@Table(name = "asset_operation", indexes = {@Index(columnList = "operationId", unique = true), @Index(columnList = "uuid", unique = true)})
@org.hibernate.annotations.Table(appliesTo = "asset_operation", comment = "资产操作如快照或冻结操作记录表")
public class AssetOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, columnDefinition = "bigint(20) comment '主键ID'")
    private Long id;

    @Column(nullable = false, columnDefinition = "bigint(20) comment '资产操作ID'")
    private Long operationId;

    @Column(nullable = false, columnDefinition = "tinyint comment '类型: 0-快照, 1-冻结'")
    private Byte type;

    @Column(nullable = false, columnDefinition = "varchar(128) comment '报文CODE'")
    private String smtCode;

    @Column(nullable = true, columnDefinition = "varchar(32) comment '冻结类型-资产代码'")
    private String assetId;

    @Column(nullable = true, columnDefinition = "varchar(64) comment '快照类型-区块高度'")
    private String blockHeight;

    @Column(nullable = true, columnDefinition = "varchar(128) comment '附带信息'")
    private String description;

    @Column(nullable = false, columnDefinition = "varchar(128) comment 'uuid'")
    private String uuid;

    @Column(nullable = false, columnDefinition = "varchar(255) comment 'message id，由DRS返回'")
    private String messageId;

    @Column(nullable = false, columnDefinition = "varchar(255) comment 'session id，由DRS返回'")
    private String sessionId;

    @Column(nullable = false, columnDefinition = "tinyint comment '上链状态: 0-PROCESSING, 1-SUCCESS, 2-FAIL'")
    private Byte status;

    @Column(nullable = false, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP comment '创建时间'")
    private Date createAt;

    @Column(nullable = false, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间'")
    private Date updateAt;

}
