package io.stacs.dapp.helloworld.dao;

import io.stacs.dapp.helloworld.dao.po.AssetAbs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssetAbsDao extends JpaRepository<AssetAbs, Long> {

    /**
     * Query ABS by Merchant Id
     *
     * @param identifierId
     * @return
     */
    List<AssetAbs> findByIdentifierId(String identifierId);

    /**
     * Query ABS by Uuid
     *
     * @param uuid
     * @return
     */
    AssetAbs findByUuid(String uuid);

}
