package io.stacs.dapp.helloworld.dao;

import io.stacs.dapp.helloworld.dao.po.AssetOperation;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Su Wenbo
 * @since 2020/9/24
 */
public interface AssetOperationDao extends CrudRepository<AssetOperation, Long> {

    AssetOperation findByUuid(String uuid);

    AssetOperation findByOperationId(Long operationId);

}
