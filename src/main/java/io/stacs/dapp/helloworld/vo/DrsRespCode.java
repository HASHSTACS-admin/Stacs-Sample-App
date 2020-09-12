package io.stacs.dapp.helloworld.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author HuangShengli
 * @ClassName DrsRespCodeEnum
 * @Description TODO
 * @since 2020/3/17
 */
@Getter
@AllArgsConstructor
public enum DrsRespCode {
    SUCCESS("success", ""),
    ACCEPTED("accepted", ""),
    ERROR_PARAM("param.invalid_parameter", "parameters error"),
    ERROR_DUP_REQUEST("idem.jpa", "duplicated request"),
    ERROR_UNKNOWN("error_unknown", "unknown error"),
    ERROR_SYS_UNCLASSIFIED("sys.unclassified", "drs system error"),
    ERROR_ON_CHAIN_150429("chain.150429", "contract symbol repeat"),
    ERROR_ON_CHAIN_150464("chain.150464", "smart contract execute failed"),
    ERROR_KMS_ADDRESS_NOT_EXISTS("kms.biz.asset_key_not_exists", "KMS address not exists"),
    ERROR_UNKNOWN_SMT_CODE("sys.unkonw_smt_code", "unknown smt code"),
    ERROR_ON_CHAIN_XXX("chain.", "error occurs on blockchain"),
    ;
    private String code;
    private String message;

    public static DrsRespCode getByCode(String code) {
        for (DrsRespCode respCode : values()) {
            if (respCode.code.equals(code)) {
                return respCode;
            }
            if (respCode.getCode().startsWith(ERROR_ON_CHAIN_XXX.getCode())) {
                return ERROR_ON_CHAIN_XXX;
            }
        }
        return ERROR_UNKNOWN;
    }
}
