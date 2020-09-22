package io.stacs.dapp.helloworld.controller;

import io.stacs.dapp.helloworld.service.SmtDemoService;
import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.demo.DigitalCurrencySmtRequest;
import io.stacs.dapp.helloworld.vo.demo.TransferSmtRequest;
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
 * @author HuangShengli
 * @ClassName DemoController
 * @Description 用户可以体验发送已提供的各种报文
 * 当用户启动helloworld服务后，可以使用curl、postman、swagger等工具来尝试发送报文
 * @since 2020/9/12
 */
@Api(tags = "SMT报文体验入口")
@Slf4j
@RestController
@RequestMapping("/smt/demo/")
public class DemoController {

    @Autowired
    Map<String, SmtDemoService> smtDemoService;

    /**
     *
     * @return
     */
    @ApiOperation(value = "数字货币报文")
    @PostMapping("issueDigitalCurrency")
    @ResponseBody
    public DrsResponse issueDigitalCurrency(@Validated @RequestBody DigitalCurrencySmtRequest request) {

        return smtDemoService.get("smta-dc-dc-issue-1-v1").doDemo(request);
    }

    /**
     *
     * @return
     */
    @ApiOperation(value = "转账(债券、数字货币、ABS、凭证)")
    @PostMapping("transfer")
    @ResponseBody
    public DrsResponse transfer(@Validated @RequestBody TransferSmtRequest request) {

        return smtDemoService.get("transfer").doDemo(request);
    }

}
