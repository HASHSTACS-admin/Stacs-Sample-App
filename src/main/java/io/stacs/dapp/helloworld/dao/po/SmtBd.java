package io.stacs.dapp.helloworld.dao.po;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author HuangShengli
 * @ClassName SmtBd
 * @Description BD Entity
 * @since 2020/9/19
 */
@Data
@Entity
@Table(name = "smt_bd", indexes = {@Index(columnList = "uuid", unique = true)})
@org.hibernate.annotations.Table(appliesTo = "smt_bd", comment = "BD Entity")
public class SmtBd {
    /**
     * primary id
     * auto-increment
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, columnDefinition = "bigint(20)  comment 'primary unique ID'")
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(128) comment 'SMT format codeE'")
    private String smtCode;
    @Column(nullable = true, columnDefinition = "varchar(128) comment 'BD id,sent back by DRS callback API'")
    private String bdId;
    @Column(nullable = false, columnDefinition = "tinyint comment 'Status:0-PROCESSING,1-SUCCESS,2-FAIL'")
    private Byte status;
    @Column(nullable = false, columnDefinition = "varchar(128) comment 'uuid'")
    private String uuid;
    @Column(nullable = false, columnDefinition = "varchar(20) comment 'merchant id'")
    private String identifierId;
    @Column(nullable = true, columnDefinition = "varchar(255) comment 'message id, sent back by DRS callback API'")
    private String messageId;
    @Column(nullable = true, columnDefinition = "varchar(255) comment 'session id, sent back by DRS callback API'")
    private String sessionId;
    @Column(nullable = false, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP comment 'creation timestamp'")
    private Date createAt;
    @Column(nullable = false, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment 'update timestamp'")
    private Date updateAt;
}
