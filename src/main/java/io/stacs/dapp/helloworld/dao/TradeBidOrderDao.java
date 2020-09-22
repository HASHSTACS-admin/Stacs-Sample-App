package io.stacs.dapp.helloworld.dao;

import io.stacs.dapp.helloworld.dao.po.TradeBidOrder;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Su Wenbo
 * @since 2020/9/22
 */
public interface TradeBidOrderDao extends CrudRepository<TradeBidOrder, Long> {

    TradeBidOrder findByUuid(String uuid);

}
