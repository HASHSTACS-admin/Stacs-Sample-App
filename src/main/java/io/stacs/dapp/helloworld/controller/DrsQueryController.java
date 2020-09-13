package io.stacs.dapp.helloworld.controller;

import io.stacs.dapp.helloworld.service.SmtQueryService;
import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.demo.BalanceOfRequest;
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
@Api(tags = "DRS相关API同步接口")
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

        return smtQueryService.balanceOf(request);
    }

    @ApiOperation(value = "获取地址")
    @GetMapping("/getAddress")
    @ResponseBody
    public DrsResponse getAddress() {
        return smtQueryService.getAddress();
    }
}
