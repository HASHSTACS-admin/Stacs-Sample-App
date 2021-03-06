package io.stacs.dapp.helloworld.dao;

import io.stacs.dapp.helloworld.dao.po.TradeOfferOrder;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Su Wenbo
 * @since 2020/9/22
 */
public interface TradeOfferOrderDao extends CrudRepository<TradeOfferOrder, Long> {

    TradeOfferOrder findBySessionId(String sessionId);

    TradeOfferOrder findByUuid(String uuid);

}
