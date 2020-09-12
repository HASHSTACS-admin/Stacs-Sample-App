package io.stacs.dapp.helloworld;

import io.stacs.dapp.helloworld.config.MyConfig;
import io.stacs.dapp.helloworld.dao.SmtMessageDao;
import io.stacs.dapp.helloworld.dao.po.SmtMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {HelloworldApplication.class})
class HelloworldApplicationTests {

    @Autowired
    private MyConfig config;
    @Autowired
    private SmtMessageDao smtMessageDao;

    @Test
    void contextLoads() {
        System.out.println(config.getMyIdentifierId());
        SmtMessage message = smtMessageDao.findByUuid("xxxxx");
        System.out.println(message);
    }

}
