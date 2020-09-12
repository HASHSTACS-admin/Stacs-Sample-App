package io.stacs.dapp.helloworld.controller;

import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.smt.DigitalCurrencySimpleRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author HuangShengli
 * @ClassName DemoController
 * @Description 用户可以体验发送已提供的各种报文
 * 当用户启动helloworld服务后，可以使用curl、postman等工具来尝试发送报文
 * @since 2020/9/12
 */
@Api(tags = "发送报文体验入口")
@Slf4j
@RestController
@RequestMapping("/smt/demo")
public class DemoController {

    /**
     * 为了
     *
     * @return
     */
    @ApiOperation(value = "发行数字货币报文")
    @PostMapping("smta-dc-dc-issue-1-v1")
    @ResponseBody
    public DrsResponse issueDigitalCurrency(@Validated @RequestBody DigitalCurrencySimpleRequest request, BindResult errors) {
        return null;
    }
}
