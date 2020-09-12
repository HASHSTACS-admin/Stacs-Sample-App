package io.stacs.dapp.helloworld.dao;

import io.stacs.dapp.helloworld.dao.po.SmtMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author HuangShengli
 * @ClassName SmtMessageDao
 * @Description TODO
 * @since 2020/9/12
 */
@Repository
public interface SmtMessageDao extends JpaRepository<SmtMessage, Long> {
    /**
     * 根据uuid查询报文消息
     *
     * @param uuid
     * @return
     */
    SmtMessage findByUuid(String uuid);
}
