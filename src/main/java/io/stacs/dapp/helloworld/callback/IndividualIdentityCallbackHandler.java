package io.stacs.dapp.helloworld.callback;

import io.stacs.dapp.helloworld.constant.IdentityType;
import io.stacs.dapp.helloworld.dao.AddressKycInfoDao;
import io.stacs.dapp.helloworld.vo.DrsSmtMessage;
import org.springframework.stereotype.Component;

import static io.stacs.dapp.helloworld.callback.SmtCallbackHandler.SUFFIX_CALLBACK;

/**
 * @author Su Wenbo
 * @since 2020/9/22
 */
@Component("smti-individual-identity-set-1-v1" + SUFFIX_CALLBACK)
public class IndividualIdentityCallbackHandler extends IdentityCallbackHandler implements SmtCallbackHandler {

    public IndividualIdentityCallbackHandler(AddressKycInfoDao addressKycInfoDao) {
        super(addressKycInfoDao);
    }

    @Override
    public void handle(DrsSmtMessage message) {
        handleIdentityCallBack(message, IdentityType.INDIVIDUAL.getCode());
    }

}
