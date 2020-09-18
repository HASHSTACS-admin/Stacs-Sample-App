package io.stacs.dapp.helloworld.dao.po;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author HuangShengli
 * @ClassName SmtMessage
 * @Description SMT Message
 * @since 2020/9/11
 */
@Data
@Entity
@Table(name = "smt_message",indexes = {@Index(columnList = "uuid",unique = true)})
@org.hibernate.annotations.Table(appliesTo = "smt_message",comment="SMT Message")
public class SmtMessage {
    /**
     * Auto Increment, Unique id
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(nullable = false,columnDefinition = "bigint(20)  comment 'Primary Key ID'")
    private Long id ;

    @Column(nullable = false,columnDefinition = "varchar(128) comment 'SMT CODE'")
    private String smtCode ;
    @Column(nullable = false,columnDefinition = "varchar(128) comment 'uuid'")
    private String uuid ;
    @Column(nullable = false,columnDefinition = "varchar(20) comment 'Merchant Id'")
    private String identifierId ;
    @Column(nullable = true,columnDefinition = "varchar(255) comment 'message id，DRS Response Parameter'")
    private String messageId ;
    @Column(nullable = true,columnDefinition = "varchar(255) comment 'session id，DRS Response Parameter'")
    private String sessionId ;
    @Column(nullable = false,columnDefinition = "varchar(64) comment 'Transaction Sender Address'")
    private String messageSenderAddress ;
    @Column(nullable = false,columnDefinition = "longtext comment 'SMT Parameters，json format'")
    private String body ;
    @Column(nullable = true,columnDefinition = "longtext comment 'Blockchain Transaction Id'")
    private String blockchainTransaction;
    @Column(nullable = true,columnDefinition = "varchar(128) comment 'additional, optional data payload'")
    private String extraData;
    @Column(nullable = true,columnDefinition = "varchar(128) comment 'remarks'")
    private String authenticationTrailer;
    @Column(nullable = true,columnDefinition = "varchar(255) comment 'Error Message from DRS'")
    private String responseMessage ;
    @Column(nullable = true,columnDefinition = "varchar(64) comment 'Error Code from DRS, if no errors will reflect:success'")
    private String responseCode ;
    @Column(nullable = true,columnDefinition = "varchar(10) comment 'Version from DRS'")
    private String version ;
    @Column(nullable = true, columnDefinition = "longtext comment 'may return multiple blockchain transaction IDs in json format'")
    private String txs;
    @Column(nullable = false, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP")
    private Date createAt;
    @Column(nullable = false, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date updateAt;
}
