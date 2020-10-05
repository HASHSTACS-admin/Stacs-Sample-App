package io.stacs.dapp.helloworld.controller;

import io.stacs.dapp.helloworld.service.SmtDemoService;
import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.demo.AbsBuybackFreezeRequest;
import io.stacs.dapp.helloworld.vo.demo.AbsBuybackRequest;
import io.stacs.dapp.helloworld.vo.demo.AbsInterestSettleRequest;
import io.stacs.dapp.helloworld.vo.demo.AddSnapshotRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Su Wenbo
 * @since 2020/9/24
 */
@Api(tags = "DRS API Endpoint")
@Slf4j
@RestController
@RequestMapping("/smt/demo")
public class AbsOperationController {

    @Autowired
    Map<String, SmtDemoService> smtDemoService;

    /**
     * Taking a snapshot of the blockchain with SMT Format
     *
     * @param request the request
     * @return the drs response
     */
    @ApiOperation(value = "/endpoint - snapshot:smtc-snapshot-snapshot-add-1-v1")
    @PostMapping("/addSnapshot")
    @ResponseBody
    public DrsResponse addSnapshot(@Validated @RequestBody AddSnapshotRequest request) {
        return smtDemoService.get("smtc-snapshot-snapshot-add-1-v1").doDemo(request);
    }

    /**
     * ABS - Freezing of assets for redemption, SMT Format
     *
     * @param request the request
     * @return the drs response
     */
    @ApiOperation(value = "/endpoint - ABS redemption freeze:smtt-abs-buyback-freeze-2-v1")
    @PostMapping("/abs/buybackFreeze")
    @ResponseBody
    public DrsResponse absBuybackFreeze(@Validated @RequestBody AbsBuybackFreezeRequest request) {
        return smtDemoService.get("smtt-abs-buyback-freeze-2-v1").doDemo(request);
    }

    /**
     * ABS interest payment, SMT Format
     *
     * @param request the request
     * @return the drs response
     */
    @ApiOperation(value = "/endpoint - ABS payment:smtt-abs-interestSettle-settle-1-v1")
    @PostMapping("/abs/interestSettle")
    @ResponseBody
    public DrsResponse absInterestSettle(@Validated @RequestBody AbsInterestSettleRequest request) {
        return smtDemoService.get("smtt-abs-interestSettle-settle-1-v1").doDemo(request);
    }

    /**
     * ABS redemption, SMT Format
     *
     * @param request the request
     * @return the drs response
     */
    @ApiOperation(value = "/endpoint - ABS redemption:smtt-abs-buyback-buyback-1-v1")
    @PostMapping("/abs/buyback")
    @ResponseBody
    public DrsResponse absBuyback(@Validated @RequestBody AbsBuybackRequest request) {
        return smtDemoService.get("smtt-abs-buyback-buyback-1-v1").doDemo(request);
    }

}
