package io.stacs.dapp.helloworld.dao.po;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author HuangShengli
 * @ClassName SmtPermission
 * @Description permission entity
 * @since 2020/9/11
 */
@Data
@Entity
@Table(name = "smt_permission", indexes = {@Index(columnList = "uuid", unique = true)})
@org.hibernate.annotations.Table(appliesTo = "smt_permission", comment = "permission entity")
public class SmtPermission {
    /**
     * primary id
     * auto increment
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, columnDefinition = "bigint(20)  comment 'primary ID'")
    private Long id;
    @Column(columnDefinition = "varchar(128) comment 'permission id,sent back by DRS callback API'")
    private String permissionId;

    @Column(nullable = false, columnDefinition = "varchar(128) comment 'SMT format code'")
    private String smtCode;
    @Column(nullable = false, columnDefinition = "tinyint comment 'Status:0-PROCESSING,1-SUCCESS,2-FAIL'")
    private Byte status;
    @Column(nullable = false, columnDefinition = "varchar(128) comment 'uuid'")
    private String uuid;
    @Column(nullable = false, columnDefinition = "varchar(20) comment 'merchant id'")
    private String identifierId;
    @Column(nullable = true, columnDefinition = "varchar(255) comment 'message id, sent back by DRS callback API'")
    private String messageId;
    @Column(nullable = true, columnDefinition = "varchar(255) comment 'message id, sent back by DRS callback API'")
    private String sessionId;
    @Column(nullable = false, columnDefinition = "longtext comment 'address that can modify this permission, input argument takes the form of a standard address such as [40!c,...40!c] in json format'")
    private String modifierAddress;
    @Column(nullable = false, columnDefinition = "longtext comment 'address that belongs to the permission, input argument takes the form of a standard address such as [40!c,...40!c] in json format'")
    private String authorizedAddress;
    @Column(nullable = false, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP comment 'creation timestamp'")
    private Date createAt;
    @Column(nullable = false, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment 'update timestamp'")
    private Date updateAt;
}
