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
 * @Description BD Entity
 * @since 2020/9/19
 */
@Data
@Entity
@Table(name = "address_kyc_info", indexes = {@Index(columnList = "address")})
@org.hibernate.annotations.Table(appliesTo = "address_kyc_info", comment = "address kyc information")
public class AddressKycInfo {

    /**
     * primary id
     * auto increment
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, columnDefinition = "bigint(20)  comment 'primary id'")
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(64) comment 'address'")
    private String address;

    @Column(nullable = false, columnDefinition = "tinyint comment 'Identity Type, 0: Individual 1: Institution'")
    private Byte identityType;

    @Column(nullable = false, columnDefinition = "varchar(1024) comment 'kyc info with JSON format'")
    private String kycInfo;

    @Column(nullable = false, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP comment 'creation timestamp'")
    private Date createAt;

    @Column(nullable = false, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment 'update timestamp'")
    private Date updateAt;

}
