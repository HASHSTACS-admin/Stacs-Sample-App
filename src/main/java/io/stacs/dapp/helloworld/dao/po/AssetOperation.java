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
 * Entity Object that handles asset operations like freezing for redemption, snapshot
 *
 * @author Su Wenbo
 * @since 2020/9/23
 */
@Data
@Entity
@Table(name = "asset_operation", indexes = {@Index(columnList = "operationId", unique = true), @Index(columnList = "uuid", unique = true)})
@org.hibernate.annotations.Table(appliesTo = "asset_operation", comment = "Asset Operations, freezing for redemption, snapshot")
public class AssetOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, columnDefinition = "bigint(20) comment 'primary ID'")
    private Long id;

    @Column(nullable = false, columnDefinition = "bigint(20) comment 'asset operation ID'")
    private Long operationId;

    @Column(nullable = false, columnDefinition = "tinyint comment 'type: 0-snapshot, 1-freeze'")
    private Byte type;

    @Column(nullable = false, columnDefinition = "varchar(128) comment 'SMT Format CODE'")
    private String smtCode;

    @Column(nullable = true, columnDefinition = "varchar(32) comment 'Freeze Type-AssetId'")
    private String assetId;

    @Column(nullable = true, columnDefinition = "varchar(64) comment 'Snapshott Type=block height'")
    private String blockHeight;

    @Column(nullable = true, columnDefinition = "varchar(128) comment 'description'")
    private String description;

    @Column(nullable = false, columnDefinition = "varchar(128) comment 'uuid'")
    private String uuid;

    @Column(nullable = false, columnDefinition = "varchar(255) comment 'message id, sent back by DRS Callback API'")
    private String messageId;

    @Column(nullable = false, columnDefinition = "varchar(255) comment 'session id,sent back by DRS Callback API'")
    private String sessionId;

    @Column(nullable = false, columnDefinition = "tinyint comment 'Chain Status: 0-PROCESSING, 1-SUCCESS, 2-FAIL'")
    private Byte status;

    @Column(nullable = false, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP comment 'creation timestamp'")
    private Date createAt;

    @Column(nullable = false, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment 'update timestamp'")
    private Date updateAt;

}
