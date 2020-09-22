package io.stacs.dapp.helloworld.callback;

import io.stacs.dapp.helloworld.dao.AddressKycInfoDao;
import io.stacs.dapp.helloworld.dao.po.AddressKycInfo;
import io.stacs.dapp.helloworld.vo.DrsSmtMessage;
import lombok.RequiredArgsConstructor;

import java.util.Date;

/**
 * @author Su Wenbo
 * @since 2020/9/21
 */
@RequiredArgsConstructor
public class IdentityCallbackHandler {

    private final AddressKycInfoDao addressKycInfoDao;

    public void handleIdentityCallBack(DrsSmtMessage message, Byte identityType) {
        //address kyc info
        AddressKycInfo addressKycInfo = new AddressKycInfo();
        DrsSmtMessage.SmtBody body = message.getBody();
        addressKycInfo.setAddress(body.getString("targetAddress"));
        addressKycInfo.setIdentityType(identityType);
        addressKycInfo.setKycInfo(body.getJSONArray("identity").toJSONString());
        addressKycInfo.setCreateAt(new Date());
        addressKycInfo.setUpdateAt(new Date());
        addressKycInfoDao.save(addressKycInfo);
    }

}
