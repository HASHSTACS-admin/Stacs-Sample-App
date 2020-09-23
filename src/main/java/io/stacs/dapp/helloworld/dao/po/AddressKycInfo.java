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
 * @author HuangShengli
 * @ClassName SmtBd
 * @Description bd实体
 * @since 2020/9/19
 */
@Data
@Entity
@Table(name = "address_kyc_info", indexes = {@Index(columnList = "address")})
@org.hibernate.annotations.Table(appliesTo = "address_kyc_info", comment = "地址的kyc信息记录表")
public class AddressKycInfo {

    /**
     * 主键id
     * 自增长
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, columnDefinition = "bigint(20)  comment '主键ID'")
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '地址'")
    private String address;

    @Column(nullable = false, columnDefinition = "tinyint comment '身份类型，0：个人 1：机构'")
    private Byte identityType;

    @Column(nullable = false, columnDefinition = "varchar(1024) comment 'kyc info, JSON字符串'")
    private String kycInfo;

    @Column(nullable = false, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP comment '创建时间'")
    private Date createAt;

    @Column(nullable = false, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间'")
    private Date updateAt;

}
