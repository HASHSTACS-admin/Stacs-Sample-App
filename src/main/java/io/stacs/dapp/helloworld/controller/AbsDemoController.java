package io.stacs.dapp.helloworld.controller;

import io.stacs.dapp.helloworld.service.SmtDemoService;
import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.demo.AbsBDRequest;
import io.stacs.dapp.helloworld.vo.demo.AbsBidRequest;
import io.stacs.dapp.helloworld.vo.demo.AbsCancelRequest;
import io.stacs.dapp.helloworld.vo.demo.AbsConfirmRequest;
import io.stacs.dapp.helloworld.vo.demo.AbsCreateRequest;
import io.stacs.dapp.helloworld.vo.demo.AbsDisputeRequest;
import io.stacs.dapp.helloworld.vo.demo.AbsOfferRequest;
import io.stacs.dapp.helloworld.vo.demo.AbsPaymentRequest;
import io.stacs.dapp.helloworld.vo.demo.AbsRefundRequest;
import io.stacs.dapp.helloworld.vo.demo.AbsRevertRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author HuangShengli
 * @ClassName DemoController
 * @Description 用户可以体验发送已提供的各种报文
 * 当用户启动helloworld服务后，可以使用curl、postman、swagger等工具来尝试发送报文
 * @since 2020/9/12
 */
@Api(tags = "SMT报文体验入口")
@Slf4j
@RestController
@RequestMapping("/smt/demo/abs")
public class AbsDemoController {

    @Autowired
    Map<String, SmtDemoService> smtDemoService;

    /**
     * @return
     */
    @ApiOperation(value = "发行ABS得BD报文:smtbd-abs-abs-issue-1-v1")
    @PostMapping("issueAbsBd")
    @ResponseBody
    public DrsResponse issueBD(@Validated @RequestBody AbsBDRequest request) {

        return smtDemoService.get("smtbd-abs-abs-issue-1-v1").doDemo(request);
    }

    /**
     * @return
     */
    @ApiOperation(value = "发行ABS资产:smta-abs-corporation-issue-1-v1")
    @PostMapping("issueAbs")
    @ResponseBody
    public DrsResponse issueAbs(@Validated @RequestBody AbsCreateRequest request) {

        return smtDemoService.get("smta-abs-corporation-issue-1-v1").doDemo(request);
    }

    /**
     * @return
     */
    @ApiOperation(value = "发布ABS的售出单:smtt-abs-subscription-offer-3-v1")
    @PostMapping("offerAbs")
    @ResponseBody
    public DrsResponse issueAbs(@Validated @RequestBody AbsOfferRequest request) {

        return smtDemoService.get("smtt-abs-subscription-offer-3-v1").doDemo(request);
    }

    /**
     * 买方出价认购ABS报文.
     *
     * @param request the request
     * @return the drs response
     */
    @ApiOperation(value = "买方出价认购ABS:smtt-abs-subscription-bid-2-v1")
    @PostMapping("bidAbs")
    @ResponseBody
    public DrsResponse bidAbs(@Validated @RequestBody AbsBidRequest request) {
        Assert.hasLength(request.getHeader().getSessionId(), "Session Id不能为空");
        return smtDemoService.get("smtt-abs-subscription-bid-2-v1").doDemo(request);
    }

    /**
     * 买方主动撤单ABS报文.
     *
     * @param request the request
     * @return the drs response
     */
    @ApiOperation(value = "买方主动撤单ABS:smtt-abs-subscription-cancel-1-v1")
    @PostMapping("cancelAbs")
    @ResponseBody
    public DrsResponse cancelAbs(@Validated @RequestBody AbsCancelRequest request) {
        Assert.hasLength(request.getHeader().getSessionId(), "Session Id不能为空");
        return smtDemoService.get("smtt-abs-subscription-cancel-1-v1").doDemo(request);
    }

    /**
     * 买方付款ABS报文
     *
     * @param request the request
     * @return the drs response
     */
    @ApiOperation(value = "买方付款ABS:smtt-abs-subscription-payment-1-v1")
    @PostMapping("paymentAbs")
    @ResponseBody
    public DrsResponse paymentAbs(@Validated @RequestBody AbsPaymentRequest request) {
        Assert.hasLength(request.getHeader().getSessionId(), "Session Id不能为空");
        return smtDemoService.get("smtt-abs-subscription-payment-1-v1").doDemo(request);
    }

    /**
     * 卖方确认收款ABS报文
     *
     * @param request the request
     * @return the drs response
     */
    @ApiOperation(value = "卖方确认收款ABS:smtt-abs-subscription-confirm-1-v1")
    @PostMapping("confirmAbs")
    @ResponseBody
    public DrsResponse confirmAbs(@Validated @RequestBody AbsConfirmRequest request) {
        Assert.hasLength(request.getHeader().getSessionId(), "Session Id不能为空");
        return smtDemoService.get("smtt-abs-subscription-confirm-1-v1").doDemo(request);
    }

    /**
     * 买/卖方发起退款ABS报文
     *
     * @param request the request
     * @return the drs response
     */
    @ApiOperation(value = "买/卖方发起退款ABS:smtt-abs-subscription-refund-2-v1")
    @PostMapping("refundAbs")
    @ResponseBody
    public DrsResponse refundAbs(@Validated @RequestBody AbsRefundRequest request) {
        Assert.hasLength(request.getHeader().getSessionId(), "Session Id不能为空");
        return smtDemoService.get("smtt-abs-subscription-refund-2-v1").doDemo(request);
    }

    /**
     * 买/卖方发起争议ABS报文
     *
     * @param request the request
     * @return the drs response
     */
    @ApiOperation(value = "买/卖方发起争议:smtt-abs-subscription-dispute-1-v1")
    @PostMapping("disputeAbs")
    @ResponseBody
    public DrsResponse disputeAbs(@Validated @RequestBody AbsDisputeRequest request) {
        Assert.hasLength(request.getHeader().getSessionId(), "Session Id不能为空");
        return smtDemoService.get("smtt-abs-subscription-dispute-1-v1").doDemo(request);
    }

    /**
     * 买方退单ABS报文
     *
     * @param request the request
     * @return the drs response
     */
    @ApiOperation(value = "买方退单:smtt-abs-subscription-revert-1-v1")
    @PostMapping("revertAbs")
    @ResponseBody
    public DrsResponse revertAbs(@Validated @RequestBody AbsRevertRequest request) {
        Assert.hasLength(request.getHeader().getSessionId(), "Session Id不能为空");
        return smtDemoService.get("smtt-abs-subscription-revert-1-v1").doDemo(request);
    }

}
