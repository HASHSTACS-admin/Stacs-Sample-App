package io.stacs.dapp.helloworld.dao.po;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author HuangShengli
 * @ClassName BdFunctionPermissionRelation
 * @Description BD方法和权限的关系表
 * @since 2020/9/18
 */
@Data
@Entity
@Table(name = "bd_function_permission_relation", indexes = {@Index(columnList = "bdId,permissionId", unique = true)})
@org.hibernate.annotations.Table(appliesTo = "bd_function_permission_relation", comment = "BD方法和权限的关系表")
public class BdFunctionPermissionRelation {
    /**
     * 主键id
     * 自增长
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, columnDefinition = "bigint(20)  comment '主键ID'")
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(128) comment 'BD id,发行BD成功后DRS返回'")
    private String bdId;
    @Column(nullable = false, columnDefinition = "varchar(128) comment 'permission id,发布成功后返回'")
    private String permissionId;
    @Column(nullable = false, columnDefinition = "varchar(128) comment '报文中描述的'")
    private String functionName;
    @Column(nullable = false, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '创建时间'")
    private Date createAt;
    @Column(nullable = false, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间'")
    private Date updateAt;
}
