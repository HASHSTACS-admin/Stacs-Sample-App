package io.stacs.dapp.helloworld.dao;

import io.stacs.dapp.helloworld.dao.po.SmtPermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SmtPermissionDao extends JpaRepository<SmtPermission, Long> {

    /**
     * 根据商户号查询permission
     * @param identifierId
     * @return
     */
    List<SmtPermission> findByIdentifierId(String identifierId);
}
