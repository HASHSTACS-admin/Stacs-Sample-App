package io.stacs.dapp.helloworld.dao;

import io.stacs.dapp.helloworld.dao.po.SmtBd;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author: yezaiyong
 * @create: 2020-09-21 17:27
 **/
public interface SmtBdDao extends CrudRepository<SmtBd, Long> {

    /**
     * 根据商户号查询bd
     *
     * @param identifierId
     * @return
     */
    List<SmtBd> findByIdentifierId(String identifierId);

    /**
     * Find by uuid smt bd.
     *
     * @param uuid the uuid
     * @return the smt bd
     */
    SmtBd findByUuid(String uuid);

}

