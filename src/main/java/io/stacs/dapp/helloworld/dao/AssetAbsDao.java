package io.stacs.dapp.helloworld.dao;

import io.stacs.dapp.helloworld.dao.po.AssetAbs;
import org.springframework.data.repository.CrudRepository;

/**
 * The interface abs dao.
 *
 * @author Huangshengli
 * @since 2020 /9/21
 */
public interface AssetAbsDao extends CrudRepository<AssetAbs, Long> {

}
