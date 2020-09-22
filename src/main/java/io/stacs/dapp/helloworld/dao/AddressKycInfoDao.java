package io.stacs.dapp.helloworld.dao;

import io.stacs.dapp.helloworld.dao.po.AddressKycInfo;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Su Wenbo
 * @since 2020/9/22
 */
public interface AddressKycInfoDao extends CrudRepository<AddressKycInfo, Long> {

}
