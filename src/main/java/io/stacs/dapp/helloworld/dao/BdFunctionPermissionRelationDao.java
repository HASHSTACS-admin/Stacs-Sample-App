package io.stacs.dapp.helloworld.dao;

import io.stacs.dapp.helloworld.dao.po.BdFunctionPermissionRelation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author: yezaiyong
 * @create: 2020-09-21 17:28
 **/
public interface BdFunctionPermissionRelationDao extends JpaRepository<BdFunctionPermissionRelation, Long> {

    /**
     * Query by BD ID
     * @param bdId
     * @return
     */
    List<BdFunctionPermissionRelation> findByBdId(String bdId);
}