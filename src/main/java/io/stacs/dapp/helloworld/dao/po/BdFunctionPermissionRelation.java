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
 * @ClassName BdFunctionPermissionRelation
 * @Description BD and Permission Relation Entity
 * @since 2020/9/18
 */
@Data
@Entity
@Table(name = "bd_function_permission_relation", indexes = {@Index(columnList = "bdId,functionName", unique = true)})
@org.hibernate.annotations.Table(appliesTo = "bd_function_permission_relation", comment = "BD and Permission Entity relation")
public class BdFunctionPermissionRelation {
    /**
     * unique id
     * auto-increment
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, columnDefinition = "bigint(20)  comment 'primary unique ID'")
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(128) comment 'BD id, sent back by DRS callback API'")
    private String bdId;
    @Column(nullable = false, columnDefinition = "varchar(128) comment 'permission id, sent back by DRS callback API'")
    private String permissionId;
    @Column(nullable = false, columnDefinition = "varchar(128) comment 'SMT format function name'")
    private String functionName;
    @Column(nullable = false, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment 'creation timestamp'")
    private Date createAt;
    @Column(nullable = false, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment 'update timestamp'")
    private Date updateAt;
}
