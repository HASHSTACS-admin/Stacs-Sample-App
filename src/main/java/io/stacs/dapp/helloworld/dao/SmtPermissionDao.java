package io.stacs.dapp.helloworld.dao;

import io.stacs.dapp.helloworld.dao.po.SmtPermission;
import org.springframework.data.repository.CrudRepository;

/**
 * The interface Smt permission dao.
 *
 * @author Su Wenbo
 * @since 2020 /9/21
 */
public interface SmtPermissionDao extends CrudRepository<SmtPermission, Long> {

    /**
     * Find by uuid smt permission.
     *
     * @param uuid the uuid
     * @return the smt permission
     */
    SmtPermission findByUuid(String uuid);

}
