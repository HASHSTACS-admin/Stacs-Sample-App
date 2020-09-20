package io.stacs.dapp.helloworld.dao.po;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author HuangShengli
 * @ClassName SmtBd
 * @Description bd实体
 * @since 2020/9/19
 */
@Data
@Entity
@Table(name = "smt_bd", indexes = {@Index(columnList = "uuid", unique = true)})
@org.hibernate.annotations.Table(appliesTo = "smt_bd", comment = "BD记录表")
public class SmtBd {
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
    @Column(nullable = true, columnDefinition = "varchar(128) comment 'BD id,发行BD成功后DRS返回'")
    private String bdId;
    @Column(nullable = false, columnDefinition = "varchar(128) comment '状态:0-PROCESSING,1-SUCCESS,2-FAIL'")
    private Byte status;
    @Column(nullable = false, columnDefinition = "varchar(128) comment 'uuid'")
    private String uuid;
    @Column(nullable = false, columnDefinition = "varchar(20) comment '商户号'")
    private String identifierId;
    @Column(nullable = true, columnDefinition = "varchar(255) comment 'message id，由DRS返回'")
    private String messageId;
    @Column(nullable = true, columnDefinition = "varchar(255) comment 'session id，由DRS返回'")
    private String sessionId;
    @Column(nullable = false, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP comment '创建时间'")
    private Date createAt;
    @Column(nullable = false, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间'")
    private Date updateAt;
}
