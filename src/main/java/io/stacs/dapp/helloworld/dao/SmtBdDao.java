package io.stacs.dapp.helloworld.dao;

import io.stacs.dapp.helloworld.dao.po.SmtBd;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author: yezaiyong
 * @create: 2020-09-21 17:27
 **/
public interface SmtBdDao extends JpaRepository<SmtBd, Long> {

    /**
     * 根据商户号查询bd
     * @param identifierId
     * @return
     */
    List<SmtBd> findByIdentifierId(String identifierId);
}