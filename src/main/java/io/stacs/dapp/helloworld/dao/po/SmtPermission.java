package io.stacs.dapp.helloworld.dao.po;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author HuangShengli
 * @ClassName SmtPermission
 * @Description permission实体
 * @since 2020/9/11
 */
@Data
@Entity
@Table(name = "smt_permission", indexes = {@Index(columnList = "uuid", unique = true)})
@org.hibernate.annotations.Table(appliesTo = "smt_permission", comment = "权限记录表")
public class SmtPermission {
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
    @Column(nullable = false, columnDefinition = "varchar(128) comment 'uuid'")
    private String uuid;
    @Column(nullable = false, columnDefinition = "varchar(20) comment '商户号'")
    private String identifierId;
    @Column(nullable = true, columnDefinition = "varchar(255) comment 'message id，由DRS返回'")
    private String messageId;
    @Column(nullable = true, columnDefinition = "varchar(255) comment 'session id，由DRS返回'")
    private String sessionId;
    @Column(nullable = false, columnDefinition = "longtext comment '对此permission拥有修改权限的地址，只能填写地址,[40!c,.,40!c]数组json格式字符串'")
    private String modifierAddress;
    @Column(nullable = false, columnDefinition = "longtext comment '拥有permission权限的地址，只能填写地址,[40!c,.,40!c]数组json格式字符串'")
    private String authorizedAddress;
    @Column(columnDefinition = "varchar(255) comment 'permission id,发布成功后返回'")
    private String permissionId;
    private Date createAt;
    @Column(nullable = false, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间'")
    private Date updateAt;
}
