package io.stacs.dapp.helloworld.vo.smt;

import io.stacs.dapp.helloworld.vo.DrsBaseRequest;
import lombok.*;

/**
 * @author Huang Shengli
 * @date 2020-03-14
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DrsAddressCreateRequest extends DrsBaseRequest {

    /**
     * outter order no
     */
    @NonNull
    private String uuid;

}
