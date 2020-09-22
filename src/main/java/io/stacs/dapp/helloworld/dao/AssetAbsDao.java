package io.stacs.dapp.helloworld.dao;

import io.stacs.dapp.helloworld.dao.po.AssetAbs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssetAbsDao extends JpaRepository<AssetAbs, Long> {

    /**
     * Query by Merchant id for Asset Backed Securities (ABS)
     * @param identifierId
     * @return
     */
    List<AssetAbs> findByIdentifierId(String identifierId);

}
