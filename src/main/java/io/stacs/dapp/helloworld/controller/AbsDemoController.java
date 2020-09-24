package io.stacs.dapp.helloworld.controller;

import io.stacs.dapp.helloworld.service.SmtDemoService;
import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.demo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author HuangShengli
 * @ClassName DemoController
 * @Description demo of Asset Backed Securities
 * Allows access via curl, postman, swagger
 * @since 2020/9/12
 */
@Api(tags = "ABS endpoints using SMT format")
@Slf4j
@RestController
@RequestMapping("/smt/demo/abs")
public class AbsDemoController {

    @Autowired
    Map<String, SmtDemoService> smtDemoService;

    /**
     * @return
     */
    @ApiOperation(value = "Issue BD for ABS:smtbd-abs-abs-issue-1-v1")
    @PostMapping("issueAbsBd")
    @ResponseBody
    public DrsResponse issueBD(@Validated @RequestBody AbsBDRequest request) {

        return smtDemoService.get("smtbd-abs-abs-issue-1-v1").doDemo(request);
    }

    /**
     * @return
     */
    @ApiOperation(value = "Issue ABS:smta-abs-corporation-issue-1-v1")
    @PostMapping("issueAbs")
    @ResponseBody
    public DrsResponse issueAbs(@Validated @RequestBody AbsCreateRequest request) {

        return smtDemoService.get("smta-abs-corporation-issue-1-v1").doDemo(request);
    }

    /**
     * @return
     */
    @ApiOperation(value = "Issue offer for ABS:smtt-abs-subscription-offer-3-v1")
    @PostMapping("offerAbs")
    @ResponseBody
    public DrsResponse issueAbs(@Validated @RequestBody AbsOfferRequest request) {
        Assert.hasLength(request.getHeader().getSessionId(), "Session Id cannot be empty");
        Assert.isTrue(request.getBody().getOrderStartTime().getTime() > 0, "order start time should be overweight 0");
        Assert.isTrue(request.getBody().getOrderEndTime().getTime() > 0, "order end time should be overweight 0");
        Assert.isTrue(request.getBody().getPaymentEndTime().getTime() > 0, "payment end time should be overweight 0");
        Assert.isTrue(request.getBody().getOrderEndTime().after(request.getBody().getOrderStartTime()), "order start time should be overweight order start time");
        Assert.isTrue(request.getBody().getPaymentEndTime().compareTo(request.getBody().getOrderStartTime()) >= 0, "order start time should be overweight order start time");
        Assert.isTrue(request.getBody().getMaxSizePerAddress().compareTo(request.getBody().getMinSizePerTrade()) >= 0, "MaxSizePerAddress must be overweight MinSizePerTrade");
        Assert.isTrue(request.getBody().getQuantity().compareTo(request.getBody().getMaxSizePerAddress()) >= 0, "Quantity must be overweight MaxSizePerAddress");


        return smtDemoService.get("smtt-abs-subscription-offer-3-v1").doDemo(request);
    }

    /**
     * bid for the ABS
     *
     * @param request the request
     * @return the drs response
     */
    @ApiOperation(value = "bid to purchase ABS:smtt-abs-subscription-bid-2-v1")
    @PostMapping("bidAbs")
    @ResponseBody
    public DrsResponse bidAbs(@Validated @RequestBody AbsBidRequest request) {
        Assert.hasLength(request.getHeader().getSessionId(), "Session Id cannot be empty");
        return smtDemoService.get("smtt-abs-subscription-bid-2-v1").doDemo(request);
    }

    /**
     * cancellation of bid order for ABS
     *
     * @param request the request
     * @return the drs response
     */
    @ApiOperation(value = "cancellation of ABS:smtt-abs-subscription-cancel-1-v1")
    @PostMapping("cancelAbs")
    @ResponseBody
    public DrsResponse cancelAbs(@Validated @RequestBody AbsCancelRequest request) {
        Assert.hasLength(request.getHeader().getSessionId(), "Session Id cannot be empty");
        return smtDemoService.get("smtt-abs-subscription-cancel-1-v1").doDemo(request);
    }

    /**
     * buyer payment for ABS
     *
     * @param request the request
     * @return the drs response
     */
    @ApiOperation(value = "payment for ABS:smtt-abs-subscription-payment-1-v1")
    @PostMapping("paymentAbs")
    @ResponseBody
    public DrsResponse paymentAbs(@Validated @RequestBody AbsPaymentRequest request) {
        Assert.hasLength(request.getHeader().getSessionId(), "Session Id cannot be empty");
        return smtDemoService.get("smtt-abs-subscription-payment-1-v1").doDemo(request);
    }

    /**
     * seller confirms purchase of ABS
     *
     * @param request the request
     * @return the drs response
     */
    @ApiOperation(value = "seller confirmation of ABS purchase:smtt-abs-subscription-confirm-1-v1")
    @PostMapping("confirmAbs")
    @ResponseBody
    public DrsResponse confirmAbs(@Validated @RequestBody AbsConfirmRequest request) {
        Assert.hasLength(request.getHeader().getSessionId(), "Session Id cannot be empty");
        return smtDemoService.get("smtt-abs-subscription-confirm-1-v1").doDemo(request);
    }

    /**
     * refund request for ABS, can be sent by either buyer or seller
     *
     * @param request the request
     * @return the drs response
     */
    @ApiOperation(value = "refund request for ABS:smtt-abs-subscription-refund-2-v1")
    @PostMapping("refundAbs")
    @ResponseBody
    public DrsResponse refundAbs(@Validated @RequestBody AbsRefundRequest request) {
        Assert.hasLength(request.getHeader().getSessionId(), "Session Id cannot be empty");
        return smtDemoService.get("smtt-abs-subscription-refund-2-v1").doDemo(request);
    }

    /**
     * delayed settlement of ABS
     *
     * @param request the request
     * @return the drs response
     */
    @ApiOperation(value = "delayed settlement of ABS:smtt-abs-subscription-settle-2-v1")
    @PostMapping("settleAbs")
    @ResponseBody
    public DrsResponse settleAbs(@Validated @RequestBody AbsSettleRequest request) {
        Assert.hasLength(request.getHeader().getSessionId(), "Session Id cannot be empty");
        return smtDemoService.get("smtt-abs-subscription-settle-2-v1").doDemo(request);
    }

    /**
     * dispute request for ABS
     *
     * @param request the request
     * @return the drs response
     */
    @ApiOperation(value = "dispute request for ABS:smtt-abs-subscription-dispute-1-v1")
    @PostMapping("disputeAbs")
    @ResponseBody
    public DrsResponse disputeAbs(@Validated @RequestBody AbsDisputeRequest request) {
        Assert.hasLength(request.getHeader().getSessionId(), "Session Id cannot be empty");
        return smtDemoService.get("smtt-abs-subscription-dispute-1-v1").doDemo(request);
    }

    /**
     * buyer rejects offer for ABS
     *
     * @param request the request
     * @return the drs response
     */
    @ApiOperation(value = "buyer rejects offer for ABS:smtt-abs-subscription-revert-1-v1")
    @PostMapping("revertAbs")
    @ResponseBody
    public DrsResponse revertAbs(@Validated @RequestBody AbsRevertRequest request) {
        Assert.hasLength(request.getHeader().getSessionId(), "Session Id cannot be empty");
        return smtDemoService.get("smtt-abs-subscription-revert-1-v1").doDemo(request);
    }

}
