package io.stacs.dapp.helloworld.dao.po;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author HuangShengli
 * @ClassName SmtMessage
 * @Description 报文记录实体
 * @since 2020/9/11
 */
@Data
@Entity
@Table(name = "smt_message",indexes = {@Index(columnList = "uuid",unique = true)})
@org.hibernate.annotations.Table(appliesTo = "smt_message",comment="报文记录表")
public class SmtMessage {
    /**
     * 主键id
     * 自增长
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(nullable = false,columnDefinition = "bigint(20)  comment '主键ID'")
    private Long id ;

    @Column(nullable = false,columnDefinition = "varchar(128) comment '报文CODE'")
    private String smtCode ;
    @Column(nullable = false,columnDefinition = "varchar(128) comment 'uuid'")
    private String uuid ;
    @Column(nullable = false,columnDefinition = "varchar(20) comment '商户号'")
    private String identifierId ;
    @Column(nullable = true,columnDefinition = "varchar(255) comment 'message id，由DRS返回'")
    private String messageId ;
    @Column(nullable = true,columnDefinition = "varchar(255) comment 'session id，由DRS返回'")
    private String sessionId ;
    @Column(nullable = false,columnDefinition = "varchar(64) comment '交易签名地址'")
    private String messageSenderAddress ;
    @Column(nullable = false,columnDefinition = "longtext comment '报文体数据，json格式存储'")
    private String body ;
    @Column(nullable = true,columnDefinition = "longtext comment '链上交易信息'")
    private String blockchainTransaction;
    @Column(nullable = true,columnDefinition = "varchar(128) comment '附加信息，暂时没用'")
    private String extraData;
    @Column(nullable = true,columnDefinition = "varchar(128) comment '备注信息'")
    private String authenticationTrailer;
    @Column(nullable = true,columnDefinition = "varchar(255) comment 'DRS错误信息'")
    private String responseMessage ;
    @Column(nullable = true,columnDefinition = "varchar(64) comment 'DRS错误码,成功:success'")
    private String responseCode ;
    @Column(nullable = true,columnDefinition = "varchar(10) comment 'DRS返回的版本号'")
    private String version ;
    @Column(nullable = true, columnDefinition = "longtext comment '该报文发生的交易ID，可能多笔，json格式'")
    private String txs;
    @Column(nullable = false, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP comment '创建时间'")
    private Date createAt;
    @Column(nullable = false, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间'")
    private Date updateAt;
}
