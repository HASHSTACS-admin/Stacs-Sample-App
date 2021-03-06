package io.stacs.dapp.helloworld.dao;

import io.stacs.dapp.helloworld.dao.po.SmtPermission;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SmtPermissionDao extends CrudRepository<SmtPermission, Long> {

    /**
     * Query Permission based on Merchant Id
     * @param identifierId
     * @return
     */
    List<SmtPermission> findByIdentifierId(String identifierId);



    /**
     * Find by uuid smt permission.
     *
     * @param uuid the uuid
     * @return the smt permission
     */
    SmtPermission findByUuid(String uuid);


}
