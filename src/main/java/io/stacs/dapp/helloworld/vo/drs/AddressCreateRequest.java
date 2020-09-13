package io.stacs.dapp.helloworld.vo.drs;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;

/**
 * @author Huang Shengli
 * @date 2020-03-14
 */
@Data
@Builder
public class AddressCreateRequest implements Serializable {

    /**
     * uuid
     */
    @NonNull
    private String uuid;
    @NonNull
    private String identifierId;

}
