package io.stacs.dapp.helloworld.vo.demo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author HuangShengli
 * @ClassName PermissionRequest
 * @Description
 * @since 2020/9/19
 */
@Data
@ApiModel(value = "买/卖方发起退款ABS报文参数")
public class AbsRefundRequest extends DemoBaseRequest {

}
