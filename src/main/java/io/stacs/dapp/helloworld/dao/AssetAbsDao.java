package io.stacs.dapp.helloworld.dao;

import io.stacs.dapp.helloworld.dao.po.AssetAbs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssetAbsDao extends JpaRepository<AssetAbs, Long> {

    /**
     * 根据商户号查询abs
     *
     * @param identifierId
     * @return
     */
    List<AssetAbs> findByIdentifierId(String identifierId);

    /**
     * 根据uuid查询
     *
     * @param uuid
     * @return
     */
    AssetAbs findByUuid(String uuid);

}
