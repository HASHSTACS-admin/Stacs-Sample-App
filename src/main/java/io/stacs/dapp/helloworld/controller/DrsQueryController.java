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
 * @Description 用户可以获取地址、查询余额等非上链交易的通用入口
 * @since 2020/9/12
 */
@Api(tags = "DRS非报文查询API")
@Slf4j
@RestController
@RequestMapping("/smt/api")
public class DrsQueryController {

    @Autowired
    SmtQueryService smtQueryService;


    @ApiOperation(value = "余额查询", notes = "余额查询请求")
    @PostMapping("/balanceOf")
    @ResponseBody
    public DrsResponse balanceOf(@Validated @RequestBody BalanceOfRequest request) {

        try {
            return smtQueryService.balanceOf(request);
        } catch (Exception e) {
            return DrsResponse.fail("error", e.getMessage());
        }
    }

    @ApiOperation(value = "获取地址")
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

    @ApiOperation(value = "根据商户号和UUID查询SMT报文结果")
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
