package io.stacs.dapp.helloworld.vo.demo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author HuangShengli
 * @ClassName AbsSettleRequest
 * @Description
 * @since 2020/9/22
 */
@Data
@ApiModel(value = "ABS offer单买/卖方发起结算报文参数")
public class AbsSettleRequest extends DemoBaseRequest {

}
