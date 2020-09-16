package io.stacs.dapp.helloworld.config;

import io.stacs.dapp.helloworld.constant.DrsApiConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author HuangShengli
 * @ClassName MyConfig
 * @Description TODO
 * @since 2020/9/12
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "helloworld.drs")
public class DrsConfig implements DrsApiConstant {
    private String url;
    private String publicKey;
    private String myPublicKey;
    private String myPrivateKey;
    private String myIdentifierId;

    public String getSmtSendUrl() {
        return url + SMT_SEND_URL;
    }

    public String getCreateAddressUrl() {
        return url + CREATE_ADDRESS_URL;
    }

    public String getBalanceOfUrl() {
        return url + BALANCE_OF_URL;
    }

    public String getQuerySmtByUuidUrl() {
        return url + QUERY_SMT_RESULT;
    }

}
