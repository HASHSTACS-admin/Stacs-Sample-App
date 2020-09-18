package io.stacs.dapp.helloworld.controller;

import io.stacs.dapp.helloworld.service.SmtQueryService;
import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.demo.BalanceOfRequest;
import io.stacs.dapp.helloworld.vo.demo.QuerySmtResultRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author HuangShengli
 * @ClassName DrsQueryController
 * @Description Query API Requests
 * @since 2020/9/12
 */
@Api(tags = "DRS Query APIs")
@Slf4j
@RestController
@RequestMapping("/smt/api")
public class DrsQueryController {

    @Autowired
    SmtQueryService smtQueryService;


    @ApiOperation(value = "Token Balance Query", notes = "Balance Query Request")
    @PostMapping("/balanceOf")
    @ResponseBody
    public DrsResponse balanceOf(@Validated @RequestBody BalanceOfRequest request) {

        try {
            return smtQueryService.balanceOf(request);
        } catch (Exception e) {
            return DrsResponse.fail("error", e.getMessage());
        }
    }

    @ApiOperation(value = "Query Address")
    @GetMapping("/getAddress")
    @ResponseBody
    public DrsResponse getAddress() {
        try {
            //f1f08851b4d3729b0585c7c4735d8b3f59990449
            //78f27fa21763300002582543279ce58d9e1ed0e2
            return smtQueryService.getAddress();
        } catch (Exception e) {
            return DrsResponse.fail("error", e.getMessage());
        }
    }

    @ApiOperation(value = "Query Transaction Status using SMT by Merchant Id and Uuid")
    @PostMapping("/querySmtResult")
    @ResponseBody
    public DrsResponse querySmtResult(@Validated @RequestBody QuerySmtResultRequest request) {
        try {
            return smtQueryService.querySmtResult(request);
        } catch (Exception e) {
            return DrsResponse.fail("error", e.getMessage());
        }
    }
}
