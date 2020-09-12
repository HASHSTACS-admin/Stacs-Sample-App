package io.stacs.dapp.helloworld.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;

/**
 * @author HuangShengli
 * @ClassName DrsBaseRequest
 * @Description base request
 * @since 2020/5/13
 */
@Data
@NoArgsConstructor
public class DrsBaseRequest implements Serializable {
    @NonNull
    private String identifierId;
}
