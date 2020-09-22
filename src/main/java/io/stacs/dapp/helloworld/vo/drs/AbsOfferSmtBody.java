package io.stacs.dapp.helloworld.vo.drs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: Huangshengli
 * @create: 2020-09-21
 **/
@Data
public class AbsOfferSmtBody implements Serializable {

    @ApiModelProperty(value = "资产代码", required = true)
    @NotBlank(message = "售卖资产不能为空")
    private String assetId;

    @ApiModelProperty(value = "售卖数量", required = true)
    @NotNull(message = "售卖数量需大于0")
    @DecimalMin(value = "0.00000001", message = "售卖数量需大于0")
    private BigDecimal quantity;

    @ApiModelProperty(value = "单价", required = true)
    @NotNull(message = "单价需大于0")
    @DecimalMin(value = "0.00000001", message = "单价需大于0")
    private BigDecimal unitPrice;

    @ApiModelProperty(value = "接受的付款法币", required = true)
    @NotBlank(message = "付款法币不能为空")
    @Length(min = 3, max = 3)
    private String paymentCurrency;

    @ApiModelProperty(value = "单个地址能购买的总量", required = true)
    @NotNull(message = "单个地址能购买的总量需大于0")
    @DecimalMin(value = "0.00000001", message = "单个地址能购买的总量需大于0")
    private BigDecimal maxSizePerAddress;

    @ApiModelProperty(value = "单次最低购买量", required = true)
    @NotNull(message = "单次最低购买量需大于0")
    @DecimalMin(value = "0.00000001", message = "单次最低购买量需大于0")
    private BigDecimal minSizePerTrade;

    @ApiModelProperty(value = "倍数，小于等于卖出数量，只能以此数字的整倍数进行购买")
    private BigDecimal lotSize;

    @ApiModelProperty(value = "买家可开始下单的时间", required = true)
    @NotNull(message = "买家可开始下单的时间不能为空")
    private Date orderStartTime;

    @ApiModelProperty(value = "买家下单截止时间", required = true)
    @NotNull(message = "买家下单截止时间不能为空")
    private Date orderEndTime;


    @ApiModelProperty(value = "买家付款截止时间", required = true)
    @NotNull(message = "买家付款截止时间")
    private Date paymentEndTime;

    @ApiModelProperty(value = "结算时间")
    private Date settleTime;

    @ApiModelProperty(value = "发起认购时想要告知的额外信息")
    private String subscriptionInfo;
}