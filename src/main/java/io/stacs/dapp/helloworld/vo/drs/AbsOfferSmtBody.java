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

    @ApiModelProperty(value = "asset id", required = true)
    @NotBlank(message = "asset id cannot be blank")
    private String assetId;

    @ApiModelProperty(value = "quantity", required = true)
    @NotNull(message = "quantity has to be larger than 0")
    @DecimalMin(value = "0.00000001", message = "quantity has to be larger than 0")
    private BigDecimal quantity;

    @ApiModelProperty(value = "unit price", required = true)
    @NotNull(message = "unit price has to be larger than 0")
    @DecimalMin(value = "0.00000001", message = "unit price has to be larger than 0")
    private BigDecimal unitPrice;

    @ApiModelProperty(value = "payment currency", required = true)
    @NotBlank(message = "payment currency cannot be blank")
    @Length(min = 3, max = 3)
    private String paymentCurrency;

    @ApiModelProperty(value = "max size for purchase per address", required = true)
    @NotNull(message = "size has to be larger than 0")
    @DecimalMin(value = "0.00000001", message = "size has to be larger than 0")
    private BigDecimal maxSizePerAddress;

    @ApiModelProperty(value = "min size for purchase per trade", required = true)
    @NotNull(message = "size has to be larger than 0")
    @DecimalMin(value = "0.00000001", message = "size has to be larger than 0")
    private BigDecimal minSizePerTrade;

    @ApiModelProperty(value = "lot size")
    private BigDecimal lotSize;

    @ApiModelProperty(value = "order start time", required = true)
    @NotNull(message = "order start time cannot be null")
    private Date orderStartTime;

    @ApiModelProperty(value = "order end time", required = true)
    @NotNull(message = "order end time cannot be null")
    private Date orderEndTime;


    @ApiModelProperty(value = "payment end time", required = true)
    @NotNull(message = "payment end time cannot be null")
    private Date paymentEndTime;

    @ApiModelProperty(value = "settlement time")
    private Date settleTime;

    @ApiModelProperty(value = "subscription additional info")
    private String subscriptionInfo;
}