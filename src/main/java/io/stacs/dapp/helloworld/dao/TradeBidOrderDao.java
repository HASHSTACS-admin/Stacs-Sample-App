package io.stacs.dapp.helloworld.dao;

import io.stacs.dapp.helloworld.dao.po.TradeBidOrder;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Su Wenbo
 * @since 2020/9/22
 */
public interface TradeBidOrderDao extends CrudRepository<TradeBidOrder, Long> {

    TradeBidOrder findByUuid(String uuid);

    TradeBidOrder findByMessageId(String messageId);

    List<TradeBidOrder> findByOfferSessionId(String offerSessionId);


}
